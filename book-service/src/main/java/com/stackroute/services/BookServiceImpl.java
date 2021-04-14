package com.stackroute.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.stackroute.exception.BookNotFound;
import com.stackroute.model.Book;
import com.stackroute.model.CommentSections;
import com.stackroute.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*This is the implementation class of the book service where abstract methods of book service are implemented.*/

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);



    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.endpointUrl}")
    private String endpointUrl;

    @Value("${aws.access_key_id}")
    private String accessKeyId;
    @Value("${aws.secret_access_key}")
    private String secretAccessKey;
    @Value("${aws.s3.region}")
    private String region;

    /*The MongoTemplate is injected into the BookServiceImpl by @Autowired annotation .*/
    @Autowired
    MongoTemplate mongoTemplate;

    /*The BookRepository is injected into the BookServiceImpl by @Autowired annotation .*/
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    private AmazonS3 s3client;

    @PostConstruct
    private void initializeAmazon() {

        BasicAWSCredentials creds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        this.s3client=  AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
    }


    /* This method will upload the book*/
    @Override
    public Book uploadBook(Book book) {
        return bookRepository.save(book);
    }

    /* This method will find the book based on title*/
    @Override
    public Book findBook(String bookTitle) {
        return bookRepository.findFirstByBookTitle(bookTitle);
    }

    /* This method will get the url of the book using book title*/
    @Override
    public String getBookPath(String bookTitle) throws BookNotFound {
        Book book1 = bookRepository.findFirstByBookTitle(bookTitle);
        String url = book1.getBookUrl();
        return url;
    }

    /* This method will update the comments given to the book*/
    @Override
    public Book updateComments(String bookTitle, CommentSections commentSections) {
        System.out.println("Under updateComments Sections");
        Book updateBook = new Book();
        Book book = bookRepository.findFirstByBookTitle(bookTitle);
        List<CommentSections> commentSectionsList = book.getCommentSections();
        if (commentSectionsList != null) {
            commentSectionsList.add(commentSections);
            System.out.println("Under updateComments Sections");
            return updateBook = this.updateBooks(bookTitle, commentSectionsList);
        } else {

            return updateBook = this.updateBooks(bookTitle, Arrays.asList(commentSections));
        }
    }


    /* This method will update the book with the updated comments*/
    public Book updateBooks(String bookTitle, List<CommentSections> commentSections) {
        System.out.println("Under updateBook Sections");
        Book updateBook = new Book();
        Query query = new Query(Criteria.where("bookTitle").is(bookTitle));
        Update update = new Update();
        update.set("commentSections", commentSections);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        updateBook = mongoTemplate.findAndModify(query, update, options, Book.class);
        return updateBook;
    }

    /* This method will find the book details by title*/
    @Override
    public Book getBookDetails(String bookTitle) throws BookNotFound {
        Book book1 = (Book) bookRepository.findFirstByBookTitle(bookTitle);
        return book1;
    }

    /* This method will List all the books */
    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Override
    // @Async annotation ensures that the method is executed in a different background thread
    // but not consume the main thread.
    @Async
    public String uploadFile(final MultipartFile multipartFile) {
        LOGGER.info("File upload in progress.");
        String fileUrl="";
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileToS3Bucket(fileName, file);
            LOGGER.info("File upload is completed.");
            file.delete();	// To remove the file locally created in the project folder.
        } catch (final AmazonServiceException ex) {
            LOGGER.info("File upload is failed.");
        }
        return fileUrl;
    }
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename()
                .replace(" ", "_");
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        LOGGER.info("converting");
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            LOGGER.error("Error converting the multi-part file to file= ");
        } catch(Exception exception) {
            LOGGER.info("Caught exception while converting");
        }
        return file;
    }

    private void uploadFileToS3Bucket(final String fileName, final File file) {
        try {
            LOGGER.info("Uploading file:"+fileName);
            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
//            LOGGER.info("putObject:"+putObjectRequest);
            s3client.putObject(putObjectRequest);
            LOGGER.info("file Uploaded");
        } catch (Exception exc) {
            LOGGER.info("Exception caught:"+exc);
            LOGGER.info("Exception caught will uploading"+exc.getMessage());
        }
    }


}

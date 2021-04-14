package com.stackroute.searchservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stackroute.searchservice.model.Book;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

/**
 * This is Repository of the Search Service.
 */

@Repository
public class BookRepository {

    private final String INDEX = "bookdata";
    private final String TYPE = "books";

    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper;

    public BookRepository(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * This method is used to insert the data into the elastic search server.
     * 
     * @param book This take the book Object to insert the data.
     * @return
     */
    public Book insertBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        Map<String, Object> dataMap = objectMapper.convertValue(book, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, book.getId()).source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest);
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (java.io.IOException ex) {
            ex.getLocalizedMessage();
        }
        return book;
    }

    /**
     * This method is used to get the book by its Id.
     * 
     * @param id
     * @return
     */
    public Map<String, Object> getBookById(String id) {
        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        return sourceAsMap;
    }

    /**
     * This method is used to get all the books that are stored in the elastic
     * search server.
     * 
     * @return
     */
    public Map<String, Object> getAllBook() {
        GetRequest getRequest = new GetRequest();
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        return sourceAsMap;
    }

    /**
     * This method is used to update the book by its id.
     * 
     * @return
     */
    public Map<String, Object> updateBookById(String id, Book book) {
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id).fetchSource(true); // Fetch Object after its
                                                                                            // update
        Map<String, Object> error = new HashMap<>();
        error.put("Error", "Unable to update book");
        try {
            String bookJson = objectMapper.writeValueAsString(book);
            updateRequest.doc(bookJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
            Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
            return sourceAsMap;
        } catch (JsonProcessingException e) {
            e.getMessage();
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        return error;
    }

    /**
     * This method is used to delete the book by its id.
     * 
     * @return
     */
    public void deleteBookById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
    }

    /**
     * This method is used to find the books by Keyword. Here whatever the keyword
     * is passing in the method it will display the book related to that keyword.
     * 
     * @param text
     * @return
     */
    public List<Book> findBook(String text) {
        System.out.println("\n\n\nInside findBook() in book service. THis is what we have recieved : " + text);
        try {
            SearchRequest request = new SearchRequest(INDEX);
            SearchSourceBuilder scb = new SearchSourceBuilder();
            SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(text);
            scb.query(mcb);
            request.source(scb);

            SearchResponse response = restHighLevelClient.search(request);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            List<Book> books = new ArrayList(searchHits.length);
            for (SearchHit hit : searchHits) {
                String sourceAsString = hit.getSourceAsString();
                if (sourceAsString != null) {
                    Gson gson = new Gson();
                    books.add(gson.fromJson(sourceAsString, Book.class));
                }
            }
            System.out.println("\n\nAt the end of findBook() in bookservice : Number of books found - " + books.size());
            return books;
        } catch (IOException ex) {
            System.out.println("Error");
        }
        return Collections.emptyList();
    }

}

package com.stackroute.service;

import com.stackroute.exception.UserAlreadyExists;
import com.stackroute.exception.UserNotFound;
import com.stackroute.model.Notes;
import com.stackroute.model.PreviousRead;
import com.stackroute.model.Role;
import com.stackroute.model.User;
import com.stackroute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/*This is the implementation class of the user service where abstract methods of user service are implemented.*/

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    /*The MongoTemplate is injected into the UserServiceImpl by @Autowired annotation .*/
    @Autowired
    MongoTemplate mongoTemplate;

    /*The UserRepository is injected into the UserServiceImpl by @Autowired annotation .*/
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*This method will save the user details.*/
    @Override
    public User saveUser(User user) throws UserAlreadyExists {
        return userRepository.save(user);
    }

    /* This method will Retrieve the list of users*/
    @Override
    public List<User> getAllUsers() throws UserNotFound {
        return (List<User>) userRepository.findAll();
    }

    /*This method will update the notes through email*/
    @Override
    public User updateNotes(String email, PreviousRead currentRead) {
        User userUpdated = new User();
        String bookTitle = currentRead.getBookTitle();
        User user = userRepository.findByEmail(email);
        System.out.println("user detail:" + user.getEmail());
        List<PreviousRead> previousRead = user.getPreviousReads();
        if (previousRead != null) {
            for (int i = 0; i < previousRead.size(); i++) {
                if (previousRead.get(i).getBookTitle().equalsIgnoreCase(bookTitle)) {
                    System.out.println("return previous:" + previousRead.get(i));
                    if (previousRead.get(i).getNotes() == null) {
                        return userUpdated = this.updatePreviousRead(email, Arrays.asList(currentRead));
                    } else {
                        List<Notes> previousNotes = previousRead.get(i).getNotes();
                        previousNotes.add(currentRead.getNotes().get(0));
                        return userUpdated = this.updatePreviousRead(email, previousRead);
                    }
                }
            }
            previousRead.add(currentRead);
            return userUpdated = this.updatePreviousRead(email, previousRead);
        } else {
            return userUpdated = this.updatePreviousRead(email, Arrays.asList(currentRead));
        }
    }

    @Override
    public User updateLastPage(String email, PreviousRead currentRead) {
        User userUpdated = new User();
        String bookTitle = currentRead.getBookTitle();
        User user = userRepository.findByEmail(email);
        System.out.println("user detail:" + user.getEmail());
        List<PreviousRead> previousRead = user.getPreviousReads();
        if (previousRead != null) {
            for (int i = 0; i < previousRead.size(); i++) {
                if (previousRead.get(i).getBookTitle().equalsIgnoreCase(bookTitle)) {
                    System.out.println("return previous:" + previousRead.get(i));
//                    List<Notes> previousNotes = previousRead.get(i).getNotes();
//                    previousNotes.add(currentRead.getNotes().get(0));
                    previousRead.get(i).setLastpage(currentRead.getLastpage());
                    return userUpdated = this.updatePreviousRead(email, previousRead);
                }
            }
            previousRead.add(currentRead);
            return userUpdated = this.updatePreviousRead(email, previousRead);
        } else {
            return userUpdated = this.updatePreviousRead(email, Arrays.asList(currentRead));
        }
    }

    @Override
    public Integer getLastPage(String userEmail, String bookTitle) {
        User user = userRepository.findByEmail(userEmail);
        Integer lastPageRead=2;
        List<PreviousRead> previousRead = user.getPreviousReads();
        if (previousRead != null) {
            for (int i = 0; i < previousRead.size(); i++) {
                if (previousRead.get(i).getBookTitle().equalsIgnoreCase(bookTitle)) {
                    System.out.println("return previous:" + previousRead.get(i));
                    lastPageRead = previousRead.get(i).getLastpage();
                }
            }
        }
        return lastPageRead;
    }

    /*This method will update the previous reads of the user using email*/
    public User updatePreviousRead(String email, List<PreviousRead> previousRead) {
        System.out.println("updating previous read with:" + previousRead);
        User userUpdated = new User();
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update();
        update.set("previousReads", previousRead);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        userUpdated = mongoTemplate.findAndModify(query, update, options, User.class);
        return userUpdated;
    }

    /*This method will Find the user by Email*/
    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    /*This method will Retrieve all the notes*/
    @Override
    public List<PreviousRead> getAllNotes(String userEmail) {
        User user = getUserByEmail(userEmail);
        List<PreviousRead> previousReads = user.getPreviousReads();
        return previousReads;
    }

    @Override
    public User getUserDetails(String email) throws UserNotFound {
        User user = userRepository.findByEmail(email);
        return user;
    }
    @Override
    public User changeRoleUser(String email) {
        User updateUser = new User() ;
        User user = userRepository.findByEmail(email);
        return updateUser = this.updateRole(email);
    }

    public User updateRole(String email){
        User userUpdated = new User();
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update();
        update.set("role",Role.Premium);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        userUpdated = mongoTemplate.findAndModify(query, update, options, User.class);


        return userUpdated;
    }


}
package com.stackroute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.config.BcryptGenerator;
import com.stackroute.exception.UserAlreadyExists;
import com.stackroute.exception.UserNotFound;
import com.stackroute.model.PreviousRead;
import com.stackroute.model.Role;
import com.stackroute.model.User;
import com.stackroute.service.RabbitMqSender;
import com.stackroute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/*This is a Controller class , it will register the user credentials.*/
/*this class is annotated with @RestController annotation, @RequestMapping */
@RestController
@RequestMapping("api/v1/register")
@CrossOrigin(origins = "*")
public class UserController {
    private UserService userService;
    private RabbitMqSender rabbitMqSender;
    private BcryptGenerator bcryptGenerator;

    /*The service class and rabbitmq sender is injected into the controller by @Autowired annotation .*/
    @Autowired
    public UserController(UserService userService, RabbitMqSender rabbitMqSender, BcryptGenerator bcryptGenerator) {
        this.userService = userService;
        this.rabbitMqSender = rabbitMqSender;
        this.bcryptGenerator = bcryptGenerator;
    }

    @Value("${app.message}")
    private String message;

    /*This method is used to get the list of the users*/
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() throws UserNotFound {
        return new ResponseEntity<List<User>>((List<User>) userService.getAllUsers(), HttpStatus.OK);
    }

    /*This method is used to upload the image of the user*/
    @PostMapping("/upload")
    public ResponseEntity<User> uploadImage(@RequestParam(value = "myfile") MultipartFile myfile, @RequestParam("item") String item) throws IOException, UserAlreadyExists {
        User user = new ObjectMapper().readValue(item, User.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setPic(myfile.getBytes());
        user.setName(myfile.getOriginalFilename());
        user.setType(myfile.getContentType());
        String password = bcryptGenerator.passwordEncoder(user.getPassword());
        user.setPassword(password);
        user.setConfirmpassword(password);
        user.setRole(Role.NormalUser);
        User savedUser = userService.saveUser(user);
        rabbitMqSender.send(savedUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /*This is method is used to upload notes*/
    @PutMapping("/uploadnotes/{userEmail}")
    public void uploadNotes(@PathVariable String userEmail, @RequestBody PreviousRead previousRead) throws IOException, UserAlreadyExists, UserNotFound {
        userService.updateNotes(userEmail, previousRead);
    }

    /*This method is used to retrieve all the notes*/
    @GetMapping("/allnotes/{userEmail}")
    public ResponseEntity<List<PreviousRead>> getAllNotes(@PathVariable String userEmail) {
        return new ResponseEntity<List<PreviousRead>>(userService.getAllNotes(userEmail), HttpStatus.OK);
    }

    @GetMapping("/userdetails/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws UserNotFound {
        User userDetails = userService.getUserDetails(email);
        return new ResponseEntity<User>(userDetails, HttpStatus.OK);
    }

    @PutMapping("/user/role/{userEmail}")
    public void changeRoleOfUser( @PathVariable String userEmail)  throws UserNotFound{
        System.out.println("helloWorld");
        User user = userService.changeRoleUser(userEmail);

    }

    @PutMapping("/user/previousRead/{userEmail}")
    public void setLastPage(@PathVariable String userEmail, @RequestBody PreviousRead previousRead) throws UserAlreadyExists {
        userService.updateLastPage(userEmail, previousRead);
    }

    @GetMapping("/lastPage/{userEmail}/{bookTitle}")
    public ResponseEntity<Integer> getLastePage(@PathVariable String userEmail, @PathVariable String bookTitle) {
        Integer lastPageRead = userService.getLastPage(userEmail, bookTitle);
        return new ResponseEntity<Integer>(lastPageRead, HttpStatus.OK);
    }

    @GetMapping("/noauth")
    public String getstringmsg() {
        return "Hello and welcome";
    }

    //    @GetMapping("/prevent")
//    public ResponseEntity<User> user(@AuthenticationPrincipal OAuth2User principal) throws UserAlreadyExists {
//        System.out.println(principal);
//        Map<String, Object> name= Collections.singletonMap("name", principal.getAttribute("name"));
//        Map<String, Object> email=Collections.singletonMap("email", principal.getAttribute("email"));
//        String strName=name.toString().replace("{name=","").replace("}","");
//        String strEmail=email.toString().replace("{email=","").replace("}","");
//        System.out.println(strEmail);
//        System.out.println(strName);
//        User user=new User();
//        user.setUserName(strName);
//        user.setEmail(strEmail);
//        user.setName(strName);
//        user.setUserId(strName);
//        String password = bcryptGenerator.passwordEncoder(UUID.randomUUID().toString());
//        user.setPassword(password);
//        user.setConfirmpassword(password);
//        User savedUser = userService.saveUser(user);
//        rabbitMqSender.send(savedUser);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }
//    @GetMapping("/prevent")
//    public ResponseEntity<User> user(@AuthenticationPrincipal OAuth2User principal) throws UserAlreadyExists {
//        System.out.println(principal);
//        Map<String, Object> name = Collections.singletonMap("name", principal.getAttribute("name"));
//        Map<String, Object> email = Collections.singletonMap("email", principal.getAttribute("email"));
//        String strName = name.toString().replace("{name=", "").replace("}", "");
//        String strEmail = email.toString().replace("{email=", "").replace("}", "");
//        System.out.println(strEmail);
//        System.out.println(strName);
//        User user = new User();
//        user.setUserName(strName);
//        user.setEmail(strEmail);
//        user.setName(strName);
//        user.setUserId(strName);
//        String password = bcryptGenerator.passwordEncoder(UUID.randomUUID().toString());
//        user.setPassword(password);
//        user.setConfirmpassword(password);
//        User savedUser = userService.saveUser(user);
////        rabbitMqSender.send(savedUser);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }
}
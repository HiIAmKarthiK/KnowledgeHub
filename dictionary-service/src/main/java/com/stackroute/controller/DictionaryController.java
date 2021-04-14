package com.stackroute.controller;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/* This is a Controller class named UserController.
The class is annotated with @RestController, @RequestMapping, and @CrossOrigin annotations.
This class is responsible for giving the meaning of the provided word.
*/
@RestController
@RequestMapping("api/v1/dictionary")
@CrossOrigin(value = "*")
public class DictionaryController {

    /*This the  RestTemplate which is responsible to consume Restful web Services*/
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*This is the method which helps to get the meaning of the word and return it in the form of ResponseEntity*/
    @GetMapping("/meaning/{word}")
    public ResponseEntity<?> getMeaning(@PathVariable String word) {
        try {
            String url = "https://api.dictionaryapi.dev/api/v2/entries/en_US/" + word;
            Object wordObjects = restTemplate().getForObject(url, Object.class);
            return new ResponseEntity<>(Arrays.asList(wordObjects), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Meaning NOt Found", HttpStatus.NOT_FOUND);
        }

    }
}

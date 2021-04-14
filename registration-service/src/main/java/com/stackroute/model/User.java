package com.stackroute.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/*this class is the model class which defines entity user object*/
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = User.class)
@Document(collection = "info")
public class User implements Serializable {
    @Id
    private String userId;
    private String userName;
    private String phone;
    private String email;
    private String password;
    private String confirmpassword;
    private String name;
    private String type;
    private byte[] pic;
    private List<PreviousRead> previousReads;
    private Role role;

}
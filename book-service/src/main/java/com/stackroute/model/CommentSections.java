package com.stackroute.model;

import lombok.*;
import org.springframework.stereotype.Component;
/*this class is the model class which defines entity comments object which is sub object of book object
 *  The Getter, Setter and constructor are generated using lombok.*/

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentSections {
    private String comment;
    private String userName;
    private byte[] pic;
    private int rating;
}

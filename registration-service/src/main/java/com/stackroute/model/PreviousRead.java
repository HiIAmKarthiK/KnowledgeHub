package com.stackroute.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

/*this class is the model class which defines entity PreviousRead object which is sub object of User object*/
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PreviousRead {

    private String bookTitle;
    private String bookAuthor;
    private String bookDescription;
    private List<Notes> notes;
    private Integer lastpage;

}

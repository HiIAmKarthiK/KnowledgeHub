package com.stackroute.model;

import lombok.*;
import org.springframework.stereotype.Component;

/*this class is the model class which defines entity Notes object*/
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Notes {

    private String notes;
    private Integer page;
}

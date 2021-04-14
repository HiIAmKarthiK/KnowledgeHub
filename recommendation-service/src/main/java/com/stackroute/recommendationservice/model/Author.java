package com.stackroute.recommendationservice.model;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/*Attributes for author node in neo4j
 * The Getter, Setter and constructor are generated using lombok.*/
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private Long authorId;
    @NonNull
    private String authorName;
}
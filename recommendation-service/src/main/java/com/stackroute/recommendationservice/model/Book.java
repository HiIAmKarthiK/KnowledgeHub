package com.stackroute.recommendationservice.model;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/*Attributes for Book node in neo4j
 * The Getter, Setter and constructor are generated using lombok.*/
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String bookTitle;
    @NonNull
    private String authorName;
    private String description;
    private String bookUrl;
    private Long totalViews;
    private int totalPages;
    private String publisher;
    private Long totalDownloads;
    private String imageUrl;
    private String isbnNumber;
    private String language;
    private String[] bookGenre;
}
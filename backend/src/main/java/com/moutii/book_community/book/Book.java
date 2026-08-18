package com.moutii.book_community.book;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.moutii.book_community.common.BaseEntity;
import com.moutii.book_community.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Book extends BaseEntity {

    @Column(name="TITLE",nullable = false)
    private String title;

    @Column(name="DESCRIPTION",nullable = false)
    private String description;

    @Column(name="RATING", nullable = false)
    private Integer rating;

    @Column(name="IMAGE_NAME")
    private String imageName;

    @Column(name="IMAGE_TYPE",nullable = false)
    private String imageType;

    @Column(name="IMAGE_DATA", nullable = false)
    @Lob
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="CREATOR_ID")
    @JsonManagedReference
    private User creator;


}

package com.moutii.book_community.book;

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

    @Column(name="IMAGE_NAME",nullable = false)
    private String imageName;

    @Column(name="IMAGE_TYPE",nullable = false)
    private String imageType;

    @Column(name="IMAGE_DATA", nullable = false)
    @Lob
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREATOR_ID")
    private User creator;


}

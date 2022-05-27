package com.example.airbnb.business.domain.accommodation;


import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    private String imageUrl;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public Image(String imageUrl, String fileName, Accommodation accommodation) {
        this.imageUrl = imageUrl;
        this.fileName = fileName;
        this.accommodation = accommodation;
    }

    public Image(String imageUrl, String fileName) {
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }

    protected Image() {
    }

    void registAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
        accommodation.getImages().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(imageId, image.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }
}


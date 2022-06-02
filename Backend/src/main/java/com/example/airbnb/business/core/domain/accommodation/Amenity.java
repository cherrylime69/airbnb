package com.example.airbnb.business.core.domain.accommodation;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "amenity")
public class Amenity {

    @Id
    @Column(name = "amenity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_category_id")
    private AmenityCategory amenityCategory;

    protected Amenity(){};
}

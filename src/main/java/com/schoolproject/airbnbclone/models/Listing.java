package com.schoolproject.airbnbclone.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "listing")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "listing")
    private List<Availability> availabilities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private User host;

    @Column(nullable = false)
    private Integer maxPeople;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer extraPeople;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer beds;

    @Column(nullable = false)
    private Integer baths;

    @Column(nullable = false)
    private Integer meters;

    @Column(nullable = false)
    private Boolean living;

    @Column(nullable = false)
    private Boolean party;

    @Column(nullable = false)
    private Boolean pets;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private Boolean wifi;

    @Column(nullable = false)
    private Boolean ac;

    @Column(nullable = false)
    private Boolean heat;

    @Column(nullable = false)
    private Boolean kitchen;

    @Column(nullable = false)
    private Boolean tv;

    @Column(nullable = false)
    private Boolean parking;

    @Column(nullable = false)
    private Boolean elevator;
}

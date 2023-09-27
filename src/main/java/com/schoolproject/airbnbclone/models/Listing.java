package com.schoolproject.airbnbclone.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "listing")
    private List<Image> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private User host;
}

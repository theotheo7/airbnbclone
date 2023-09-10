package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}

package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.exceptions.InternalServerErrorException;
import com.schoolproject.airbnbclone.models.Image;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private void createDirectory(String directory, Integer id) {
        File uploadsDirectory = new File(directory);

        if (!uploadsDirectory.exists()) {
            if (!uploadsDirectory.mkdir()) {
                throw new RuntimeException();
            }
        }

        File userDirectory = new File(directory + id + "/");

        if (!userDirectory.exists()) {
            if (!userDirectory.mkdir()) {
                throw new RuntimeException();
            }
        }
    }

    @Transactional
    public void uploadUserImage(User user, MultipartFile multipartFile) {

        String USER_UPLOADS_DIRECTORY = "Uploads/Users/";
        this.createDirectory(USER_UPLOADS_DIRECTORY, user.getId());

        if (multipartFile != null) {

            String fileName = multipartFile.getOriginalFilename();
            String filePath = USER_UPLOADS_DIRECTORY + user.getId() + "/" + fileName;

            File file = new File(filePath);

            try {

                if (file.createNewFile()) {

                    try {

                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(multipartFile.getBytes());
                        fileOutputStream.close();
                        Image image = new Image();
                        image.setPath(filePath);
                        image.setUser(user);
                        Image userImage;
                        userImage = image;
                        this.imageRepository.save(userImage);

                    } catch (IOException e) {
                        throw new InternalServerErrorException(e.getMessage(), InternalServerErrorException.MEDIA_UPLOAD_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            } catch (IOException e) {
                throw new InternalServerErrorException(e.getMessage(), InternalServerErrorException.MEDIA_UPLOAD_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {

            String PLACEHOLDER_IMAGE_PATH = "src/main/resources/PlaceholderImage.png";
            File placeholderFile = new File(PLACEHOLDER_IMAGE_PATH);
            String fileName = placeholderFile.getName();
            String filePath = USER_UPLOADS_DIRECTORY + user.getId() + "/" + fileName;
            File userFile = new File(filePath);
            try {
                Files.copy(placeholderFile.toPath(), userFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image();
                image.setPath(filePath);
                image.setUser(user);
                Image userImage;
                userImage = image;
                this.imageRepository.save(userImage);
            } catch (IOException e) {
                throw new InternalServerErrorException(e.getMessage(), InternalServerErrorException.MEDIA_UPLOAD_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    @Transactional
    public void uploadListingImages(Listing listing, MultipartFile[] multipartFiles) {

        String LISTING_UPLOADS_DIRECTORY = "Uploads/Listings/";
        this.createDirectory(LISTING_UPLOADS_DIRECTORY, listing.getId());

        List<Image> listingImages = new ArrayList<>();

        if (multipartFiles != null) {

            for (MultipartFile multipartFile : multipartFiles) {

                String fileName = multipartFile.getOriginalFilename();
                String filePath = LISTING_UPLOADS_DIRECTORY + listing.getId() + "/" + fileName;

                File file = new File(filePath);

                try {

                    if (file.createNewFile()) {

                        try {

                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.write(multipartFile.getBytes());
                            fileOutputStream.close();
                            Image image = new Image();
                            image.setPath(filePath);
                            image.setListing(listing);
                            listingImages.add(image);

                        } catch (IOException e) {
                            throw new InternalServerErrorException(e.getMessage(), InternalServerErrorException.MEDIA_UPLOAD_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                } catch (IOException e) {
                    throw new InternalServerErrorException(e.getMessage(), InternalServerErrorException.MEDIA_UPLOAD_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
        }
        else {

            String PLACEHOLDER_IMAGE_PATH = "src/main/resources/PlaceholderImage.png";
            File multipartFile = new File(PLACEHOLDER_IMAGE_PATH);
            String fileName = multipartFile.getName();
            String filePath = LISTING_UPLOADS_DIRECTORY + listing.getId() + "/" + fileName;
            File listingFile = new File(filePath);
            try {
                Files.copy(multipartFile.toPath(), listingFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image();
                image.setPath(filePath);
                image.setListing(listing);
                listingImages.add(image);
            } catch (IOException e) {
                throw new InternalServerErrorException(e.getMessage(), InternalServerErrorException.MEDIA_UPLOAD_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        this.imageRepository.saveAll(listingImages);

    }

}

package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.exceptions.InternalServerErrorException;
import com.schoolproject.airbnbclone.models.Image;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
import java.util.Objects;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final String LISTING_UPLOADS_DIRECTORY = "Uploads/Listings/";
    private final String USER_UPLOADS_DIRECTORY = "Uploads/Users/";

    private void createDirectory(String directory, Integer id) {
        File uploadsDirectory = new File(directory);

        if (!uploadsDirectory.exists()) {
            if (!uploadsDirectory.mkdirs()) {
                throw new RuntimeException("What the fck");
            }
        }

        File userDirectory = new File(directory + id + "/");

        if (!userDirectory.exists()) {
            if (!userDirectory.mkdirs()) {
                throw new RuntimeException("Giati den doulevei");
            }
        }
    }

    @Transactional
    public void uploadUserImage(User user, MultipartFile multipartFile) {

        this.createDirectory(this.USER_UPLOADS_DIRECTORY, user.getId());

        if (multipartFile != null) {

            String fileName = multipartFile.getOriginalFilename();
            String filePath = this.USER_UPLOADS_DIRECTORY + user.getId() + "/" + fileName;

            File file = new File(filePath);

            try {

                if (file.createNewFile()) {

                    try {

                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(multipartFile.getBytes());
                        fileOutputStream.close();
                        Image image = new Image();
                        image.setPath(filePath);
                        user.setImage(image);
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
            String filePath = this.USER_UPLOADS_DIRECTORY + user.getId() + "/" + fileName;
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

        this.createDirectory(this.LISTING_UPLOADS_DIRECTORY, listing.getId());

        List<Image> listingImages = new ArrayList<>();

        if (multipartFiles != null) {

            for (MultipartFile multipartFile : multipartFiles) {

                String fileName = multipartFile.getOriginalFilename();
                String filePath = this.LISTING_UPLOADS_DIRECTORY + listing.getId() + "/" + fileName;

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
            String filePath = this.LISTING_UPLOADS_DIRECTORY + listing.getId() + "/" + fileName;
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

    @Transactional
    public void updateListingImages(Listing listing, MultipartFile[] multipartFiles) {

        File listingDirectory = new File(this.LISTING_UPLOADS_DIRECTORY + listing.getId() + "/");

        if (listingDirectory.exists()) {
            for (File file : Objects.requireNonNull(listingDirectory.listFiles()))
                if (!file.delete()) {
                    throw new InternalServerErrorException(InternalServerErrorException.MEDIA_DELETION_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            this.imageRepository.deleteByListing(listing.getId());
        }

        this.uploadListingImages(listing, multipartFiles);
    }

    @Transactional
    public void updateUserImage(User user, MultipartFile multipartFile) {

        File userDirectory = new File(this.USER_UPLOADS_DIRECTORY + user.getId() + "/");

        if (userDirectory.exists()) {
            for (File file : Objects.requireNonNull(userDirectory.listFiles()))
                if (!file.delete()) {
                    throw new InternalServerErrorException(InternalServerErrorException.MEDIA_DELETION_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            this.imageRepository.deleteByUser(user.getId());
        }

        this.uploadUserImage(user, multipartFile);
    }

    @Transactional
    public void deleteListingImages(Integer listingID) {

        this.imageRepository.deleteByListing(listingID);

        File listingDirectory = new File(this.LISTING_UPLOADS_DIRECTORY + listingID + "/");

        if (listingDirectory.exists()) {
            try {
                FileUtils.deleteDirectory(listingDirectory);
            } catch (IOException e) {
                throw new InternalServerErrorException(e.getMessage(), InternalServerErrorException.MEDIA_DELETION_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Transactional
    public void deleteUserImage(Integer userID) {

        this.imageRepository.deleteByUser(userID);

        File userDirectory = new File(this.USER_UPLOADS_DIRECTORY + userID + "/");

        if (userDirectory.exists()) {
            try {
                FileUtils.deleteDirectory(userDirectory);
            } catch (IOException e) {
                throw new InternalServerErrorException(e.getMessage(), InternalServerErrorException.MEDIA_DELETION_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }



}

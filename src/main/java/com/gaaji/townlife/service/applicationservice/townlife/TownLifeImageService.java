package com.gaaji.townlife.service.applicationservice.townlife;

import org.springframework.web.multipart.MultipartFile;

public interface TownLifeImageService {

    void upload(String townLifeId, MultipartFile... multipartFiles);
    void delete();
    void modify();

}

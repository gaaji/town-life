package com.gaaji.townlife.service.adapter.aws;

import com.gaaji.townlife.global.exceptions.internalServer.exception.NullValueException;
import com.gaaji.townlife.global.exceptions.internalServer.exception.TownLifeAwsS3Exception;
import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Client {

    String[] uploadFile(MultipartFile... multipartFiles) throws TownLifeAwsS3Exception, NullValueException;
    void deleteFile(String... originFileSrcs) throws TownLifeAwsS3Exception, NullValueException;

}

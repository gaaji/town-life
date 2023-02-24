package com.gaaji.townlife.service.adapter.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gaaji.townlife.global.exceptions.internalServer.InternalErrorCode;
import com.gaaji.townlife.global.exceptions.internalServer.exception.NullValueException;
import com.gaaji.townlife.global.exceptions.internalServer.exception.TownLifeAwsS3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.gaaji.townlife.global.utils.validation.ValidateRequireValue.validateRequireNonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3ClientImpl implements AwsS3Client {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private static final String AWS_IMG_ROOT_DIR = "townlife/img/";

    @Override
    public String[] uploadFile(MultipartFile... multipartFiles) throws TownLifeAwsS3Exception, NullValueException {
        String[] uploadSrcs = new String[multipartFiles.length];

        for (int i = 0; i < multipartFiles.length; i++) {
            uploadSrcs[i] = uploadFile(multipartFiles[i]);
        }

        return uploadSrcs;
    }

    @Override
    public void deleteFile(String... originFileSrcs) throws TownLifeAwsS3Exception, NullValueException {
        for (String originFileSrc : originFileSrcs) {
            deleteFile(originFileSrc);
        }
    }

    public String uploadFile(MultipartFile multipartFile) throws TownLifeAwsS3Exception, NullValueException {
        try {
            validateRequireNonNull(multipartFile);

            String extension = this.getExtension(multipartFile);
            File file = convertMultipartFileToFile(multipartFile);

            return upload(file, extension);

        } catch (IOException | AmazonClientException e) {
            throw new TownLifeAwsS3Exception(InternalErrorCode.ERROR_FILE_UPLOAD);
        }
    }

    private void deleteFile(String originFileUrl) throws TownLifeAwsS3Exception, NullValueException {
        try {
            validateRequireNonNull(originFileUrl);

            int index = originFileUrl.lastIndexOf(AWS_IMG_ROOT_DIR);
            String key = originFileUrl.substring(index);

            deleteS3(key);

        } catch (AmazonClientException e) {
            throw new TownLifeAwsS3Exception(InternalErrorCode.ERROR_FILE_DELETE);
        }
    }

    private String getExtension(MultipartFile multipartFile) throws NullValueException {
        validateRequireNonNull(multipartFile.getOriginalFilename());

        int extensionIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
        return multipartFile.getOriginalFilename().substring(extensionIndex);
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException, TownLifeAwsS3Exception, NullValueException {
        validateRequireNonNull(multipartFile.getOriginalFilename());

        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return file;
        }
        throw new TownLifeAwsS3Exception(InternalErrorCode.ERROR_FILE_CONVERT);
    }

    private String upload(File file, String extension) throws NullValueException {
        String fileName = AWS_IMG_ROOT_DIR + UUID.randomUUID() + extension;

        return putS3(file, fileName);
    }

    private String putS3(File file, String key) throws NullValueException {
        validateRequireNonNull(file, key);

        amazonS3.putObject(new PutObjectRequest(bucket, key, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        removeLocalFile(file); // Delete local file instance

        return getS3(bucket, key);
    }

    private void removeLocalFile(File file) throws NullValueException {
        validateRequireNonNull(file);

        boolean isDeleted = file.delete();
        if ( !isDeleted ) {
            log.error("AwsS3Client ERROR:: Remove Local File");
        }
    }

    private String getS3(String bucket, String key) throws NullValueException {
        validateRequireNonNull(bucket, key);

        return amazonS3.getUrl(bucket, key).toString();
    }

    private void deleteS3(String key) throws TownLifeAwsS3Exception, NullValueException {
        validateRequireNonNull(key);

        if (!amazonS3.doesObjectExist(bucket, key)) {
            throw new TownLifeAwsS3Exception(InternalErrorCode.ERROR_FILE_DELETE);
        }

        amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
    }

}

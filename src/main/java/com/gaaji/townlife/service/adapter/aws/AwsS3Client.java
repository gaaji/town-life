package com.gaaji.townlife.service.adapter.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3Client {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(String fileDir, MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) return null;
        try {
            String extension = this.getExtension(multipartFile);
            File file = convertMultipartFileToFile(multipartFile).orElseThrow();
            return upload(fileDir, file, extension);
        } catch (IOException e) {
            throw new AmazonS3Exception("S3 upload error");
        }
    }

    public String updateFile(String fileDir, MultipartFile multipartFile, String originFileUrl) {
        if(multipartFile.isEmpty() || originFileUrl==null || originFileUrl.isBlank()) return null;
        try {
            String extension = this.getExtension(multipartFile);
            File file = convertMultipartFileToFile(multipartFile).orElseThrow();

            deleteFile(fileDir, originFileUrl); // Delete S3 Object for update file

            return upload(fileDir, file, extension);
        } catch (IOException e) {
            throw new AmazonS3Exception("S3 update error");
        }
    }

    public void deleteFile(String fileDir, String originFileUrl) {
        int index = originFileUrl.lastIndexOf("upload/"+ fileDir +"/");
        String key = originFileUrl.substring(index);
        deleteS3(key);
    }

    private String getExtension(MultipartFile multipartFile) {
        Objects.requireNonNull(multipartFile.getOriginalFilename());

        int extensionIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
        return multipartFile.getOriginalFilename().substring(extensionIndex);
    }

    private Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    private String upload(String dir, File file, String extension) {
        String fileName = "upload/"+ dir +"/"+ UUID.randomUUID() + extension;
        return putS3(file, fileName);
    }

    private String putS3(File file, String key) {
        amazonS3.putObject(new PutObjectRequest(bucket, key, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        removeFile(file); // Delete local file instance

        return getS3(bucket, key);
    }

    private void removeFile(File file) {
        if ( !file.delete() ) {
            log.error("AwsS3Client:: cannot success removing local file");
        }
    }

    private String getS3(String bucket, String key) {
        return amazonS3.getUrl(bucket, key).toString();
    }

    private void deleteS3(String key) {
        if (!amazonS3.doesObjectExist(bucket, key)) {
            throw new AmazonS3Exception("Object " +key+ " does not exist!");
        }
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
    }

}

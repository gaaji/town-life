package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.adapter.aws.AwsS3Client;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TownLifeImageServiceImplTest {

    @Autowired
    private AwsS3Client awsS3Client;
    @Autowired
    private TownLifeRepository townLifeRepository;
    @Autowired
    private TownLifeImageServiceImpl townLifeImageService;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TownLifeSaveServiceImpl townLifeSaveService;

    private Category category;

    void init_category() {
        category = categoryRepository.save(
                Category.create(UUID.randomUUID().toString(), true, "테스트_카테고리입니다.", TownLifeType.POST));
    }

    private final String authorId = "user01";
    private final String townId = "town01";
    private final String title = "테스트 게시글";
    private final String text = "테스트 게시글 내용입니다.";
    private final String location = "서울특별시 테스트구 테스트동";
    private static String townLifeId;

    void init_post_town_life() {
        init_category();

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                .categoryId(category.getId()).authorId(authorId).townId(townId)
                .title(title).text(text).location(location).build();
        townLifeId = townLifeSaveService.save(dto).getId();
    }

    @Test
    @Order(100)
    @DisplayName("이미지 파일 1개 정상 업로드")
    void upload_one_image_file() throws Exception {
        init_post_town_life();

        int[] orderIndexes = { 1 };
        MockMultipartFile multipartFile = getOneMockImage();
        townLifeImageService.upload(townLifeId, orderIndexes, multipartFile);
    }

    @Test
    @Order(200)
    @DisplayName("이미지 파일 1개 정상 삭제")
    void delete_one_image_file() {
        townLifeImageService.deleteAll(townLifeId);
    }

    @Test
    @Order(300)
    @DisplayName("이미지 파일 4개 정상 업로드")
    void upload_four_image_files() throws Exception {
        init_post_town_life();

        int[] orderIndexes = { 1, 2, 3, 4 };
        MultipartFile[] multipartFiles = getFourMockImages();
        townLifeImageService.upload(townLifeId, orderIndexes, multipartFiles);
    }

    @Test
    @Order(400)
    @DisplayName("이미지 파일 4개 정상 삭제")
    void delete_four_image_files() {
        townLifeImageService.deleteAll(townLifeId);
    }

    private MockMultipartFile getOneMockImage() throws IOException {
        /*MockMultipartHttpServletRequest multipartHttpServletRequest = new MockMultipartHttpServletRequest();*/ // controller test 시 사용
        String mockFileName = "image_test";
        String realFileName = "image_test_1.jpg";
        String contentType = "image";
        String filePath = "src/test/resources/image/"+realFileName;

        return getMockMultipartFile(mockFileName, realFileName, contentType, filePath);
    }

    private MultipartFile[] getFourMockImages() throws IOException {
        /*MockMultipartHttpServletRequest multipartHttpServletRequest = new MockMultipartHttpServletRequest();*/ // controller test 시 사용

        MultipartFile[] multipartFiles = new MultipartFile[4];

        for(int i=1; i<=4; i++) {
            String mockFileName = "image_test_"+i;
            String realFileName = "image_test_"+i+".jpg";
            String contentType = "image";
            String filePath = "src/test/resources/image/"+realFileName;

            multipartFiles[i-1] = getMockMultipartFile(mockFileName, realFileName, contentType, filePath);
        }

        return multipartFiles;
    }

    private MockMultipartFile getMockMultipartFile(String fileName, String originFileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, originFileName, contentType, fileInputStream);
    }
}
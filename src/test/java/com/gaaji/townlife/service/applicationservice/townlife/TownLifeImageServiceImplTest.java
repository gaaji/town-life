package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceAuthorizationException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceSaveException;
import com.gaaji.townlife.service.adapter.aws.AwsS3Client;
import com.gaaji.townlife.service.controller.townlife.dto.AttachedImageDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
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
import java.util.List;
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("동네생활 게시글 이미지 서비스 테스트")
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
    @Autowired
    private TownLifeFindServiceImpl townLifeFindService;

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
    void test_upload_one_image_file() throws Exception {
        init_post_town_life();

        int[] orderIndexes = { 1 };
        MockMultipartFile multipartFile = getOneMockImage();
        List<AttachedImageDto> dto = townLifeImageService.upload(townLifeId, authorId, orderIndexes, multipartFile);

        System.out.println(dto);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(1, dto.size());
    }

    @Test
    @Order(200)
    @DisplayName("이미지 파일 1개 정상 삭제")
    void test_delete_one_image_file() {
        List<AttachedImageDto> dto = townLifeImageService.deleteAll(townLifeId, authorId);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(0, dto.size());
    }

    @Test
    @Order(300)
    @DisplayName("이미지 파일 4개 정상 업로드")
    void test_upload_four_image_files() throws Exception {
        init_post_town_life();

        int[] orderIndexes = { 1, 2, 3, 4 };
        MultipartFile[] multipartFiles = getFourMockImages();
        List<AttachedImageDto> dto = townLifeImageService.upload(townLifeId, authorId, orderIndexes, multipartFiles);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(4, dto.size());
    }

    @Test
    @Order(350)
    @DisplayName("이미지 파일 4개 정상 수정")
    void test_update_four_image_files() throws Exception {
        int[] orderIndexes = {1, 2, 3, 4};
        MultipartFile[] fourMockImages = getFourMockImages();
        List<AttachedImageDto> dto = townLifeImageService.update(townLifeId, authorId, orderIndexes, fourMockImages);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(4, dto.size());
    }

    @Test
    @Order(360)
    @DisplayName("이미지 업로드 이후 게시글 조회")
    void test_find_town_life_after_upload() {
        TownLifeDetailDto dto = townLifeFindService.findById(townLifeId);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getAttachedImages());
    }

    @Test
    @Order(400)
    @DisplayName("이미지 파일 4개 정상 삭제")
    void test_delete_four_image_files() {
        List<AttachedImageDto> dto = townLifeImageService.deleteAll(townLifeId, authorId);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(0, dto.size());
    }

    @Test
    @Order(500)
    @DisplayName("이미지 업로드 실패 - content type 에러")
    void test_validate_wrong_content_type() throws Exception {
        int[] orderIndexes = {1};
        MockMultipartFile multipartFile = getOneMockImageWrongContentType();

        ResourceSaveException ex = Assertions.assertThrows(ResourceSaveException.class,
                () -> townLifeImageService.upload(townLifeId, authorId, orderIndexes, multipartFile));
        Assertions.assertEquals(ApiErrorCode.IMAGE_CONTENT_TYPE_ERROR.getErrorCode(), ex.getErrorCode());
    }

    @Test
    @Order(600)
    @DisplayName("이미지 업로드 실패 - orderIndex와 multipartFiles 배열 길이 불일치")
    void test_validate_array_length() throws Exception {
        int[] orderIndexes = { 1, 2 };
        MultipartFile[] multipartFiles = getFourMockImages();

        ResourceSaveException ex = Assertions.assertThrows(ResourceSaveException.class,
                () -> townLifeImageService.upload(townLifeId, authorId, orderIndexes, multipartFiles));
        Assertions.assertEquals(ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST.getErrorCode(), ex.getErrorCode());
    }

    @Test
    @Order(700)
    @DisplayName("이미지 업로드 실패 - 게시자와 요청자 ID 불일치")
    void test_validate_authorization_modifying() throws Exception {
        int[] orderIndexes = { 1 };
        MockMultipartFile multipartFile = getOneMockImage();

        String authId = "wrong_author";
        ResourceAuthorizationException ex = Assertions.assertThrows(ResourceAuthorizationException.class,
                () -> townLifeImageService.upload(townLifeId, authId, orderIndexes, multipartFile));
        Assertions.assertEquals(ApiErrorCode.AUTHORIZATION_MODIFY_ERROR.getErrorCode(), ex.getErrorCode());
    }

    @Test
    @Order(800)
    @DisplayName("이미지 업로드 실패 - 데이터 불충분 입력 요청")
    void test_validate_require_value_bad_request() {
        int[] orderIndexes = {1,2,3,4};

        ResourceSaveException ex = Assertions.assertThrows(ResourceSaveException.class,
                () -> townLifeImageService.upload(townLifeId, authorId, orderIndexes, (MultipartFile) null));
        Assertions.assertEquals(ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST.getErrorCode(), ex.getErrorCode());
    }

    @Test
    @Order(900)
    @DisplayName("이미지 삭제 실패 - 데이터 불충분 입력 요청")
    void test_validate_require_value_bad_request_remove() {
        init_post_town_life();

        ResourceRemoveException ex = Assertions.assertThrows(ResourceRemoveException.class,
                () -> townLifeImageService.deleteAll(townLifeId, authorId));
        Assertions.assertEquals(ApiErrorCode.IMAGE_NOT_FOUND.getErrorCode(), ex.getErrorCode());
    }


    ///////////////////////////////////////////////////////////////////////////////

    private MockMultipartFile getOneMockImage() throws IOException {
        /*MockMultipartHttpServletRequest multipartHttpServletRequest = new MockMultipartHttpServletRequest();*/ // controller test 시 사용
        String mockFileName = "image_test";
        String realFileName = "image_test_1.jpg";
        String contentType = "image";
        String filePath = "src/test/resources/image/"+realFileName;

        return getMockMultipartFile(mockFileName, realFileName, contentType, filePath);
    }

    private MockMultipartFile getOneMockImageWrongContentType() throws IOException {
        /*MockMultipartHttpServletRequest multipartHttpServletRequest = new MockMultipartHttpServletRequest();*/ // controller test 시 사용
        String mockFileName = "image_test";
        String realFileName = "image_test_1.jpg";
        String contentType = "text";
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
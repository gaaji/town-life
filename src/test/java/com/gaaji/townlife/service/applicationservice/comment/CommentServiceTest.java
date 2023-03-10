package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.config.TestBeanConfig;
import com.gaaji.townlife.global.exceptions.api.exception.BadRequestException;
import com.gaaji.townlife.global.exceptions.api.exception.NotYourResourceException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.global.exceptions.internalServer.exception.NullValueException;
import com.gaaji.townlife.global.exceptions.internalServer.exception.TownLifeAwsS3Exception;
import com.gaaji.townlife.service.adapter.aws.AwsS3Client;
import com.gaaji.townlife.service.adapter.gaaji.AuthServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.TownServiceClient;
import com.gaaji.townlife.service.applicationservice.admin.AdminCategorySaveService;
import com.gaaji.townlife.service.applicationservice.townlife.TownLifeSaveService;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveRequestDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveResponseDto;
import com.gaaji.townlife.service.controller.comment.dto.*;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.comment.ChildComment;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.domain.comment.ParentComment;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Import(TestBeanConfig.class)
@Transactional
public class CommentServiceTest {
    @Autowired
    AdminCategorySaveService adminCategorySaveService;
    @Autowired
    AuthServiceClient authServiceClient;
    @Autowired
    TownServiceClient townServiceClient;
    @Autowired
    TownLifeSaveService townLifeSaveService;
    @Autowired
    CommentSaveService commentSaveService;
    @Autowired
    CommentFindService commentFindService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private TownLifeRepository townLifeRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void ??????_??????() {
        String commenterId = "user1";
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);

        String text = "???????????????.";
        String location = townLife.getContent().getLocation();
        CommentSaveResponseDto dto = commentSaveService.saveParent(commenterId, townLife.getId(), CommentSaveRequestDto.create(commenterId, location, text));

        Assertions.assertTrue(townLife.getComments().size() > 0);
        Assertions.assertEquals(commenterId, dto.getCommenterId());
        Assertions.assertEquals(text, dto.getText());
        Assertions.assertEquals(townLife.getId(), dto.getPostId());
        Assertions.assertEquals(location, dto.getLocation());
        Assertions.assertNotNull(dto.getCreatedAt());
    }

    @Test
    void ?????????_??????() {
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);
        ParentComment parentComment = randomParentComment(townLife);

        String commenterId = randomString();
        String text = randomString();
        String location = randomString();
        CommentSaveResponseDto dto = commentSaveService.saveChild(commenterId, townLife.getId(), parentComment.getId(), CommentSaveRequestDto.create(commenterId, location, text));

        Assertions.assertTrue(parentComment.getChildren().size() > 0);
        Assertions.assertEquals(commenterId, dto.getCommenterId());
        Assertions.assertEquals(text, dto.getText());
        Assertions.assertEquals(townLife.getId(), dto.getPostId());
        Assertions.assertEquals(location, dto.getLocation());
        Assertions.assertNotNull(dto.getCreatedAt());
    }

    @Test
    void ??????_??????() {
        TownLife townLife = randomTownLife(randomCategory());
        final int commentsCount = 10;
        List<ParentComment> parents = IntStream.range(0, commentsCount)
                .mapToObj(i -> randomParentComment(townLife))
                .sorted(Comparator.comparing(Comment::getId))
                .collect(Collectors.toList());
        List<ParentCommentListDto> dtos = commentFindService.findParentCommentListByTownLifeId(townLife.getId()).stream()
                .sorted(Comparator.comparing(ParentCommentListDto::getId))
                .collect(Collectors.toList());
        Assertions.assertEquals(commentsCount, dtos.size());
        IntStream.range(0, commentsCount).forEach(i -> {
            ParentComment expected = parents.get(i);
            ParentCommentListDto actual = dtos.get(i);
            Assertions.assertEquals(expected.getId(), actual.getId());
            Assertions.assertEquals(expected.getUserId(), actual.getCommenterId());
            Assertions.assertEquals(expected.getContent().getText(), actual.getText());
            Assertions.assertEquals(expected.getContent().getLocation(), actual.getLocation());
            Assertions.assertEquals(expected.getContent().getImageSrc(), actual.getImageSrc());
        });

    }

    @Test
    void ?????????_??????() {
        TownLife townLife = randomTownLife(randomCategory());
        final int parentCommentsCount = 10;
        final int childCommentsCount = 100;
        IntStream.range(0, parentCommentsCount)
                .mapToObj(i -> randomParentComment(townLife))
                .sorted(Comparator.comparing(Comment::getId))
                .map(parent -> {
                    List<ChildComment> expectedList = IntStream.range(0, childCommentsCount)
                            .mapToObj(i -> randomChildComment(parent))
                            .sorted(Comparator.comparing(Comment::getId)).collect(Collectors.toList());
                    List<ChildCommentListDto> actualList = commentFindService.findChildCommentListByParentCommentId(townLife.getId(), parent.getId(), null, null);
                    Assertions.assertEquals(childCommentsCount, actualList.size());
                    Assertions.assertEquals(expectedList.size(), actualList.size());
                    IntStream.range(0, childCommentsCount)
                            .forEach(i -> {
                                ChildComment expected = expectedList.get(i);
                                ChildCommentListDto actual = actualList.get(i);
                                Assertions.assertEquals(expected.getId(), actual.getId());
                                Assertions.assertEquals(expected.getParent().getId(), actual.getParentId());
                                Assertions.assertEquals(expected.getUserId(), actual.getCommenterId());
                                Assertions.assertEquals(expected.getContent().getLocation(), actual.getLocation());
                                Assertions.assertEquals(expected.getContent().getText(), actual.getText());
                                Assertions.assertEquals(expected.getContent().getImageSrc(), actual.getImageSrc());
                            });
                    return parent;
                }).findFirst().map(parent -> {
                    Assertions.assertThrows(BadRequestException.class, () -> commentFindService.findChildCommentListByParentCommentId(townLife.getId(), parent.getId(), null, 3));
                    Assertions.assertThrows(BadRequestException.class, () -> commentFindService.findChildCommentListByParentCommentId(townLife.getId(), parent.getId(), "some", null));
                    final int startInclusive = 10;
                    final int endExclusive = 21;
                    List<ChildComment> children = parent.getChildren().stream().sorted(Comparator.comparing(Comment::getId)).collect(Collectors.toList());
                    List<ChildComment> expectedList = children.subList(startInclusive, endExclusive);

                    List<ChildCommentListDto> actualList = commentFindService.findChildCommentListByParentCommentId(townLife.getId(), parent.getId(), children.get(startInclusive - 1).getId(), endExclusive - startInclusive);
                    Assertions.assertEquals(endExclusive - startInclusive, actualList.size());
                    Assertions.assertEquals(expectedList.size(), actualList.size());
                    IntStream.range(0, endExclusive-startInclusive)
                            .forEach(i -> {
                                ChildComment expected = expectedList.get(i);
                                ChildCommentListDto actual = actualList.get(i);
                                Assertions.assertEquals(expected.getId(), actual.getId());
                                Assertions.assertEquals(expected.getParent().getId(), actual.getParentId());
                                Assertions.assertEquals(expected.getUserId(), actual.getCommenterId());
                                Assertions.assertEquals(expected.getContent().getLocation(), actual.getLocation());
                                Assertions.assertEquals(expected.getContent().getText(), actual.getText());
                                Assertions.assertEquals(expected.getContent().getImageSrc(), actual.getImageSrc());
                            });
                    return null;
                });
    }

    @Test
    void ??????_?????????_??????_??????() {
        String myId = randomString();
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);
        List<Comment> myComments = new ArrayList<>();
        ParentComment someonesParentComment = randomParentComment(townLife);
        ParentComment myParentComment = randomParentComment(townLife, myId);
        myComments.add(myParentComment);
        randomChildComment(myParentComment);
        randomChildComment(someonesParentComment);
        myComments.add(randomChildComment(myParentComment, myId));
        myComments.add(randomChildComment(someonesParentComment, myId));


        List<CommentListDto> dtos = commentFindService.findListByUserId(myId).stream().sorted(Comparator.comparing(CommentListDto::getId)).collect(Collectors.toList());
        Assertions.assertEquals(3, dtos.size());
        IntStream.range(0, 3).forEach(i -> {
            Comment expected = myComments.get(i);
            CommentListDto actual = dtos.get(i);
            Assertions.assertEquals(expected.getId(), actual.getId());
            if(expected instanceof ChildComment)Assertions.assertEquals(((ChildComment) expected).getParent().getId(), actual.getParentId());
            Assertions.assertEquals(expected.getUserId(), actual.getCommenterId());
            Assertions.assertEquals(expected.getContent().getLocation(), actual.getLocation());
            Assertions.assertEquals(expected.getContent().getText(), actual.getText());
            Assertions.assertEquals(expected.getContent().getImageSrc(), actual.getImageSrc());
        });
    }

    @Autowired
    CommentModifyService commentModifyService;

    @Test
    void ??????_??????() {
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);
        String commenterId = randomString();
        ParentComment parentComment = randomParentComment(townLife, commenterId);
        String toBeText = randomString();
        String toBeLocation = randomString();
        CommentModifyRequestDto toBeDto = CommentModifyRequestDto.create(toBeText, toBeLocation);

        CommentListDto actual = commentModifyService.modify(commenterId, townLife.getId(), parentComment.getId(), toBeDto);

        Assertions.assertEquals(toBeText, actual.getText());
        Assertions.assertEquals(toBeLocation, actual.getLocation());

        String otherId = randomString();
        Assertions.assertThrows(NotYourResourceException.class, () -> commentModifyService.modify(otherId, townLife.getId(), parentComment.getId(), toBeDto));
    }

    @Autowired
    CommentRemoveService commentRemoveService;
    @Test
    void ??????_??????() {
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);
        String commenterId = randomString();
        ParentComment parentComment = randomParentComment(townLife, commenterId);

        commentLikeService.like(commenterId, townLife.getId(), parentComment.getId());
        commentRemoveService.remove(commenterId, townLife.getId(), parentComment.getId());
        Assertions.assertTrue(commentRepository.findById(parentComment.getId()).isEmpty());
        Comment comment = commentRepository.findAllById(Collections.singleton(parentComment.getId())).get(0);

        Assertions.assertNotNull(comment.getDeletedAt());
        Assertions.assertEquals(0, comment.getLikes().size());
    }
    @Autowired
    CommentLikeService commentLikeService;


    @Test
    void ?????????_?????????_??????() {
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);
        ParentComment parentComment = randomParentComment(townLife);
        ChildComment childComment = randomChildComment(parentComment);
        String userId = randomString();

        commentLikeService.like(userId, townLife.getId(), parentComment.getId());
        Assertions.assertEquals(1, parentComment.getLikes().size());
        Assertions.assertEquals(parentComment.getId(), parentComment.getLikes().get(0).getComment().getId());
        commentLikeService.like(userId, townLife.getId(), childComment.getId());
        Assertions.assertEquals(1, childComment.getLikes().size());
        // ???????????? ??????
        commentLikeService.like(userId, townLife.getId(), childComment.getId());
        Assertions.assertEquals(1, childComment.getLikes().size());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentLikeService.like(userId, "wrong id", parentComment.getId()));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentLikeService.like(userId, townLife.getId(), "wrong id"));
    }

    @Test
    void ??????_?????????_??????() {
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);
        ParentComment parentComment = randomParentComment(townLife);
        ChildComment childComment = randomChildComment(parentComment);
        String userId = randomString();

        commentLikeService.like(userId, townLife.getId(), parentComment.getId());
        Assertions.assertEquals(1, parentComment.getLikes().size());

        commentLikeService.unlike(userId, townLife.getId(), parentComment.getId());
        Assertions.assertEquals(0, parentComment.getLikes().size());

        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(0, parentComment.getLikes().size()));
    }

    @Autowired
    AwsS3Client awsS3Client;
    @Autowired
    CommentImageService commentImageService;

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("?????? ????????? ????????? ?????????")
    class CommentImageSaveTest {
        String townLifeAuthorId;
        String parentCommentCommenterId;
        String childCommentCommenterId;
        Category category;
        TownLife townLife;
        ParentComment parentComment;
        ChildComment childComment;
        List<String> shouldBeDeleted = new ArrayList<>();

        @BeforeEach
        void beforeEach() {
            townLifeAuthorId = randomString();
            parentCommentCommenterId = randomString();
            childCommentCommenterId = randomString();
            category = randomCategory();
            townLife = randomTownLife(category, townLifeAuthorId);
            parentComment = randomParentComment(townLife, parentCommentCommenterId);
            childComment = randomChildComment(parentComment, childCommentCommenterId);
        }

        @AfterEach
        void afterEach() {
            shouldBeDeleted.forEach(s -> {
                try {
                    awsS3Client.deleteFile(s);
                } catch (TownLifeAwsS3Exception | NullValueException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        @Test
        void ??????_?????????_??????() throws IOException {
            CommentImageSaveResponseDto dto = commentImageService.save(parentCommentCommenterId, townLife.getId(), parentComment.getId(), getMockMultipartFile());
            shouldBeDeleted.add(dto.getSrc());
            Assertions.assertNotNull(dto);
            Assertions.assertNotNull(dto.getCommentId());
            Assertions.assertNotNull(dto.getSrc());
            Assertions.assertEquals(parentComment.getId(), dto.getCommentId());
        }

        @Test
        void ??????_?????????_??????() throws IOException {
            CommentImageSaveResponseDto dto = commentImageService.save(parentCommentCommenterId, townLife.getId(), parentComment.getId(), getMockMultipartFile());
            commentImageService.remove(parentCommentCommenterId, townLife.getId(), parentComment.getId());

            Assertions.assertThrows(TownLifeAwsS3Exception.class, () -> awsS3Client.deleteFile(dto.getSrc()));
        }

        private MockMultipartFile getMockMultipartFile() throws IOException {
            return new MockMultipartFile("image_test", "image_test_1.jpg", "image", new FileInputStream("src/test/resources/image/" + "image_test_1.jpg"));
        }
    }

    private ChildComment randomChildComment(ParentComment parentComment, String commenterId) {
        CommentSaveResponseDto dto = commentSaveService.saveChild(commenterId, parentComment.getTownLife().getId(), parentComment.getId(), CommentSaveRequestDto.create(commenterId, randomString(), randomString()));
        return (ChildComment) commentRepository.findById(dto.getId()).get();
    }

    private ChildComment randomChildComment(ParentComment parentComment) {
        return randomChildComment(parentComment, randomString());
    }

    private ParentComment randomParentComment(TownLife townLife, String commenterId) {
        CommentSaveResponseDto dto = commentSaveService.saveParent(commenterId, townLife.getId(), CommentSaveRequestDto.create(commenterId, randomString(), randomString()));
        return (ParentComment) commentRepository.findById(dto.getId()).get();
    }

    private ParentComment randomParentComment(TownLife townLife) {
        return randomParentComment(townLife, randomString());
    }

    TownLife randomTownLife(Category category) {
        return randomTownLife(category, randomString());
    }

    private TownLife randomTownLife(Category category, String townLifeAuthor) {
        return createTownLife(
                category.getId(),
                townLifeAuthor,
                randomString(),
                randomString(),
                randomString(),
                randomString()
        );
    }

    TownLife createTownLife(String categoryId, String authorId, String townId, String title, String text, String location) {
        TownLifeDetailDto dto = townLifeSaveService.save(
                authorId, townId,
                TownLifeSaveRequestDto.builder()
                        .townId(categoryId)
                        .categoryId(categoryId)
                        .authorId(authorId)
                        .townId(townId)
                        .title(title)
                        .text(text)
                        .location(location)
                        .build()
        );
        return townLifeRepository.findById(dto.getId()).get();
    }

    Category randomCategory() {
        return createCategory(randomString(), true, randomString());
    }

    Category createCategory(String name, boolean defaultCategory, String description) {
        AdminCategorySaveResponseDto dto = adminCategorySaveService.save(new AdminCategorySaveRequestDto(name, defaultCategory, description, TownLifeType.POST));
        return categoryRepository.findById(dto.getId()).get();
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}

package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.service.applicationservice.admin.AdminCategoryFindService;
import com.gaaji.townlife.service.applicationservice.admin.AdminCategoryModifyService;
import com.gaaji.townlife.service.applicationservice.admin.AdminCategoryRemoveService;
import com.gaaji.townlife.service.applicationservice.admin.AdminCategorySaveService;
import com.gaaji.townlife.service.applicationservice.townlife.TownLifeSaveService;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveRequestDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveResponseDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentSaveRequestDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentSaveResponseDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.comment.ParentComment;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
@EmbeddedKafka(
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092"
        }
)
public class CommentServiceTest {
    @Autowired
    AdminCategorySaveService adminCategorySaveService;
    @Autowired
    AdminCategoryFindService adminCategoryFindService;
    @Autowired
    AdminCategoryModifyService adminCategoryModifyService;
    @Autowired
    AdminCategoryRemoveService adminCategoryRemoveService;
    @Autowired
    TownLifeSaveService townLifeSaveService;
    @Autowired
    CommentSaveService commentSaveService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private TownLifeRepository townLifeRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 댓글_추가() {
        String commenterId = "user1";
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);

        String text = "댓글입니다.";
        String location = townLife.getContent().getLocation();
        CommentSaveResponseDto dto = commentSaveService.saveParent(commenterId, townLife.getId(), CommentSaveRequestDto.create(commenterId, location, text));

        Assertions.assertEquals(commenterId, dto.getCommenterId());
        Assertions.assertEquals(text, dto.getText());
        Assertions.assertEquals(townLife.getId(), dto.getTownLifeId());
        Assertions.assertEquals(location, dto.getLocation());
        Assertions.assertNotNull(dto.getCreatedAt());
    }

    @Test
    void 대댓글_추가() {
        Category category = randomCategory();
        TownLife townLife = randomTownLife(category);
        ParentComment parentComment = randomParentComment(townLife);

        String commenterId = randomString();
        String text = randomString();
        String location = randomString();
        CommentSaveResponseDto dto = commentSaveService.saveChild(commenterId, townLife.getId(), parentComment.getId(), CommentSaveRequestDto.create(commenterId, location, text));

        Assertions.assertEquals(commenterId, dto.getCommenterId());
        Assertions.assertEquals(text, dto.getText());
        Assertions.assertEquals(townLife.getId(), dto.getTownLifeId());
        Assertions.assertEquals(location, dto.getLocation());
        Assertions.assertNotNull(dto.getCreatedAt());
    }

    private ParentComment randomParentComment(TownLife townLife) {
        String commenterId = randomString();
        CommentSaveResponseDto dto = commentSaveService.saveParent(commenterId, townLife.getId(), CommentSaveRequestDto.create(commenterId, randomString(), randomString()));
        return (ParentComment) commentRepository.findById(dto.getId()).get();
    }

    TownLife randomTownLife(Category category) {
        return createTownLife(
                category.getId(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString()
        );
    }

    TownLife createTownLife(String categoryId, String authorId, String townId, String title, String text, String location) {
        TownLifeDetailDto dto = townLifeSaveService.save(
                TownLifeType.POST,
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
        AdminCategorySaveResponseDto dto = adminCategorySaveService.save(new AdminCategorySaveRequestDto(name, defaultCategory, description));
        return categoryRepository.findById(dto.getId()).get();
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}

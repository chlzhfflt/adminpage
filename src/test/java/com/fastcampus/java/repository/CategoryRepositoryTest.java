package com.fastcampus.java.repository;

import com.fastcampus.java.JavaApplicationTests;
import com.fastcampus.java.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class CategoryRepositoryTest extends JavaApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create(){
        String type = "COMPUTER";
        String title = "컴퓨터";
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        Category category = new Category();
        category.setType(type);
        category.setTitle(title);
        category.setCreatedAt(createdAt);
        category.setCreatedBy(createdBy);

        Category newCategory = categoryRepository.save(category);
        Assertions.assertNotNull(newCategory); // 비어있는지 확인
        Assertions.assertEquals(newCategory.getType(),type); // 타입 확인
        Assertions.assertEquals(newCategory.getTitle(),title); // 타이틀 확인
    }

    @Test
    public void read(){
        String type = "COMPUTER";

//        Optional<Category> optionalCategory = categoryRepository.findById(1L);
        Optional<Category> optionalCategory = categoryRepository.findByType(type); // 쿼리메서드 작성한 findByType 사용

        // select * from category where type = 'COMPUTER' 이런식으로 검색되는 경우가 가장 많음

        optionalCategory.ifPresent(c ->{ // c : category
            Assertions.assertEquals(c.getType(), type); // 타입 확인

            System.out.println(c.getId());
            System.out.println(c.getType());
            System.out.println(c.getTitle());
        });

    }
}

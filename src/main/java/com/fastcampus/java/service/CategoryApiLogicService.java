package com.fastcampus.java.service;

import com.fastcampus.java.controller.CrudController;
import com.fastcampus.java.ifs.CrudInterface;
import com.fastcampus.java.model.entity.AdminUser;
import com.fastcampus.java.model.entity.Category;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.Pagination;
import com.fastcampus.java.model.network.request.CategoryApiRequest;
import com.fastcampus.java.model.network.response.AdminUserApiResponse;
import com.fastcampus.java.model.network.response.CategoryApiResponse;
import com.fastcampus.java.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryApiLogicService implements CrudInterface<CategoryApiRequest, CategoryApiResponse> {

    private final CategoryRepository categoryRepository;


    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {

        CategoryApiRequest body = request.getData();

        Category category = Category.builder()
                .type(body.getType())
                .title(body.getTitle())
                .build();

        Category newCategory = categoryRepository.save(category);

        return Header.OK(response(newCategory));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return categoryRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        
        CategoryApiRequest body = request.getData();
        
        return categoryRepository.findById(body.getId())
                .map(entityCategory -> {
                    entityCategory
                            .setType(body.getType())
                            .setTitle(body.getTitle())
                            ;
                    return entityCategory;
                })
                .map(newEntityCategory -> categoryRepository.save(newEntityCategory))
                .map(category -> response(category))
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private CategoryApiResponse response(Category category){

        CategoryApiResponse body = CategoryApiResponse.builder()
                .id(category.getId())
                .type(category.getType())
                .title(category.getTitle())
                .build();

        return body;
    }

    public Header<List<CategoryApiResponse>> search(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable); // 해당 page에 들어있는 Category정보들을 Page<Category> 형태로 리턴
        List<CategoryApiResponse> categoryApiResponseList = categories.stream()
                .map(category -> response(category))
                .collect(Collectors.toList());

        // page정보 추가
        Pagination pagination = Pagination.builder()
                .totalPages(categories.getTotalPages())                 // 전체 페이지 수
                .totalElements(categories.getTotalElements())           // 전체 elements 수
                .currentPage(categories.getNumber())                    // 현재 페이지
                .currentElements(categories.getNumberOfElements())      // 현재 페이지에 몇개의 data가 들어있는지
                .build();


        return Header.OK(categoryApiResponseList,pagination);
    }
}

package com.fastcampus.java.service;

import com.fastcampus.java.ifs.CrudInterface;
import com.fastcampus.java.model.entity.AdminUser;
import com.fastcampus.java.model.entity.User;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.Pagination;
import com.fastcampus.java.model.network.request.AdminUserApiRequest;
import com.fastcampus.java.model.network.response.AdminUserApiResponse;
import com.fastcampus.java.model.network.response.UserApiResponse;
import com.fastcampus.java.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserApiLogicService implements CrudInterface<AdminUserApiRequest, AdminUserApiResponse> {
    
    private final AdminUserRepository adminUserRepository;
    
    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {
        
        AdminUserApiRequest body = request.getData();
        
        AdminUser adminUser = AdminUser.builder()
                .account(body.getAccount())
                .password(body.getPassword())
                .status(body.getStatus())
                .role(body.getRole())
                .lastLoginAt(LocalDateTime.now())
                .loginFailCount(0)
                .passwordUpdatedAt(LocalDateTime.now()) // password를 update했을때의 시간을 가져다가 그 값 대입
                .registeredAt(LocalDateTime.now())
                .unregisteredAt(LocalDateTime.now())
                .build();

        AdminUser newAdminUser = adminUserRepository.save(adminUser);
        
        return Header.OK(response(newAdminUser));
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        return adminUserRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        
        AdminUserApiRequest body = request.getData();
        
        return adminUserRepository.findById(body.getId())
                .map(entityAdminUser -> {
                    entityAdminUser
                            .setAccount(body.getAccount())
                            .setPassword(body.getPassword())
                            .setStatus(body.getStatus())
                            .setRole(body.getRole())
                            .setLastLoginAt(body.getLastLoginAt())
                            .setLoginFailCount(body.getLoginFailCount())
                            .setPasswordUpdatedAt(body.getPasswordUpdatedAt())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setUnregisteredAt(body.getUnregisteredAt())
                            ;
                    return entityAdminUser;
                })
                .map(newEntityAdminUser -> adminUserRepository.save(newEntityAdminUser))
                .map(updateAdminUser -> response(updateAdminUser))
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return adminUserRepository.findById(id)
                .map(adminUser -> {
                    adminUserRepository.delete(adminUser);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private AdminUserApiResponse response(AdminUser adminUser){

        AdminUserApiResponse adminUserApiResponse = AdminUserApiResponse.builder()
                .id(adminUser.getId())
                .account(adminUser.getAccount())
                .password(adminUser.getPassword())
                .status(adminUser.getStatus())
                .role(adminUser.getRole())
                .lastLoginAt(adminUser.getLastLoginAt())
                .loginFailCount(adminUser.getLoginFailCount())
                .passwordUpdatedAt(adminUser.getPasswordUpdatedAt())
                .registeredAt(adminUser.getRegisteredAt())
                .unregisteredAt(adminUser.getUnregisteredAt())
                .build();

        return adminUserApiResponse;
    }

    public Header<List<AdminUserApiResponse>> search(Pageable pageable) {
        Page<AdminUser> adminUsers = adminUserRepository.findAll(pageable); // 해당 page에 들어있는 AdminUser정보들을 Page<AdminUser> 형태로 리턴
        List<AdminUserApiResponse> adminUserApiResponseList = adminUsers.stream()
                .map(adminUser -> response(adminUser))
                .collect(Collectors.toList());

        // List<UserApiResponse> 모양의 데이터를
        // Header<List<UserApiResponse>> 형태로 변경해야함

        // page정보 추가
        Pagination pagination = Pagination.builder()
                .totalPages(adminUsers.getTotalPages())                 // 전체 페이지 수
                .totalElements(adminUsers.getTotalElements())           // 전체 elements 수
                .currentPage(adminUsers.getNumber())                    // 현재 페이지
                .currentElements(adminUsers.getNumberOfElements())      // 현재 페이지에 몇개의 data가 들어있는지
                .build();


        return Header.OK(adminUserApiResponseList,pagination);
    }
}

package com.fastcampus.java.model.network.response;

import com.fastcampus.java.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApiResponse {

    private Long id;

    private String account;

    private String password;

    private UserStatus status;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private List<OrderGroupApiResponse> orderGroupApiResponseList; // 2. 어떠한 주문내역이 있는지 -> 사용자는 여러가지 주문내역을 가질 수 있기에 List
                                                                    // 그룹내역도 UserApiResponse 안에 포함되어야함
}

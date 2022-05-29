package com.fastcampus.java.model.network.response;

import com.fastcampus.java.model.entity.OrderGroup;
import com.fastcampus.java.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderInfoApiResponse {

    private UserApiResponse userApiResponse; // 1. 어떠한 유저에 대해서

    // UserApiResponse
    // 2. 어떠한 주문내역이 있는지 -> 사용자는 여러가지 주문내역을 가질 수 있기에 List
    // 그룹내역도 UserApiResponse 안에 포함되어야함

    // OrderGroupApiResponse
    // 3. 주문내역에 담겨있는 여러가지 Item들 / 모든 item이 넘어가면 안되기에
    // OrderGroupApiResponse 내에 있어야하므로 OrderGroupApiResponse 에 변수 생성

}

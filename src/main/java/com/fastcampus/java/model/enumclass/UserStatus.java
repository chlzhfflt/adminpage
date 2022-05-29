package com.fastcampus.java.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    REGISTERED(0,"등록","사용자 등록상태"), // indexId, 외부에 노출되는 값, 조금 더 상세한 설명 값
    UNREGISTERED(1,"해지","사용자 해지상태");

    private Integer id;
    private String title;
    private String description;
}

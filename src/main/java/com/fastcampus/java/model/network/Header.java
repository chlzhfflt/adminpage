package com.fastcampus.java.model.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Header<T> {
    
    // api 통신시간
//    @JsonProperty("transaction_time") // Json형태가 만들어질 때 해당 이름으로 생성 , 하지만 많은 변수들을 일일히 처리하기 힘듦
    private LocalDateTime transactionTime;
    
    // api 응답코드
    private String resultCode;
    
    // api 부가설명
    private String description;

    // data(body)부에는 제네릭으로 지정한 타입을 가진 오브젝트가 들어감
    private T data;

    private Pagination pagination;
    
    
    // 정상적인 통신일때는 OK
    public static <T> Header<T> OK(){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }
    
    // DATA를 가진 호출일때는 DATA OK
    public static <T> Header<T> OK(T data){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    // 메서드 오버로딩을 통해(매개변수에 따라서 다르게 동작) Page 정보가 추가됨
    public static <T> Header<T> OK(T data, Pagination pagination){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .pagination(pagination)
                .build();
    }

    // 비정상적인 통신일때는 ERROR
    public static <T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }

}

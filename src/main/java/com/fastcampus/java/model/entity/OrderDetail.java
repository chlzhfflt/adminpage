package com.fastcampus.java.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // order_datail 에 자동으로 연결
@ToString(exclude = {"orderGroup","item"})
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String status;
    
    private LocalDateTime arrivalDate;
    
    private Integer quantity;
    
    private BigDecimal totalPrice;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

//    private LocalDateTime orderAt;

    // OrderDetail N : 1 Item
    @ManyToOne
    private Item item;
//    private Long itemId;

    // OrderDetail N : 1 OrderGroup
    @ManyToOne
    private OrderGroup orderGroup; // ManyToOne에 있는 orderGroup 변수명은 연결되고자 하는 곳의 mappedBy의 변수명과 일치해야함
//    private Long orderGroupId;


     
    // OneToMany, ManyToOne 설정 설명
    // 변수를 가지고있는 클래스에 가서 본인(오더디테일)을 기준으로 자신이 N인지 상대방이 무엇인지를 결정
    // 중요한점 : 객체를 연결시켜줘야함
    // 1에 해당하는 클래스에 가서 User입장에선 1이지만 설정된 것은 N으로 받아와야하기 때문에 List로 받아온다는 변수를 선언
    // 어떠한 변수에 매칭시킬것인지 mappedBy를 통해 변수명 맞춰줘야함

}

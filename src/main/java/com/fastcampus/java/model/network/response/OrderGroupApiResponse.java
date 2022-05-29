package com.fastcampus.java.model.network.response;

import com.fastcampus.java.model.enumclass.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderGroupApiResponse {

    private Long id;

    private String status;

    private OrderType orderType;

    private String revAddress;

    private String revName;

    private String paymentType;

    private BigDecimal totalPrice;

    private Integer totalQuantity;

    private LocalDateTime orderAt;

    private LocalDateTime arrivalDate;

    private Long userId;

    private List<ItemApiResponse> itemApiResponseList; // 3. 주문내역에 담겨있는 여러가지 Item들 / 모든 item이 넘어가면 안되기에
                                                        // OrderGroupApiResponse 내에 있어야하므로 OrderGroupApiResponse 에 변수 생성
}

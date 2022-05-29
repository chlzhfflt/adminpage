package com.fastcampus.java.service;

import com.fastcampus.java.ifs.CrudInterface;
import com.fastcampus.java.model.entity.Category;
import com.fastcampus.java.model.entity.OrderDetail;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.Pagination;
import com.fastcampus.java.model.network.request.OrderDetailApiRequest;
import com.fastcampus.java.model.network.response.CategoryApiResponse;
import com.fastcampus.java.model.network.response.OrderDetailApiResponse;
import com.fastcampus.java.repository.ItemRepository;
import com.fastcampus.java.repository.OrderDetailRepository;
import com.fastcampus.java.repository.OrderGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailApiLogicService implements CrudInterface<OrderDetailApiRequest, OrderDetailApiResponse> {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderGroupRepository orderGroupRepository;
    private final ItemRepository itemRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {

        OrderDetailApiRequest body = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .status(body.getStatus())
                .arrivalDate(body.getArrivalDate())
                .quantity(body.getQuantity())
                .totalPrice(body.getTotalPrice())
                .orderGroup(orderGroupRepository.getOne(body.getOrderGroupId()))
                .item(itemRepository.getOne(body.getItemId()))
//                .orderAt(body.getOrderAt())
                .build();

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);

        return Header.OK(response(newOrderDetail));
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        System.out.println("================================================");
        System.out.println("read() id : " + id);
        System.out.println("================================================");
        System.out.println("orderDetailRepository id : " + orderDetailRepository.findById(id));
        return orderDetailRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {

        OrderDetailApiRequest body = request.getData();

        return orderDetailRepository.findById(body.getId())
                .map(entityOrderDetail -> {
                    entityOrderDetail
                            .setStatus(body.getStatus())
                            .setArrivalDate(body.getArrivalDate())
                            .setQuantity(body.getQuantity())
                            .setTotalPrice(body.getTotalPrice())
//                            .setOrderAt(body.getOrderAt())
                            ;
                    return entityOrderDetail;
                })
                .map(newEntityOrderDetail -> orderDetailRepository.save(newEntityOrderDetail))
                .map(newOrderDetail -> response(newOrderDetail))
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

//    @Override
//    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
//
//        OrderDetailApiRequest body = request.getData();
//
//        System.out.println("제발요 : " + orderDetailRepository.findById(body.getId()));
//
//        Optional<OrderDetail> optional = orderDetailRepository.findById(body.getId());
//
//        return optional.map(entityOrderDetail -> {
//                    entityOrderDetail
//                            .setStatus(body.getStatus())
//                            .setArrivalDate(body.getArrivalDate())
//                            .setQuantity(body.getQuantity())
//                            .setTotalPrice(body.getTotalPrice())
//                            .setOrderAt(body.getOrderAt())
//                    ;
//                    return entityOrderDetail;
//                })
//                .map(newEntityOrderDetail -> orderDetailRepository.save(newEntityOrderDetail))
//                .map(newOrderDetail -> response(newOrderDetail))
//                .orElseGet(()->Header.ERROR("데이터 없음"));
//    }

    @Override
    public Header delete(Long id) {
        return orderDetailRepository.findById(id)
                .map(orderDetail -> {
                    orderDetailRepository.delete(orderDetail);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

//    @Override
//    public Header delete(Long id) {
//        System.out.println("delete 실행");
//        System.out.println(id);
//        System.out.println("delete 아이디 : " + orderDetailRepository.findById(id));
//
//        Optional<OrderDetail> optional = orderDetailRepository.findById(id);
//
//        return optional.map(orderDetail -> {
//                    orderDetailRepository.delete(orderDetail);
//                    return Header.OK();
//                })
//                .orElseGet(()->Header.ERROR("데이터 없음"));
//    }

    private OrderDetailApiResponse response(OrderDetail orderDetail){

        OrderDetailApiResponse body = OrderDetailApiResponse.builder()
                .id(orderDetail.getId())
                .status(orderDetail.getStatus())
                .arrivalDate(orderDetail.getArrivalDate())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .orderGroupId(orderDetail.getOrderGroup().getId())
                .itemId(orderDetail.getItem().getId())
//                .orderAt(orderDetail.getOrderAt())
                .build();

        return body;
    }

    public Header<List<OrderDetailApiResponse>> search(Pageable pageable) {
        Page<OrderDetail> orderDetails = orderDetailRepository.findAll(pageable); // 해당 page에 들어있는 Category정보들을 Page<Category> 형태로 리턴
        List<OrderDetailApiResponse> orderDetailApiResponseList = orderDetails.stream()
                .map(orderDetail -> response(orderDetail))
                .collect(Collectors.toList());

        // page정보 추가
        Pagination pagination = Pagination.builder()
                .totalPages(orderDetails.getTotalPages())                 // 전체 페이지 수
                .totalElements(orderDetails.getTotalElements())           // 전체 elements 수
                .currentPage(orderDetails.getNumber())                    // 현재 페이지
                .currentElements(orderDetails.getNumberOfElements())      // 현재 페이지에 몇개의 data가 들어있는지
                .build();


        return Header.OK(orderDetailApiResponseList,pagination);
    }
}

package com.fastcampus.java.repository;

import com.fastcampus.java.JavaApplicationTests;
import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.model.entity.User;
import com.fastcampus.java.model.enumclass.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends JavaApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        //user에 들어갈 값 설정
        String account = "Test03";
        String password = "Test03";
        String status = "REGISTERED";
        String email = "Test03@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
//        LocalDateTime createdAt = LocalDateTime.now();
//        String createdBy = "AdminServer";

        // 객체 생성후 값 대입
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
//        user.setStatus(status); // ★에러나서 주석처리
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
//        user.setCreatedAt(createdAt);
//        user.setCreatedBy(createdBy);

        // Builder패턴 -> Entity 클래스에 @Builder 적용 / @Accessors(chain = true) <- 비슷하게 작동함
        // 생성자의 매개변수갯수나 순서에 상관없이 객체 생성가능
//        User u = User.builder()
//                .account(account)
//                .password(password)
//                .status(status)
//                .email(email)
//                .build();



        // repository 저장 후 값이 잘 들어갔는지 확인
        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);

//        // String sql = insert into user (%s, %s, %d ) value (account, email, age); 같은 쿼리문을 넣어서 매칭함
//        User user = new User();
//        user.setAccount("TestUser01");
//        user.setEmail("TestUser01@gmail.com");
//        user.setPhoneNumber("010-1111-1111");
//        user.setCreatedAt(LocalDateTime.now());
//        user.setCreatedBy("TestUser1");
//
//        User newUser = userRepository.save(user);
//        System.out.println("newUser : " + newUser);
    }

    @Test
    @Transactional
    public void read(){
        //핸드폰 번호로 검색 > Id를 역순으로 정렬해서 가장 마지막에 핸드폰 번호가 매칭이 되는 user를 리턴
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

        // @Accessors(chain = true)
        // setMethod를 여러 줄로 생성할 필요 없이 Chain형태로 이어서 원하는 setMethod를 생성할 수 있다.
//        user
//                .setEmail("")
//                .setPhoneNumber("")
//                .setStatus("");
//
//        User user2 = new User().setAccount("").setEmail("");

        if(user != null){
            user.getOrderGroupList().stream().forEach(orderGroup ->{
//              user.getOrderGroupList().forEach(orderGroup ->{
                System.out.println("---------------주문묶음---------------");
                System.out.println("수령인 : " +orderGroup.getRevName());
                System.out.println("수령지 : " +orderGroup.getRevAddress());
                System.out.println("총금액 : " +orderGroup.getTotalPrice());
                System.out.println("총수량 : " +orderGroup.getTotalQuantity());

                System.out.println("---------------주문상세---------------");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("파트너사 이름 : " +orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리 : " +orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문 상품 : " +orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : " +orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문의 상태 : " +orderDetail.getStatus());
                    System.out.println("도착예정일자 : " +orderDetail.getArrivalDate());


                });

            });
        }

        Assertions.assertNotNull(user);

//        // select * from user where id = ?
//        Optional<User> user = userRepository.findByAccount("TestUser01"); // account 값을 통해 검색
//
//        user.ifPresent(selectUser -> { // user라는 값이 들어있다면
//            selectUser.getOrderDetailList().stream().forEach(detail ->{ // 1:N관계의 리스트형식을 가져와서 스트림 형식으로 돌려서 출력
//                Item item = detail.getItem();
//                System.out.println(item);
//            });
//        });
    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> { // user라는 값이 들어있다면
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser); // 생성인지 업데이트인지 ID값을 통해 확인후 ID가 존재한다면 업데이트
        });
    }

    @Test
    @Transactional // 해당되는 메서드는 실행이 되지만 실질적으로 db에서 데이터가 동작하지 않음 / 다시 rollback해줌
    public void delete(){
        Optional<User> user = userRepository.findById(3L);
        Assertions.assertTrue(user.isPresent()); // true - 1. 값이 있는지 확인후 통과를 해서

        user.ifPresent(selectUser->{
            userRepository.delete(selectUser); // 2. delete가 이루어져야하고
        });

        Optional<User> deleteUser = userRepository.findById(3L); // 삭제가 되었는지 다시 한번 select

        Assertions.assertFalse(deleteUser.isPresent()); // false 3. 값이 delete를 탔기 때문에 반드시 false여야함

    }
}

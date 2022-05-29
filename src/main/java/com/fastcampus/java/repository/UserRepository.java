package com.fastcampus.java.repository;

import com.fastcampus.java.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //핸드폰 번호로 검색 > Id를 역순으로 정렬해서 가장 마지막에 핸드폰 번호가 매칭이 되는 user를 리턴
    User findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);


//    // select * from user where account = ? <- test03, test04같은 account값을 통해 검색
//    Optional<User> findByAccount(String account); // account에 매칭되는 것을 찾겠다 -> QueryMethod
//
//    Optional<User> findByEmail(String email); // email로 검색
//
//    // select * from user where account = ? and email = ?
//    Optional<User> findByAccountAndEmail(String account, String email);
//
//    // 쿼리문을 메서드형태로 작성한다고 해서 쿼리메서드
}

package com.fastcampus.java.controller;

import com.fastcampus.java.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // 메서드의 주소는 중복X, 클래스에 대한 리퀘스트 맵핑 주소는 중복O
public class PostController {

    // Post가 발생할 때
    // 1. HTML <Form> 를 사용할 때
    // ajax 검색할 때 등..

    // RequestBody -> json, xml, multipart-form / text-plain..

//    @RequestMapping(method = RequestMethod.POST, path = "postMethod") // 아래와 동일한 동작
    @PostMapping("/postMethod")
    public SearchParam postMethod(@RequestBody SearchParam searchParam){
        return searchParam;
    }
    
    // 이런식으로 쓸 수도 있지만 레스트API에서는 이러한 용도로 주소를 할당해서 사용하진 않음 
    @PutMapping("/putMethod")
    public void put(){
        
    }
    
    @PatchMapping("/patchMethod")
    public void patch(){
        
    }
}

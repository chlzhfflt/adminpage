package com.fastcampus.java.controller;

import com.fastcampus.java.ifs.CrudInterface;
import com.fastcampus.java.model.network.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller 추상화

// 1. CrudInterface<Req,Res> 를 implements 받는 abstract class CrudController<Req,Res> 생성
// 2. Implement method를 한 후
// 3. @맵핑, @RequestBody, @PathVariable 다 걸어주고
// 4. CrudInterface<Req,Res> 타입의 baseService를 생성한 후
// 5. 각자의 ApiLogicService의 crud와 연결시켜줌
// 6. 해당 Controller로 이동하여 CrudInterface를 implements 받던 것을
//    extends CrudController<해당req,해당res>로 변경시켜주고
// 7. @PostConstruct 어노테이션이 달린
//    public void init(){
//          this.baseService = 해당ApiLogicService; 
//    }
//    위와 같이 연결시켜주면 CrudController의 baseService와 해당ApiLogicService가 연결됨
//    crud 메서드는 자동으로 CrudController와 연결되어 생성하지 않아도 됨


public abstract class CrudController<Req,Res> implements CrudInterface<Req,Res> {

    protected CrudInterface<Req,Res> baseService;

    @Override
    @GetMapping("")
    public Header<List<Res>> search(@PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        return baseService.search(pageable);
    }

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return baseService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return baseService.delete(id);
    }
}

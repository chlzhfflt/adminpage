package com.fastcampus.java.ifs;

import com.fastcampus.java.model.network.Header;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudInterface<Req,Res> {

    Header<List<Res>> search(Pageable pageable);

    Header<Res> create(Header<Req> request);    // todo request object 추가 : 추후에 어떠한 매개변수를 받을 것인지 추가할 예정
    
    Header<Res> read(Long id);
    
    Header<Res> update(Header<Req> request);

    Header delete(Long id);
}

package com.kcs3.panda.domain.mypage.service;

import com.kcs3.panda.domain.mypage.repository.MypageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MypageService {
    private final MypageRepository mypageRepository;

    @Autowired
    public MypageService(MypageRepository mypageRepository) {
        this.mypageRepository = mypageRepository;
    }

    public List<String> getAll(){
        return null;
    }

}

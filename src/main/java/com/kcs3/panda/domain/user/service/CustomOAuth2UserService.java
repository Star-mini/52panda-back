package com.kcs3.panda.domain.user.service;


import com.kcs3.panda.global.config.oauth.CustomOAuth2User;
import com.kcs3.panda.domain.user.dto.UserDTO;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.domain.user.repository.UserRepository;
import com.kcs3.panda.global.config.oauth.GoogleResponse;
import com.kcs3.panda.global.config.oauth.KakaoResponse;
import com.kcs3.panda.global.config.oauth.NaverResponse;
import com.kcs3.panda.global.config.oauth.OAuth2Response;
import com.kcs3.panda.global.exception.CommonException;
import com.kcs3.panda.global.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        log.info("loadUser 서비스 접속 확인");

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("loadUser response 테스트 : "+oAuth2User.getAttributes());

        String registration = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        if("google".equals(registration)){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }else if("kakao".equals(registration)){
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
            log.info("kakao oauth 입니다..");
        }else if("naver".equals(registration)){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
            log.info("naver oauth 입니다..");
        }else{
            log.info("알수없는 oauth 입니다..");
        }



        Optional<User> user = userRepository.findByUserEmail(oAuth2Response.getEmail());


        UserDTO userDTO;
        if(user.isEmpty()){
            log.info("회원정보 없음 db에 저장하자!");

            userRepository.save(User.builder()
                    .userNickname(oAuth2Response.getName())
                    .userEmail(oAuth2Response.getEmail())
                    .build());


            user = userRepository.findByUserEmail(oAuth2Response.getEmail());

            userDTO = UserDTO.builder()
                    .userId(user.get().getUserId())
                    .userNickname(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    .build();


        }else{
            log.info("회원정보 있음 db에서 불러오자!");
            userDTO = UserDTO.builder()
                    .userId(user.get().getUserId())
                    .userNickname(user.get().getUserNickname())
                    .email(user.get().getUserEmail())
                    .build();
        }


        return new CustomOAuth2User(userDTO);
    }
}

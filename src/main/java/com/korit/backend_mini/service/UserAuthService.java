package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.ApiRespDto;
import com.korit.backend_mini.dto.SigninReqDto;
import com.korit.backend_mini.dto.SignupReqDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.entity.UserRole;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.repository.UserRoleRepository;
import com.korit.backend_mini.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserAuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> signup(SignupReqDto signupReqDto) {
        Optional<User> foundUser = userRepository.getUserByEmail(signupReqDto.getEmail());
        if (foundUser.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 이메일 입니다.", null);
        }
        
        Optional<User> foundUserByUsername = userRepository.getUserByUsername(signupReqDto.getUsername());
        if (foundUserByUsername.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 사용자 이름 입니다.", null);
        }

        try {
            Optional<User> optionalUser = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));
            if (optionalUser.isEmpty()) {
                throw new RuntimeException("회원 추가에 실패했습니다.");
            }

            UserRole userRole = UserRole.builder()
                    .userId(optionalUser.get().getUserId())
                    .roleId(3)
                    .build();

            int result = userRoleRepository.addUserRole(userRole);
            if (result != 1) {
                throw new RuntimeException("회원 권한 추가에 실패했습니다.");
            }

            return new ApiRespDto<>("success", "회원가입이 완료되었습니다.", optionalUser.get());

        } catch (Exception e) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다. 다시 시도해주세요.: "+ e.getMessage(), null);
        }
    }

    public ApiRespDto<?> signin(SigninReqDto signinReqDto) {
        Optional<User> foundUser = userRepository.getUserByEmail(signinReqDto.getEmail());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "사용자 정보를 다시 확인해주세요.", null);
        }

        if (!bCryptPasswordEncoder.matches(signinReqDto.getPassword(), foundUser.get().getPassword())) {
            return new ApiRespDto<>("failed", "사용자 정보를 다시 확인해주세요.", null);
        }

        String accessToken = jwtUtils.generateAccessToken(foundUser.get().getUserId().toString());

        return new ApiRespDto<>("success", "로그인 성공", accessToken);
    }
}















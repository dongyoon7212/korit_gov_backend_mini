package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.ApiRespDto;
import com.korit.backend_mini.dto.ChangePasswordReqDto;
import com.korit.backend_mini.dto.ChangeUsernameReqDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiRespDto<?> changePassword(ChangePasswordReqDto changePasswordReqDto, PrincipalUser principalUser) {
        if (!changePasswordReqDto.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다.", null);
        }

        Optional<User> foundUser = userRepository.getUserByUserId(changePasswordReqDto.getUserId());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않은 회원정보입니다.", null);
        }

        User user = foundUser.get();

        if (!bCryptPasswordEncoder.matches(changePasswordReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "현재 비밀번호가 일치하지 않습니다.", null);
        }

        if (bCryptPasswordEncoder.matches(changePasswordReqDto.getNewPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "새 비밀번호가 기존 비밀번호와 일치합니다.", null);
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePasswordReqDto.getNewPassword()));

        int result = userRepository.changePassword(user);
        if (result != 1) {
            return new ApiRespDto<>("faild", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "비밀번호가 변경되었습니다. 다시 로그인 해주세요.", null);
    }

    public ApiRespDto<?> changeUsername(ChangeUsernameReqDto changeUsernameReqDto, PrincipalUser principalUser) {
        if (!changeUsernameReqDto.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다.", null);
        }

        Optional<User> optionalUser = userRepository.getUserByUserId(changeUsernameReqDto.getUserId());
        if (optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 회원 정보입니다.", null);
        }

        Optional<User> foundUser = userRepository.getUserByUsername(changeUsernameReqDto.getUsername());
        if (foundUser.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 사용자 이름입니다.", null);
        }

        User user = optionalUser.get();
        user.setUsername(changeUsernameReqDto.getUsername());

        int result = userRepository.changeUsername(user);
        if (result != 1) {
            return new ApiRespDto<>("failed", "사용자 이름 변경에 실패했습니다. 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "사용자 이름이 변경되었습니다.", null);
    }

}









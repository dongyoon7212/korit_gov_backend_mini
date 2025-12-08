package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.AddBoardReqDto;
import com.korit.backend_mini.dto.ApiRespDto;
import com.korit.backend_mini.dto.BoardRespDto;
import com.korit.backend_mini.dto.ModifyBoardReqDto;
import com.korit.backend_mini.entity.Board;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.BoardRepository;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    public ApiRespDto<?> addBoard(AddBoardReqDto addBoardReqDto, PrincipalUser principalUser) {
        if (!addBoardReqDto.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다.", null);
        }

        Optional<User> foundUser = userRepository.getUserByUserId(addBoardReqDto.getUserId());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않은 회원정보입니다.", null);
        }

        int result = boardRepository.addBoard(addBoardReqDto.toEntity());
        if (result != 1) {
            return new ApiRespDto<>("failed", "게시물 추가에 실패했습니다.", null);
        }

        return new ApiRespDto<>("success", "게시물이 추가되었습니다.", null);
    }

    public ApiRespDto<?> getBoardList() {
        return new ApiRespDto<>("success", "게시물 전체 조회 완료", boardRepository.getBoardList());
    }

    public ApiRespDto<?> getBoardByBoardId(Integer boardId) {
        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(boardId);
        if (foundBoard.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 게시물이 존재하지 않습니다.", null);
        }

        return new ApiRespDto<>("success", "게시물 단건 조회 완료", foundBoard.get());
    }

    public ApiRespDto<?> getBoardListByKeyword(String keyword) {
        return new ApiRespDto<>("success", "게시물 검색 조회 완료", boardRepository.getBoardListByKeyword(keyword));
    }

    public ApiRespDto<?> modifyBoard(ModifyBoardReqDto modifyBoardReqDto, PrincipalUser principalUser) {
        if (!modifyBoardReqDto.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다.", null);
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(modifyBoardReqDto.getBoardId());
        if (foundBoard.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 게시물입니다.", null);
        }

        int result = boardRepository.modifyBoard(modifyBoardReqDto.toEntity());
        if (result != 1) {
            return new ApiRespDto<>("failed", "게시물 수정에 실패했습니다.", null);
        }

        return new ApiRespDto<>("success", "게시물 수정 완료", null);
    }
}





















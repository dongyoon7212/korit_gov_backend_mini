package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.*;
import com.korit.backend_mini.dto.board.*;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.BoardRepository;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public ApiRespDto<?> getBoardInfinite(Integer limit, LocalDateTime cursorCreateDt, Integer cursorBoardId) {
        int limitPlusOne = limit + 1;

        List<BoardRespDto> boardRespDtoList = boardRepository.getBoardInfinite(cursorCreateDt, cursorBoardId, limitPlusOne);

        boolean hasNext = boardRespDtoList.size() > limit;
        if (hasNext) {
            boardRespDtoList = boardRespDtoList.subList(0, limit);
        }

        BoardNextCursor boardNextCursor = null;
        if (!boardRespDtoList.isEmpty() && hasNext) {
            BoardRespDto last = boardRespDtoList.get(boardRespDtoList.size() - 1);
            boardNextCursor = new BoardNextCursor(last.getCreateDt(), last.getBoardId());
        }

        BoardInfiniteRespDto boardInfiniteRespDto = new BoardInfiniteRespDto(boardRespDtoList, hasNext, boardNextCursor);

        return new ApiRespDto<>("success", "무한스크롤 조회 완료", boardInfiniteRespDto);
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

    public ApiRespDto<?> removeBoard(RemoveBoardReqDto removeBoardReqDto, PrincipalUser principalUser) {
        if (!removeBoardReqDto.getUserId().equals(principalUser.getUserId())
                && principalUser.getUserRoles()
                .stream()
                .noneMatch(userRole -> userRole.getRole().getRoleId() == 1)) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다.", null);
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(removeBoardReqDto.getBoardId());
        if (foundBoard.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않은 게시물입니다.", null);
        }

        int result = boardRepository.removeBoard(removeBoardReqDto.getBoardId());
        if (result != 1) {
            return new ApiRespDto<>("failed", "게시물 삭제에 실패했습니다.", null);
        }

        return new ApiRespDto<>("success", "게시물 삭제 완료", null);
    }

    public ApiRespDto<?> getBoardListByUserId(Integer userId) {
        Optional<User> foundUser = userRepository.getUserByUserId(userId);
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원정보가 존재하지 않습니다.", null);
        }

        return new ApiRespDto<>("success", "유저 게시물 리스트 조회 완료", boardRepository.getBoardListByUserId(userId));
    }
}





















package com.korit.backend_mini.repository;

import com.korit.backend_mini.dto.board.BoardRespDto;
import com.korit.backend_mini.entity.Board;
import com.korit.backend_mini.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    @Autowired
    private BoardMapper boardMapper;

    public int addBoard(Board board) {
        return boardMapper.addBoard(board);
    }

    public List<BoardRespDto> getBoardList() {
        return boardMapper.getBoardList();
    }

    public Optional<BoardRespDto> getBoardByBoardId(Integer boardId) {
        return boardMapper.getBoardByBoardId(boardId);
    }

    public List<BoardRespDto> getBoardListByKeyword(String keyword) {
        return boardMapper.getBoardListByKeyword(keyword);
    }

    public int modifyBoard(Board board) {
        return boardMapper.modifyBoard(board);
    }

    public int removeBoard(Integer boardId) {
        return boardMapper.removeBoard(boardId);
    }

    public List<BoardRespDto> getBoardListByUserId(Integer userId) {
        return boardMapper.getBoardListByUserId(userId);
    }

    public List<BoardRespDto> getBoardInfinite(LocalDateTime cursorCreateDt, Integer cursorBoardId, Integer limitPlusOne) {
        return boardMapper.getBoardInfinite(cursorCreateDt, cursorBoardId, limitPlusOne);
    }
}













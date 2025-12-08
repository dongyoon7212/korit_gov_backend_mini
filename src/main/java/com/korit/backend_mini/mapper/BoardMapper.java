package com.korit.backend_mini.mapper;

import com.korit.backend_mini.dto.BoardRespDto;
import com.korit.backend_mini.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    int addBoard(Board board);
    List<BoardRespDto> getBoardList();
    Optional<BoardRespDto> getBoardByBoardId(Integer boardId);
    List<BoardRespDto> getBoardListByKeyword(String keyword);
    int modifyBoard(Board board);
    int removeBoard(Integer boardId);
}

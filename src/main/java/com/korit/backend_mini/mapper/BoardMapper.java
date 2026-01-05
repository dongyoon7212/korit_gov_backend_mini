package com.korit.backend_mini.mapper;

import com.korit.backend_mini.dto.board.BoardRespDto;
import com.korit.backend_mini.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
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
    List<BoardRespDto> getBoardListByUserId(Integer userId);
    List<BoardRespDto> getBoardInfinite(LocalDateTime cursorCreateDt, Integer cursorBoardId, Integer limitPlusOne);

}

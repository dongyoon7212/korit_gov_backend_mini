package com.korit.backend_mini.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardNextCursor {
    private LocalDateTime cursorCreateDt;
    private Integer cursorBoardId;
}

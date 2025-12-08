package com.korit.backend_mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardRespDto {
    private Integer boardId;
    private String title;
    private String content;
    private Integer userId;
    private String username;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
}

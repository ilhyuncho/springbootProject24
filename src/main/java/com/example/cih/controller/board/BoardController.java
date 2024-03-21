package com.example.cih.controller.board;

import com.example.cih.dto.board.BoardResponseDTO;
import com.example.cih.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@AllArgsConstructor
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    @Operation(summary="게시판 목록")
    public Iterable<BoardResponseDTO> boardList() {

        return boardService.list();
    }
}

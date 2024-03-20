package com.example.cih.controller.board;

import com.example.cih.domain.board.Board;
import com.example.cih.dto.board.BoardResponseDTO;
import com.example.cih.repository.board.BoardRepository;
import com.example.cih.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board")
@AllArgsConstructor
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    @Operation(summary="게시판 목록")
    public List<Board> boardList() {

        return boardService.list();
    }
}

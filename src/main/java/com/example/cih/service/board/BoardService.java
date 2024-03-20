package com.example.cih.service.board;

import com.example.cih.domain.board.Board;
import com.example.cih.dto.board.BoardResponseDTO;

import java.util.List;

public interface BoardService {

    List<BoardResponseDTO> list();
}

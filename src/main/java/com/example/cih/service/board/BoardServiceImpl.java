package com.example.cih.service.board;

import com.example.cih.domain.board.Board;
import com.example.cih.dto.board.BoardResponseDTO;
import com.example.cih.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<BoardResponseDTO> list() {

        List<Board> findResult = boardRepository.findAll();

        List<BoardResponseDTO> result = findResult.stream()
                .map(board -> modelMapper.map(board, BoardResponseDTO.class))
                .collect(Collectors.toList());

        return result;
    }
}

package com.example.cih.service.board;

import com.example.cih.domain.board.Board;
import com.example.cih.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    @Override
    public List<Board> list() {

        List<Board> result = boardRepository.findAll();

        return result;
    }
}

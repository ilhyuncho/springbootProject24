package com.example.cih.domain.board;

import com.example.cih.repository.board.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Board board = Board.builder()
                    .title("title.." + i)
                    .content("content.." + i)
                    .writer("user" + (i % 10))
                    .build();

            Board result = boardRepository.save(board);

            log.info("BNO: " + result.getBno());
        });
    }


}
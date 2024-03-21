package com.example.cih.domain.board;

import com.example.cih.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BoardRepository extends JpaRepository<Board, Long> {


}

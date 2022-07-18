package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest // 테스트 클래스임을 명시
class BoardRepositoryImplTest {

    // 이 클래스에는 생성자 주입이 불가능함

    @Autowired // 만약에 BoardRepository 인터페이스를 구현한 구현체가 하나 더 있고 @Repo도 붙어있다면 @Qualifier 로 명시해줘야함.
    BoardRepository repository;


    @Test
    @DisplayName("300개의 게시물을 삽입해야 한다.")
    void saveTest() { // 테스트 클래스에서는 접근제한자 쓰지 말아라.

        Board board;
        for (int i = 1; i <= 300; i++) {
            board = new Board();
            board.setTitle("제목" + i);
            board.setWriter("길동이" + i);
            board.setContent("안녕하세요~~~~"+i);
            repository.save(board);
        }
    }


    @Test
    @DisplayName("전체 게시물을 조회하고 반환된 리스트의 사이즈는 300이어야 한다.")
    void findAllTest() {

        List<Board> boardList = repository.findAll();
        boardList.forEach(b -> System.out.println(b));

        assertEquals(300, boardList.size());
    }

}
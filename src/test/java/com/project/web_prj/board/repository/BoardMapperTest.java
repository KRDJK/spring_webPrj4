package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.common.paging.Page;
import com.project.web_prj.common.search.Search;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {

    @Autowired
    BoardMapper mapper;

//    @Test
//    @DisplayName("제목으로 검색된 목록을 조회해야 한다.")
//    void searchByTitleTest() {
//        Search search = new Search(
//                new Page(1, 10), "title", "랄랄라"
//        );
//
//        mapper.findAll2(search).forEach(System.out::println);
//    }


    @Test
    @DisplayName("새로운 게시물이 DB에 등록되어야 한다.")
    @Transactional
    @Rollback
    void saveTest() {
        Board board = new Board();
        board.setTitle("mapper되나");
        board.setContent("gd");
        board.setWriter("dz");

        System.out.println("board = " + board);

        boolean flag = mapper.save(board);

        assertTrue(flag);
    }


    @Test
    @DisplayName("삭제할 게시물 번호를 입력하면 게시물이 삭제되어야 한다.")
    @Transactional
    @Rollback
    void removeTest() {
        boolean flag = mapper.remove(1L);

        assertTrue(flag);
    }


    @Test
    @DisplayName("수정할 게시물 내용을 주면 해당 게시물이 수정되어야 한다.")
    void modifyTest() {
        Board board = new Board();
        board.setBoardNo(311L);
        board.setWriter("수정된 작성자");
        board.setTitle("수정된 제목");
        board.setContent("수정된 내용");

        boolean flag = mapper.modify(board);

        assertTrue(flag);
    }


    @Test
    @DisplayName("전체 게시물 내용을 조회해야 한다.")
    void findAllTest() {
        Page page = new Page();
        List<Board> boardList = mapper.findAll(page);

        boardList.forEach(System.out::println);
    }


    @Test
    @DisplayName("특정 게시물 번호를 입력하면 해당 게시물이 상세조회 되어야 한다.")
    void findOneTest() {
        Board board = mapper.findOne(311L);

        System.out.println("board = " + board);
    }


    @Test
    @DisplayName("총 게시물 수가 정확히 구해져야 한다.")
    void getTotalCountTest() {
        int totalCount = mapper.getTotalCount();

        System.out.println("totalCount = " + totalCount);
    }


    @Test
    @DisplayName("게시물 번호를 주면 해당 게시물의 조회수가 정상적으로 올라가야 한다.")
    void upViewCountTest() {
        mapper.upViewCount(311L);

        Board board = mapper.findOne(311L);

        assertTrue(board.getViewCnt() == 3);
    }
}
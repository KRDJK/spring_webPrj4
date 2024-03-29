package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.dto.ValidateMemberDTO;
import com.project.web_prj.common.paging.Page;
import com.project.web_prj.common.search.Search;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
        Search search = new Search();
        search.setType("title");
        search.setKeyword("30");
        int totalCount = mapper.getTotalCount2(search);

        System.out.println("totalCount = " + totalCount);
    }


    @Test
    @DisplayName("게시물 번호를 주면 해당 게시물의 조회수가 정상적으로 올라가야 한다.")
    void upViewCountTest() {
        mapper.upViewCount(311L);

        Board board = mapper.findOne(311L);

        assertTrue(board.getViewCnt() == 3);
    }


    @Test
    @DisplayName("특정 게시물에 첨부된 파일 경로들을 조회한다.")
    void findFileNamesTest() {

        // given
        Long bno = 331L;

        // when
        List<String> fileNames = mapper.findFileNames(bno);

        // then
        fileNames.forEach(System.out::println);
        assertEquals(2, fileNames.size());

    }


    @Test
    @DisplayName("게시물 번호로 글쓴이의 계정명과 권한에 대한 정보를 가져와야 한다.")
    void findMemberByBoardNoTest() {
        //given
        Long boardNo = 336L;

        //when
        ValidateMemberDTO member = mapper.findMemberByBoardNo(boardNo);

        //then
        System.out.println("member = " + member);
        assertEquals("a123", member.getAccount());
        assertEquals("COMMON", member.getAuth().toString());
    }


    @Test
    @DisplayName("300개의 게시물을 삽입해야 한다.")
    void bulkInsert() {

        Board board;
        for (int i = 1; i <= 300; i++) {
            board = new Board();
            board.setAccount("kdf556");
            board.setTitle("제목" + i);
            board.setWriter("길동이" + i);
            board.setContent("안녕하세요요요요요요요~~" + i);
            mapper.save(board);
        }
    }
}
package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;

import java.util.List;

public interface BoardRepository {

    // 게시글 쓰기 기능
    boolean save(Board board); // 게시글 쓰기 기능을 할 때 매개변수에 클라이언트로부터 뭘 받아와야 할까..? 를 생각해보라


    // 게시글 전체 조회
    List<Board> findAll(); // 리턴 타입에 자료구조 선택은 자율적. 스택, 큐 등등등~~


    // 게시글 상세 조회
    Board findOne(Long boardNo); // where절에 들어갈 것을 매개변수로 받자! 보통 pk로 찾겠지


    // 게시글 삭제
    boolean remove(Long boardNo);


    // 게시글 수정
    boolean modify(Board board);


    // 전체 게시물 수 조회
    Long getTotalCount();


    // 조회수 상승 처리
    void upViewCount(Long boardNo);
}

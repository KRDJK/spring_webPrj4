package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.dto.ValidateMemberDTO;
import com.project.web_prj.common.paging.Page;
import com.project.web_prj.common.search.Search;
import com.project.web_prj.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 게시글 쓰기 기능
    boolean save(Board board); // 게시글 쓰기 기능을 할 때 매개변수에 클라이언트로부터 뭘 받아와야 할까..? 를 생각해보라



    // 게시글 전체 조회 with paging
    List<Board> findAll(Page page); // 리턴 타입에 자료구조 선택은 자율적. 스택, 큐 등등등~~


    // 게시글 전체 조회 with searching
    List<Board> findAll2(Search search);


    // 게시글 상세 조회
    Board findOne(Long boardNo); // where절에 들어갈 것을 매개변수로 받자! 보통 pk로 찾겠지


    // 게시글 삭제
    boolean remove(Long boardNo);


    // 게시글 수정
    boolean modify(Board board);


    // 전체 게시물 수 조회
    int getTotalCount();
    int getTotalCount2(Search search);


    // 조회수 상승 처리
    void upViewCount(Long boardNo);

    
    // 파일 첨부 기능 처리
    void addFile(String fileName);


    // 게시물에 붙어있는 첨부파일 경로명 전부 조회하기
    List<String> findFileNames(Long bno);


    // 22.08.04 추가
    // 게시물 번호로 게시글 작성자의 계정명과 권한 가져오기
    ValidateMemberDTO findMemberByBoardNo(Long boardNo);
}

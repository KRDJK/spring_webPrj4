package com.project.web_prj.board.service;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.repository.BoardMapper;
import com.project.web_prj.board.repository.BoardRepository;
import com.project.web_prj.common.paging.Page;
import com.project.web_prj.common.search.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper repository;


    // 게시물 등록 요청 중간 처리
    public boolean saveService(Board board) {
        log.info("save service start - {}", board);
        return repository.save(board);
    }


    // 게시물 전체 조회 요청 중간 처리
//    public List<Board> findAllService() {
//        log.info("findAll service start");
//        List<Board> boardList = repository.findAll();
//
//        // 목록 중간 데이터처리
//        processConverting(boardList);
//
//        return boardList;
//    }


    // 게시물 전체 조회 요청 중간 처리 with paging
    public Map<String, Object> findAllService(Page page) {
        log.info("findAll service start");

        HashMap<String, Object> findDataMap = new HashMap<>();

        List<Board> boardList = repository.findAll(page);


        // 목록 중간 데이터처리
        processConverting(boardList);


        findDataMap.put("bList", boardList);
        findDataMap.put("tc", repository.getTotalCount());

        return findDataMap;
    }


    // 게시물 전체 조회 요청 중간 처리 with searching
    public Map<String, Object> findAllService(Search search) {
        log.info("findAll service start");

        HashMap<String, Object> findDataMap = new HashMap<>();

        List<Board> boardList = repository.findAll2(search);


        // 목록 중간 데이터처리
        processConverting(boardList);


        findDataMap.put("bList", boardList);
        findDataMap.put("tc", repository.getTotalCount2(search));

        return findDataMap;
    }


    private void processConverting(List<Board> boardList) {
        for (Board b : boardList) {
            convertDateFormat(b);
            substringTitle(b);
        }
    }


    private void convertDateFormat(Board b) {
        // 시간 포맷팅( 표시 형식 변경 ) 처리
        Date date = b.getRegDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd a hh:mm");
                                                                    // a = 오전, 오후 구분
                                                                    // hh = 0~12시까지만 표기.
        b.setPrettierDate(sdf.format(date));
    }


    private void substringTitle(Board b) {
        // 글 제목 줄임처리 ( 너무 많이 입력하면 제목 앞부분 일부 + ... 으로 표시되게!! )

        // 만약에 글제목이 5글자 이상이라면
        // 5글자만 보여주고 나머지는 ...처리
        String title = b.getTitle();
        if (title.length() > 5) {
            String subStr = title.substring(0, 5);  // 이상 미만 개념이라 ㅇㅇ
            b.setShortTitle(subStr + "...");
        } else {
            b.setShortTitle(title);
        }

    }


    // 게시물 상세 조회 요청 중간 처리
    @Transactional // sql이 여러개 돌 때 전부 성공해야 정상 작동! 하나라도 실패하면 롤백하게 만들기!!
    public Board findOneService(Long boardNo, HttpServletRequest request, HttpServletResponse response) { // response는 쿠키를 전송하기 위해 사용!!
        log.info("findOne service start - {}", boardNo);

        Board board = repository.findOne(boardNo);

        // 상세조회함과 동시에 조회수 상승 기능
        makeUpViewCount(boardNo, request, response);


        return board;
    }


    // 조회수 상승 (쿠키 부여 및 쿠키 보유 여부 확인까지) 메서드
    private void makeUpViewCount(Long boardNo, HttpServletRequest request, HttpServletResponse response) {
        // 클라이언트로부터 어떤 요청이 발생하면 그 요청 정보 속에서 쿠키를 조회한다.
        // - 해당 이름의 쿠키가 있으면 쿠키가 들어오고 없으면 null이 들어옴.
        Cookie foundCookie = WebUtils.getCookie(request, "b" + boardNo);


        if (foundCookie == null) {
            // 해당 게시물 번호에 해당하는 쿠키가 있는지 확인
            // 쿠키가 없으면 조회수를 상승시켜주고 쿠키를 만들어서 클라이언트에 전송
//        new Cookie("쿠키 이름", "쿠키값"); // 쿠키 생성 javax.servlet~~
            repository.upViewCount(boardNo);

            Cookie cookie = new Cookie("b" + boardNo, String.valueOf(boardNo));// 쿠키 생성
            cookie.setMaxAge(60); // 쿠키 수명 설정 (초 단위로 설정) * 60 * 24 * 7 를 통해서 간격 조절 가능
            cookie.setPath("/board/content"); // 쿠키 적용 범위 제한 설정

            response.addCookie(cookie); // 클라이언트에게 쿠키 전송
        }
    }


    // 게시물 삭제 요청 중간 처리
    public boolean removeService(Long boardNo) {
        log.info("remove service start - {}", boardNo);
        return repository.remove(boardNo);
    }


    // 게시물 수정 요청 중간 처리
    public boolean modifyService(Board board) {
        log.info("modify service start - {}", board);

        return repository.modify(board);
    }
}
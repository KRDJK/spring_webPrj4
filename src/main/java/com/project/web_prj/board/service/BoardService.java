package com.project.web_prj.board.service;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;


    // 게시물 등록 요청 중간 처리
    public boolean saveService(Board board) {
        log.info("save service start - {}", board);
        return repository.save(board);
    }


    // 게시물 전체 조회 요청 중간 처리
    public List<Board> findAllService() {
        log.info("findAll service start");
        List<Board> boardList = repository.findAll();

        // 목록 중간 데이터처리
        processConverting(boardList);

        return boardList;
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
    public Board findOneService(Long boardNo) {
        log.info("findOne service start - {}", boardNo);
        return repository.findOne(boardNo);
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
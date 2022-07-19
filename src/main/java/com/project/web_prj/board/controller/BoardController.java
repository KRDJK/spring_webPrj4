package com.project.web_prj.board.controller;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    /**
     *
     */

    // 게시물 목록 요청
    @GetMapping("/list") // http://localhost:8183/board/list
    public String list(Model model) {
        log.debug("controller request /board/list GET!!");

        List<Board> boardList = boardService.findAllService();
        log.debug("return data - {}", boardList);


        model.addAttribute("bList", boardList);

        return "board/board-list";
    }


    // 게시물 상세 조회 요청
    @GetMapping("/content/{boardNo}") // http://localhost:8183/board/content/150
    public String content(@PathVariable Long boardNo, Model model) {
        log.info("controller request /board/content GET! - {}", boardNo);

        Board board = boardService.findOneService(boardNo);
        log.info("return data - {}", board);

        model.addAttribute("b", board);

        return "board/board-detail";
    }


    // 게시물 쓰기 화면 요청
    @GetMapping("/write")
    public String write() {
        log.info("controller request /board/write GET!!");

        return "board/board-write";
    }


    // 게시물 작성 후 등록 요청
    @PostMapping("/write")
    public String write(Board board) { // @RequestBody <- 테스트 할 때만 붙이고 끝났으면 떼라.
        log.info("controller request /board/write POST! - {}", board);

        boolean flag = boardService.saveService(board); // 테스트하면서 DB로 보내는 것도 잘 등록되는 것을 확인했다!

        return flag ? "redirect:/board/list" : "redirect:/";
    }

    
    // 게시글 수정 화면 요청
    @GetMapping("/modify")
    public String modify(Long boardNo, Model model) {
        log.info("controller request /board/modify GET!! - {}", boardNo);

        Board board = boardService.findOneService(boardNo);
        log.info("find article: {}", board);

        model.addAttribute("board", board);

        return "board/board-modify";
    }


    // 게시글 수정 완료 후 반영 요청
    @PostMapping("/modify")
    public String modify(Board board) {
        log.info("controller request /board/modify POST!! - {}", board);

        boolean flag = boardService.modifyService(board);

        return flag ? "redirect:/board/content/" + board.getBoardNo() : "redirect:/";
    }
    
    
    // 게시글 삭제 요청
    @GetMapping("/delete")
    public String delete(Long boardNo) {
        log.info("controller request /board/delete GET! - bno: {}", boardNo);

        return boardService.removeService(boardNo) ? "redirect:/board/list" : "redirect:/";
    }
}

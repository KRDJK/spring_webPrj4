package com.project.web_prj.board.controller;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.service.BoardService;
import com.project.web_prj.common.paging.Page;
import com.project.web_prj.common.paging.PageMaker;
import com.project.web_prj.common.search.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    public String list(Search search, Model model) {
        log.info("controller request /board/list GET!! -{}", search);


        Map<String, Object> boardMap = boardService.findAllService(search);
        log.debug("return data - {}", boardMap);


        // 페이지 정보 생성
        PageMaker pm = new PageMaker(new Page(search.getPageNum(), search.getAmount()), (Integer) boardMap.get("tc"));


        model.addAttribute("bList", boardMap.get("bList"));
        model.addAttribute("pm", pm);


        return "board/board-list";
    }


    // 게시물 상세 조회 요청
    @GetMapping("/content/{boardNo}") // http://localhost:8183/board/content/150
    public String content(@PathVariable Long boardNo, Page page, Model model, HttpServletRequest request, HttpServletResponse response) {
        log.info("controller request /board/content GET! - {}", boardNo);

        Board board = boardService.findOneService(boardNo, request, response);
        log.info("return data - {}", board);

        model.addAttribute("b", board);
        model.addAttribute("p", page);

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
    public String write(Board board, RedirectAttributes ra) { // @RequestBody <- 테스트 할 때만 붙이고 끝났으면 떼라.
        log.info("controller request /board/write POST! - {}", board);

        boolean flag = boardService.saveService(board); // 테스트하면서 DB로 보내는 것도 잘 등록되는 것을 확인했다!


        // 게시물 등록에 성공하면 클라이언트에게 성공메시지 전송
        if (flag) ra.addFlashAttribute("msg", "reg-success");
        // 모델에 담으면 HttpServletRequest 객체에 저장됨. => 리다이렉트하면 사라짐. 포워딩은 유지됨.
        // 리다이렉트 시에도 데이터를 가져가고 싶으면 RedirectAttributes 객체를 활용하라!


        return flag ? "redirect:/board/list" : "redirect:/";
        // 왜 리다이렉트를 해야할까?? 글 작성 이후 /board/list 요청에 따른 비즈니스 로직을 컨트롤러가 다시 수행하여 출력해야 하기 때문에!!!
    }

    
    // 게시글 수정 화면 요청
    @GetMapping("/modify")
    public String modify(Long boardNo, Model model, HttpServletRequest request, HttpServletResponse response) {
        log.info("controller request /board/modify GET!! - {}", boardNo);

        Board board = boardService.findOneService(boardNo, request, response);
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

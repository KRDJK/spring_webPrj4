package com.project.web_prj.common.api;

import com.project.web_prj.board.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController // 스프링에서 제공! 이걸 붙이는 순간 그냥 컨트롤러와는 달리 jsp 뷰 포워딩을 하지 않고 클라이언트에게 json 데이터를 자동으로 전송한다.
@Log4j2
public class RestBasicController {



    @GetMapping("/api/hello")
    public String hello() {
        return "hello!!!";
    }


    @GetMapping("/api/board")
    public Board board() {
        Board board = new Board();
        board.setBoardNo(10L);
        board.setContent("안녕~~");
        board.setTitle("메롱~~");
        board.setWriter("박영희");

        return board;
    }


    @GetMapping("/api/arr")
    public String[] arr() {
        String[] foods = {"짜장면", "레몬에이드", "볶음밥"};
        return foods;
    }


    // post 요청 처리 : REST 방식에서는 HTML의 form을 활용하는 것이 아니다.
            // HTML에서야 form이 가능하겠지.. 근데 클라이언트가 웹브라우저가 아니라면?? 그래서 클라이언트가 뭐든간에 JSON 으로 송신한다.
    @PostMapping("/api/join")
    public String joinPost(@RequestBody List<String> info) { // 이러면 List 형태의 JSON을 클라이언트에서 던져줘야 한다.
                                            // 그럼 그걸 다시 여기서 자바 형태로 JSON을 변환한다. 그걸 누가해?? 스프링 내부의 '잭슨' 이라는 라이브러리가 한다.
                                            // 스프링 이전에는 개발자가 직접 JSON 형태로 변환했다.. 어우..
                    // 매개변수에 아무것도 붙이지 않고 'List<String> info' 라고만 한다면 앞에 @RequestParm이 생략된거처럼 된다. 그럼 쿼리파라미터로 받아야함.
                    // 근데 json 형태라면 다르게 받아야 한다. 앞에 @RequestBody를 붙여야 json을 매개변수 형태에 맞춰 변환해준다.
        log.info("/api/join POST!!! - {}", info);
        return "POST - OK"; // 여긴 클라이언트로 가는 부분이다. JSON 형태로 감!!
    }


    // put 요청 처리
    @PutMapping("/api/join")
    public String joinPut(@RequestBody Board board) {
        log.info("/api/join PUT!!! - {}", board);
        return "PUT - OK";
    }


    // delete 요청 처리
    @DeleteMapping("/api/join")
    public String joinDelete() {
        log.info("/api/join DELETE!!!");
        return "DELETE - OK";
    }


    // RestController에서 뷰 포워딩을 하고 싶으면?? String으로 보내면 그냥 화면으로 다이렉트로 index라고만 띄움.
    @GetMapping("/hoho")
    public ModelAndView hoho() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        return mv;
    }
}

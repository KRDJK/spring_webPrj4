package com.project.web_prj.reply.api;

import com.project.web_prj.common.paging.Page;
import com.project.web_prj.reply.domain.Reply;
import com.project.web_prj.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/replies")
public class ReplyApiController {

    private final ReplyService replyService;

    /*
        - 댓글 목록 조회 요청 : /api/v1/replies - GET
        - 댓글 개별 조회 요청 : /api/v1/replies/72(댓글 번호) - GET
        - 댓글 쓰기 요청 : /api/v1/replies - POST
        - 댓글 수정 요청 : /api/v1/replies/72 - PUT
        - 댓글 삭제 요청 : /api/v1/replies/72 - DELETE
    */

    // 댓글 목록 조회 요청
    @GetMapping("")
    public Map<String, Object> list(Long boardNo, Page page) {
        log.info("/api/v1/replies GET! bno={}, page{}", boardNo, page);


        Map<String, Object> replies = replyService.getList(boardNo, page);

        return replies;
    }


    // 댓글 쓰기 요청
    @PostMapping("")
            // form을 쓰면 submit이 일어날 때 새로고침이 무조건 되어버린다.. 그럼 form이 아니라 json으로 받아야 함.
    public String create(@RequestBody Reply reply) {
        log.info("/api/v1/replies POST! - {}", reply);

        boolean flag = replyService.write(reply);

        return flag ? "insert-success" : "insert-fail";
    }


    // 댓글 수정 요청
    @PutMapping("/{rno}")
    // form을 쓰면 submit이 일어날 때 새로고침이 무조건 되어버린다.. 그럼 form이 아니라 json으로 받아야 함.
    public String modify(@PathVariable Long rno, @RequestBody Reply reply) {
        log.info("/api/v1/replies PUT! - {}", rno);

        reply.setReplyNo(rno);

        boolean flag = replyService.modify(reply);

        return flag ? "mod-success" : "mod-fail";
    }


    // 댓글 삭제 요청
    @DeleteMapping("/{rno}")
    // form을 쓰면 submit이 일어날 때 새로고침이 무조건 되어버린다.. 그럼 form이 아니라 json으로 받아야 함.
    public String modify(@PathVariable Long rno) {
        log.info("/api/v1/replies DELETE! - {}", rno);

        boolean flag = replyService.remove(rno);

        return flag ? "del-success" : "del-fail";
    }


} // end class

package com.project.web_prj.reply.controller;

import com.project.web_prj.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyService;



}

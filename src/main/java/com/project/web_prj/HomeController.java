package com.project.web_prj;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        log.info("welcome page open!");

        model.addAttribute("scroll", true);

        return "index";
    }


    @GetMapping("/haha")
    @ResponseBody // 리턴 데이터가 뷰 포워딩이 아닌 JSON 으로 전달됨. 위에 @RestController라고 하지 않았더라도!!
    public Map<String, String> haha() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "aaa");
        map.put("b", "bbb");
        map.put("c", "ccc");

        return map;
    }


}

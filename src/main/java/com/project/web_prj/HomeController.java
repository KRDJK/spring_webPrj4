package com.project.web_prj;

import com.project.web_prj.member.domain.Member;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        log.info("welcome page open!");
//        model.addAttribute("scroll", true);


        // 로그인이 된 상태인지 아닌지 메인화면에서 확인해야 하므로 세션을 찾아보자.
        // 브라우저가 꺼지기 전에는 정보가 유지되므로 어디서든 가능하다.
        /*Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            model.addAttribute("login", false);
        } else {
            model.addAttribute("login", true);
        }*/

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

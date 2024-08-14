package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {  /* Model 객체에 데이터를 담아 템플릿 view에 넘겨줄 수 있음 (reuqest, session) 객체 같은 */
        model.addAttribute("data", "hello");

        // thymleaf의 viewName 매칭: return 문자열 + .html 파일로 렌더링 (resources > templates의 하위 파일)
        return "hello";
    }
}

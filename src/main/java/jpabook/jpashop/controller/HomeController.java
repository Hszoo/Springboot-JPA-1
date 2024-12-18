package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

//    Logger log = LoggerFactory.getLogger(); // @Sl4j로 대체 가능
    @RequestMapping("/")
    public String home() {
        log.info("home Controller");
        return "home"; // home.html
    }
}

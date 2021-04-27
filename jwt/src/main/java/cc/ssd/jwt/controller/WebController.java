
package cc.ssd.jwt.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpServletRequest;


@Controller
public class WebController {
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/")
    public String getLoginPage(){
        return "LoginForm";
    }

}

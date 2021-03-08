package cc.ssd.webbasics.controller;


import cc.ssd.webbasics.utils.payload.request.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "/login")
    public ResponseEntity<String> addMemberV1(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        logger.info(loginRequest.getUsername());
        if((loginRequest.getUsername().equals("admin")) && (loginRequest.getPassword().equals("12345678"))) {
            request.getSession().setAttribute("SESSION_USERNAME", loginRequest.getUsername());
            request.getSession().setAttribute("SESSION_ROLE", "ROLE_ADMIN");
            return ResponseEntity.ok(Json.createObjectBuilder().add("sonuc","1").build().toString());
        }else{
            return ResponseEntity.ok(Json.createObjectBuilder().add("sonuc","0").build().toString());
        }
    }

    @GetMapping("/logout")
    public void destroySession(HttpServletRequest request) {
        //invalidate the session , this will clear the session vars
        request.getSession().invalidate();
        //return "redirect:index.html";
    }


}

package cc.ssd.insecurewebapplication.controller;


import cc.ssd.insecurewebapplication.model.User;
import cc.ssd.insecurewebapplication.service.LoginService;
import cc.ssd.insecurewebapplication.utils.payload.request.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        logger.info(loginRequest.getUsername());


        //if(!loginService.login(loginRequest.getUsername(), loginRequest.getPassword()).isEmpty()) {
        if(loginService.login(loginRequest.getUsername(), loginRequest.getPassword())!= null) {
            request.getSession().setAttribute("SESSION_USERNAME", loginRequest.getUsername());
            request.getSession().setAttribute("SESSION_ROLE", "ROLE_ADMIN");
            return ResponseEntity.ok(Json.createObjectBuilder().add("sonuc","1").build().toString());
        }else{
            return ResponseEntity.ok(Json.createObjectBuilder().add("sonuc","0").build().toString());
        }
    }
    @PostMapping(value = "/loginxss")
    public ResponseEntity<String> loginxss(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        logger.info(loginRequest.getUsername());
        return ResponseEntity.ok(Json.createObjectBuilder().add("username",loginRequest.getUsername()).build().toString());

        //if(!loginService.login(loginRequest.getUsername(), loginRequest.getPassword()).isEmpty()) {
       /* if(loginService.login(loginRequest.getUsername(), loginRequest.getPassword())!= null) {
            request.getSession().setAttribute("SESSION_USERNAME", loginRequest.getUsername());
            request.getSession().setAttribute("SESSION_ROLE", "ROLE_ADMIN");
            return ResponseEntity.ok(Json.createObjectBuilder().add("sonuc","1").build().toString());
        }else{
            return ResponseEntity.ok(Json.createObjectBuilder().add("sonuc","0").build().toString());
        }*/
    }

    @GetMapping("/logout")
    public void destroySession(HttpServletRequest request) {
        //invalidate the session , this will clear the session vars
        request.getSession().invalidate();
        //return "redirect:index.html";
    }


}

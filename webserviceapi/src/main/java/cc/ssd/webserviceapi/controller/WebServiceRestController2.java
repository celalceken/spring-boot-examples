package cc.ssd.webserviceapi.controller;

import cc.ssd.webserviceapi.model.User;
import cc.ssd.webserviceapi.repository.UserRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api",
        produces = "application/json",
        method = {RequestMethod.DELETE, RequestMethod.PUT})
public class WebServiceRestController2 {

    private static final Logger logger = LoggerFactory.getLogger(WebServiceRestController2.class);

    @Autowired
    UserRepository userRepository;

    @DeleteMapping(value = "/users")
    public ResponseEntity<String> addUser(@RequestBody User user, HttpServletRequest request) {
        logger.info(user.getName());
        userRepository.deleteById(user.getId());
        return ResponseEntity.ok(Json.createObjectBuilder().add("sonuc","1").build().toString());
    }

}

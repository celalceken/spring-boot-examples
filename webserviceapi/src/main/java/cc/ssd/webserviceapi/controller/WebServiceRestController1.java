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
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8001", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class WebServiceRestController1 {

    private static final Logger logger = LoggerFactory.getLogger(WebServiceRestController1.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<String> getUsers(Model model)
    {
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(userRepository.findAll());
        logger.info(jsonUsers.toString());
        return ResponseEntity.ok(jsonUsers);
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUser(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        Gson gson = new Gson();
        String jsonUser = gson.toJson(userRepository.findById(id));
        logger.info(jsonUser.toString());
        //return ResponseEntity.ok(jsonUser);
        return userRepository.findById(id);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<String> addUser(@RequestBody User user, HttpServletRequest request) {
        logger.info(user.getName());
        userRepository.save(user);
        return ResponseEntity.ok(Json.createObjectBuilder().add("sonuc","1").build().toString());
    }
}

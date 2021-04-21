
package cc.ssd.insecurewebapplication.controller;

import cc.ssd.insecurewebapplication.model.User;
import cc.ssd.insecurewebapplication.repository.UserRepository;
import cc.ssd.insecurewebapplication.service.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;


@Controller
public class WebController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/examples")
    public String getExamples( ) {
        return "Examples";
    }

    @GetMapping("/kullanicilar")
    public ResponseEntity<String> kullanicilar(Model model, HttpServletRequest request)
    {
        System.out.println(userService.findAll().get(0).getName());
        //System.out.println("Session info:"+request.getSession().getId());
        //request.getSession().setAttribute("SESSION_VAR1", userService.findAll().get(0).getName());
        return ResponseEntity.ok("Kullanıcılar:"+userService.findAll());
        //return ResponseEntity.ok("Kullanıcılar:"+userRepository.findAllUsers());
    }

    @GetMapping("/kullanicilar/{id}")
    public ResponseEntity<String> kullanici(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        System.out.println(userService.findById(id).toString());
        //return ResponseEntity.ok("Aranan kullanıcı:"+userService.findById(id)+"<br>oturum değişkeni (SESSION_VAR1):"+request.getSession().getAttribute("SESSION_VAR1"));
        return ResponseEntity.ok("Aranan kullanıcı:"+userRepository.findAllUsers(id)+"<br>oturum değişkeni (SESSION_VAR1):"+request.getSession().getAttribute("SESSION_VAR1"));

    }

    @GetMapping("/kullanicilarJSON/{id}")
    public ResponseEntity<String> kullaniciJSON(@PathVariable("id") int id, Model model, HttpServletRequest request) {

        User user=userService.findById(id);

        JsonObject userJSON = Json.createObjectBuilder().add("id", user.getId())
                .add("name", user.getName())
                .add("surname", user.getSurname())
                .build();
        System.out.println(userJSON.toString());
        return ResponseEntity.ok(userJSON.toString());
    }
    @GetMapping("/kullanicilarJSONad/{adi}")
    public ResponseEntity<String> kullaniciJSON(@PathVariable("adi") String adi, Model model, HttpServletRequest request) {

        User user=userService.findByName(adi);

        JsonObject userJSON = Json.createObjectBuilder().add("id", user.getId())
                .add("name", user.getName())
                .add("surname", user.getSurname())
                .build();
        System.out.println(userJSON.toString());
        return ResponseEntity.ok(userJSON.toString());
    }
    @GetMapping("/kullanicilarJSON")
    public ResponseEntity<String> kullanicilarJSON(Model model)
    {
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(userService.findAll());
        System.out.println(jsonUsers.toString());
        return ResponseEntity.ok(jsonUsers);
    }
    @GetMapping("/kullanicilarJSONadkeyup/{adi}")
    public ResponseEntity<String> kullaniciJSONadkeyup(@PathVariable("adi") String adi) {
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(userService.findAllByName(adi));
        System.out.println(jsonUsers.toString());
        return ResponseEntity.ok(jsonUsers);
    }

    @GetMapping("/loginpage")
    public String getLoginPage(){
        return "LoginForm";
    }

    @GetMapping("/xssform")
    public String getXssPage(){
        return "XSSForm";
    }


    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest request){

        try{
            logger.info("Session Role is:"+ request.getSession().getAttribute("SESSION_ROLE").toString());
            if ("ROLE_ADMIN".equals(request.getSession().getAttribute("SESSION_ROLE")))
                return "Dashboard";
        }catch (Exception e)
        {
            return "Error";
        }

        return "Error";
    }
}

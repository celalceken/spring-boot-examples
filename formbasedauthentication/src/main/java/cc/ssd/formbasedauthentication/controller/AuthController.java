package cc.ssd.formbasedauthentication.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/")
    public ResponseEntity<String> getGreetings() {
        return ResponseEntity.ok("Greetings (for every one)");
    }
    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard() {
            return ResponseEntity.ok("Dashboard (for only admins)");
    }
}

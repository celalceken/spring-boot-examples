package cc.ssd.jwt.controller;

import cc.ssd.jwt.payload.JwtResponse;
import cc.ssd.jwt.payload.LoginRequest;
import cc.ssd.jwt.repository.RoleRepository;
import cc.ssd.jwt.repository.UserRepository;
import cc.ssd.jwt.security.jwt.JwtUtils;
import cc.ssd.jwt.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPoint.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/greeting")
    public ResponseEntity<String> getGreetings() {
        return ResponseEntity.ok("Greetings (for every one)");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard() {
        return ResponseEntity.ok("Dashboard (for only admins)");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        //System.out.println("usernamePasswordAuthenticationToken:"+usernamePasswordAuthenticationToken.toString());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //System.out.println("isAuthenticated:"+authentication.isAuthenticated()+"Principle:"+authentication.getPrincipal().toString());
        // if (usernamePasswordAuthenticationToken.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        logger.info(jwt);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getEmail(),
                roles));
        //}else
        //	return (ResponseEntity<?>) ResponseEntity.status();

    }

}
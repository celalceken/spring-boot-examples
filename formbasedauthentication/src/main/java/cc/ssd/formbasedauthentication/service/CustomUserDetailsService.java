package cc.ssd.formbasedauthentication.service;

import cc.ssd.formbasedauthentication.model.User;
import cc.ssd.formbasedauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(" " + userName + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}

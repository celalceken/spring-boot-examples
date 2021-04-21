package cc.ssd.insecurewebapplication.service;

import cc.ssd.insecurewebapplication.controller.AuthController;
import cc.ssd.insecurewebapplication.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import javax.json.Json;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User login(String username, String password){
        User user=null;

        //admin2' or '1'--
        //https://examples.javacodegeeks.com/enterprise-java/spring/jdbc/spring-jdbctemplate-example/
        String sql = "SELECT * FROM users Where username = \'" +
                username+"\' AND password=\'" + password+"\'";
        try{
        List<User> users = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(User.class));
        user=users.get(0);
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }


        /*String sql = "SELECT * FROM users Where username = ? AND password = ?";
        try{
            user = (User) jdbcTemplate.queryForObject(
                    sql,  new Object[] { username,password }, new BeanPropertyRowMapper(User.class));
        }catch (EmptyResultDataAccessException e){
            logger.info(e.getMessage());
            return null;
        }*/

        return user;
    }


}

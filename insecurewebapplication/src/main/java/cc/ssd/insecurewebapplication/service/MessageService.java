package cc.ssd.insecurewebapplication.service;

import cc.ssd.insecurewebapplication.controller.AuthController;
import cc.ssd.insecurewebapplication.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Message message){

        String sql = "INSERT INTO messages " +
                "(id, message) VALUES (?, ?)";

        jdbcTemplate.update(sql, new Object[] { message.getId(),
                message.getMessage()
        });
    }

   /* public List<Message> findAll(){

        String sql = "SELECT * FROM messages";

        List<Message> messages = new ArrayList<Message>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Message message = new Message();
            message.setId(Integer.parseInt(String.valueOf(row.get("ID"))));
            message.setMessage(Integer.parseInt(String.valueOf(row.get("AGE"))));
            messages.add(message);
        }
        return messages;
    }*/



}

package cc.ssd.insecurewebapplication.model;


import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name="messages")
public class Message
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
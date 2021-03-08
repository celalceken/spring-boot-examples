
/*
* Service layer is optional. The bussiness logic of the crud operations may be implemented here.
* It uses repository layer...
*
* */
package cc.ssd.webbasics.service;


import cc.ssd.webbasics.model.User;
import cc.ssd.webbasics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    //We inject the UserRepository.
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll()
    {
       List<User> list = new ArrayList<>();
       userRepository.findAll().forEach(e -> list.add(e));
       return list;
    }

    public User findById(Integer id)
    {
        return userRepository.findById(id).orElse(null);
    }

    public User findByName(String  name)
    {
        return userRepository.findByName(name).orElse(null);
    }

    public List<User> findAllByName(String name)
    {
        List<User> list = new ArrayList<>();
        userRepository.findAllByNameContaining(name).forEach(e -> list.add(e));
        return list;
    }


    public Long count()
    {
        return userRepository.count();
    }

    public void deleteById(int id)
    {
        userRepository.deleteById(id);
    }

    public User save(User user)
    {
        userRepository.save(user);
        return user;
    }
}
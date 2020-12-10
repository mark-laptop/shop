package ru.ndg.shop.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ndg.shop.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    User getById(Long id);
    User saveOrUpdate(User user);
    void delete(Long id);
    User findByUsername(String username);
}

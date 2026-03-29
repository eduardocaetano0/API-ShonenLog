package br.com.shonenlog.service;

import br.com.shonenlog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import br.com.shonenlog.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

        public User save(User user){
            return userRepository.save(user);
        }
}

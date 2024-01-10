package liubov.taskmanager.services;

import liubov.taskmanager.dto.request.SignInRequest;
import liubov.taskmanager.dto.request.SignUpRequest;
import liubov.taskmanager.models.User;
import liubov.taskmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserById(Long.valueOf(username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public User findUserById(Long id){
        return userRepository.findUserById(id).orElseThrow(() -> new IllegalArgumentException(
                "Пользователь с таким id не найден"
        ));
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
    public  Optional<User> findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public boolean validatePassword(SignInRequest signInRequest){
        User user = findUserByEmail(signInRequest.email()).get();
        return passwordEncoder.matches(signInRequest.password(), user.getPassword());
    }

    public User save(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.email());
        user.setUsername(signUpRequest.username());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        return userRepository.save(user);
    }
}

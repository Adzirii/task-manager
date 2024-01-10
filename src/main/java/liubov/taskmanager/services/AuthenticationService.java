package liubov.taskmanager.services;

import jakarta.transaction.Transactional;
import liubov.taskmanager.dto.UserClaims;
import liubov.taskmanager.dto.request.SignInRequest;
import liubov.taskmanager.dto.request.SignUpRequest;
import liubov.taskmanager.dto.response.ApplicationError;
import liubov.taskmanager.dto.response.JwtResponse;
import liubov.taskmanager.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static liubov.taskmanager.dto.JwtType.ACCESS;
import static liubov.taskmanager.dto.JwtType.REFRESH;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
//    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> register(final SignUpRequest signUpRequest) {
        if (userService.findUserByEmail(signUpRequest.email()).isPresent())
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Пользователь с такой почтой уже существет!"), HttpStatus.BAD_REQUEST);
        if (userService.findUserByUsername(signUpRequest.username()).isPresent())
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким именем уже существет!"), HttpStatus.BAD_REQUEST);
        if (!signUpRequest.password().equals(signUpRequest.passwordConfirm()))
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают!"), HttpStatus.BAD_REQUEST);

//        User user = new User();
//        user.setEmail(signUpRequest.email());
//        user.setUsername(signUpRequest.username());
//        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        return ResponseEntity.ok(userService.save(signUpRequest));
    }

    @Transactional
    public JwtResponse login(final SignInRequest signInRequest) throws ApplicationError {
        User user = userService.findUserByEmail(signInRequest.email()).orElseThrow(() ->
                new IllegalArgumentException(
                        String.format("Пользователя с именем '%s' не существует", signInRequest.email())
                ));
        if(!userService.validatePassword(signInRequest))
             throw new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Неверный пароль!");


        UserClaims userClaims = new UserClaims(user.getId(), user.getEmail(), user.getUsername());
        String accessToken = jwtService.generateToken(userClaims, ACCESS);
        String refreshToken = jwtService.generateToken(userClaims, REFRESH);

        return new JwtResponse(accessToken, refreshToken);
    }
}

package liubov.taskmanager.controllers;

import liubov.taskmanager.dto.request.SignInRequest;
import liubov.taskmanager.dto.request.SignUpRequest;
import liubov.taskmanager.dto.response.ApplicationError;
import liubov.taskmanager.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthorizeController {
    private final AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        return authService.register(request);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) throws ApplicationError {
        return ResponseEntity.ok().body(authService.login(request));
    }
}

package com.rifqy.potofolio.job_tracker.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.rifqy.potofolio.job_tracker.applicationuser.ApplicationUserService;
import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;
import com.rifqy.potofolio.job_tracker.applicationuser.model.dto.RegisterationRequestDTO;
import com.rifqy.potofolio.job_tracker.authentication.dto.JwtResponseDTO;
import com.rifqy.potofolio.job_tracker.authentication.dto.LoginRequestDTO;
import com.rifqy.potofolio.job_tracker.authentication.jwt.JwtProvider;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final ApplicationUserService applicationUserService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/tokens")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        logger.info("Attemp to system");
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponseDTO(jwt));
    }

    @PostMapping("/registerations")
    public ResponseEntity<JwtResponseDTO> registeration(
            @RequestBody @Valid RegisterationRequestDTO registerationRequestDTO) {
        ApplicationUser newUser = registerationRequestDTO.convertToEntity();
        this.applicationUserService.save(newUser);

        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                registerationRequestDTO.getUsername(), registerationRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponseDTO(jwt));
    }
}
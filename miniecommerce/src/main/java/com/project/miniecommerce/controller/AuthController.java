package com.project.miniecommerce.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.miniecommerce.entity.User;
import com.project.miniecommerce.model.JwtResponse;
import com.project.miniecommerce.model.LoginRequest;
import com.project.miniecommerce.model.RefreshTokenRequest;
import com.project.miniecommerce.model.SignUpRequest;
import com.project.miniecommerce.security.jwt.JwtUtil;
import com.project.miniecommerce.security.service.UserDEtailServiceImplementation;
import com.project.miniecommerce.security.service.UserDetailImplementation;
import com.project.miniecommerce.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserDEtailServiceImplementation userDEtailServiceImplementation;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser (@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateJwtToken(authentication);
        String refreshToken = jwtUtil.generateRefreshJwtToken(authentication);
        UserDetailImplementation principal = (UserDetailImplementation)authentication.getPrincipal();
        return ResponseEntity.ok().body(new JwtResponse(token, refreshToken, principal.getUsername(), principal.getEmail()));
    }

    @PostMapping("/signup")
    public User signup(@RequestBody SignUpRequest request) throws BadRequestException {
        User user = new User();
        user.setId(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRoles("user");

        User created = userService.create(user);
        return created;
    }
    
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        String token = request.getRefreshToken();
        boolean valid = jwtUtil.validateJwtToken(token);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String username =jwtUtil.getUsernameFromJwtToken(token);
        UserDetailImplementation userDetailImplementation = (UserDetailImplementation)userDEtailServiceImplementation.loadUserByUsername(username);
        Authentication authentication =new UsernamePasswordAuthenticationToken(userDetailImplementation, null, userDetailImplementation.getAuthorities());
        String newToken=jwtUtil.generateJwtToken(authentication);
        String refreshToken = jwtUtil.generateRefreshJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(newToken, refreshToken, username, userDetailImplementation.getEmail()));

    }

    
    
}

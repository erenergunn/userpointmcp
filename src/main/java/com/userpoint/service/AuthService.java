package com.userpoint.service;

import com.userpoint.dto.UserLoginDto;
import com.userpoint.dto.UserRegistrationDto;
import com.userpoint.dto.JwtResponseDto;
import com.userpoint.entity.User;
import com.userpoint.entity.Point;
import com.userpoint.repository.UserRepository;
import com.userpoint.repository.PointRepository;
import com.userpoint.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public JwtResponseDto registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        // Create new user
        User user = new User(
            registrationDto.getUsername(),
            registrationDto.getEmail(),
            passwordEncoder.encode(registrationDto.getPassword())
        );

        User savedUser = userRepository.save(user);

        // Create initial point record
        Point point = new Point(savedUser, 0);
        pointRepository.save(point);

        // Authenticate and generate token
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                registrationDto.getUsername(),
                registrationDto.getPassword()
            )
        );

        String jwt = jwtUtils.generateJwtToken(authentication);

        return new JwtResponseDto(jwt, savedUser.getUsername(), savedUser.getEmail());
    }

    public JwtResponseDto authenticateUser(UserLoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = (User) authentication.getPrincipal();

        return new JwtResponseDto(jwt, user.getUsername(), user.getEmail());
    }
}

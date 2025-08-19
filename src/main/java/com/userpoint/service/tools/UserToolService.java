package com.userpoint.service.tools;

import com.userpoint.entity.User;
import com.userpoint.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserToolService {

    private final UserDetailsServiceImpl userDetailsService;

    @Tool(
            name = "getCurrentUser",
            description = "Retrieves the currently authenticated user."
    )
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}

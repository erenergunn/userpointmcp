package com.userpoint.service.tools;

import com.userpoint.dto.UserDetailDto;
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
    public UserDetailDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        UserDetailDto userDetailDto = new UserDetailDto();
        userDetailDto.setId(user.getId());
        userDetailDto.setUsername(user.getUsername());
        userDetailDto.setEmail(user.getEmail());
        return userDetailDto;
    }

}

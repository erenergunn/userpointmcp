package com.userpoint.controller;

import com.userpoint.service.tools.PointToolService;
import com.userpoint.service.tools.RewardToolService;
import com.userpoint.service.tools.UserToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chat")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "AI Chatbot", description = "Spring AI powered chatbot for points management")
public class ChatController {


    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder,
                          PointToolService pointToolService, UserToolService userToolService, RewardToolService rewardToolService) {
        ToolCallback[] toolCallbacks = ToolCallbacks.from(pointToolService, userToolService, rewardToolService);
        this.chatClient = chatClientBuilder
                .defaultSystem("You are a helpful AI assistant in a project that manages user points and rewards." +
                        " You can answer questions about points, rewards, and user accounts. " +
                        " You must be careful to be accurate and helpful. Do not make up information." +
                        " If you do not know the answer, say 'I don't know'." +
                        " You can use the tools provided to answer questions." +
                        " Do not answer questions that are not related to points or rewards." +
                        " If the user asks about something else, politely redirect them to the appropriate service." +
                        " You must be ethical and not provide any harmful or illegal information.")
                .defaultToolCallbacks(toolCallbacks)
                .build();
    }

    @PostMapping("/message")
    @Operation(summary = "Chat with AI assistant about points and rewards")
    public ResponseEntity<String> chat(@RequestBody Map<String, String> request) {
        try {
            String message = request.get("message");
            if (StringUtils.isEmpty(message)) {
                return ResponseEntity.badRequest().body("Message cannot be empty");
            }
            String response = chatClient.prompt().user(message).call().content();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing chat request", e);
            return ResponseEntity.badRequest().body("Chat service error");
        }
    }
}

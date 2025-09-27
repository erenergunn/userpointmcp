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
                .defaultSystem("You are an MCP client AI assistant for managing user points and rewards. " +
                        "Answer questions related to points, rewards, and user accounts accurately, using the appropriate tool callbacks based on the prompt. " +
                        "Answer only questions related to points, rewards, and user accounts, using the provided system tools as needed. " +
                        "Do not respond to questions about politics, religion, or other unrelated topics. " +
                        "Respond to the user's message in the same language they used, even if it's off-topic, you don't know the answer, or you can't help. " +
                        "If unsure, say 'I don't know.' in the same language of message. Responses should be 200 words maximum, but try to keep it under 100 words.")
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

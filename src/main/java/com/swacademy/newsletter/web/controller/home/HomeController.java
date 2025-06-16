package com.swacademy.newsletter.web.controller.home;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.security.JwtTokenProvider;
import com.swacademy.newsletter.service.home.HomeService;
import com.swacademy.newsletter.web.dto.response.home.HomeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    private final HomeService homeService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    @Operation(summary = "홈 화면 API")
    public ApiResponse<HomeResponseDto> getHome(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        HomeResponseDto homeResponseDto = homeService.getHome(userId);
        return ApiResponse.onSuccess(homeResponseDto);
    }
}

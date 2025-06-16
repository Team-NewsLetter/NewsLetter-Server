package com.swacademy.newsletter.service.home;

import com.swacademy.newsletter.web.dto.response.home.HomeResponseDto;

public interface HomeService {
    HomeResponseDto getHome(Long userId);
}

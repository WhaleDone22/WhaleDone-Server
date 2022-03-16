package com.server.whaledone.posts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class PostsMapToDateResponseDto {
    private Map<LocalDate, List<PostsResponseDto>> result = new HashMap<>();
}

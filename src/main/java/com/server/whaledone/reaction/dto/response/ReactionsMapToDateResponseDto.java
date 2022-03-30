package com.server.whaledone.reaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ReactionsMapToDateResponseDto {
    private Map<LocalDate, List<GetReactionAlarmsResponseDto>> result = new HashMap<>();
}

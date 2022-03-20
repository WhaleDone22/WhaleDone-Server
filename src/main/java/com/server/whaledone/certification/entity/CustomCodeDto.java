package com.server.whaledone.certification.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomCodeDto {

    private String code;

    private CustomCodeInfo info;
}

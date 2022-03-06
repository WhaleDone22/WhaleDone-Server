package com.server.whaledone.config.response.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultipleResult<T> extends CommonResult {
    private List<T> multipleData;
}

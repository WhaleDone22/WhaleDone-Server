package com.server.whaledone.config.response;

import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.response.result.CommonResult;
import com.server.whaledone.config.response.result.MultipleResult;
import com.server.whaledone.config.response.result.SingleResult;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Getter
    public enum CommonResponse {
        SUCCESS(0, "응답 성공"),
        FAIL(-1, "응답 실패");

        int code;
        String message;

        CommonResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    // 단일건 결과 리턴
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> singleResult = new SingleResult<>();
        singleResult.setSingleData(data);
        setSuccessResult(singleResult);
        return singleResult;
    }

    // 다중건 결과 리턴
    public <T> MultipleResult<T> getMultipleResult(List<T> data) {
        MultipleResult<T> multipleResult = new MultipleResult<>();
        multipleResult.setMultipleData(data);
        setSuccessResult(multipleResult);
        return multipleResult;
    }

    // 성공 결과만 리턴
    public CommonResult getSuccessResult() {
        CommonResult commonResult = new CommonResult();
        setSuccessResult(commonResult);
        return commonResult;
    }

    // 예외 내용을 담아서 결과 리턴
    public CommonResult getFailResult(CustomExceptionStatus status) {
        CommonResult commonResult = new CommonResult();
        setFailResult(commonResult, status);
        return commonResult;
    }

    private <T> void setSuccessResult(CommonResult result) {
        result.setResponseSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMessage());
    }

    private <T> void setFailResult(CommonResult result, CustomExceptionStatus status) {
        result.setResponseSuccess(true);
        result.setCode(status.getCode());
        result.setMessage(status.getMessage());
    }
}

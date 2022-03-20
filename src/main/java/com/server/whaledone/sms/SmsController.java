package com.server.whaledone.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.whaledone.config.response.ResponseService;
import com.server.whaledone.config.response.result.SingleResult;
import com.server.whaledone.sms.dto.SendSmsRequestDto;
import com.server.whaledone.sms.dto.standard.SmsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@Tag(name = "SMS API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;
    private final ResponseService responseService;

    @Operation(summary = "인증 번호 API", description = "국가와 휴대전화 번호를 이용해서 SMS 문자를 보낸다.")
    @PostMapping("/sms/code")
    public SingleResult<SmsResponseDto> sendSMS(@RequestBody @Valid SendSmsRequestDto dto)
            throws UnsupportedEncodingException, ParseException, NoSuchAlgorithmException,
            URISyntaxException, InvalidKeyException, JsonProcessingException {
        return responseService.getSingleResult(smsService.sendSms(dto));
    }
}

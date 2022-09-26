package com.server.whaledone.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.whaledone.certification.CertificationManager;
import com.server.whaledone.certification.entity.SmsCodeDto;
import com.server.whaledone.config.ApplicationYmlConfig;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.sms.dto.SendSmsRequestDto;
import com.server.whaledone.sms.dto.SmsType;
import com.server.whaledone.sms.dto.ValidateSmsCodeRequestDto;
import com.server.whaledone.sms.dto.standard.MessageDto;
import com.server.whaledone.sms.dto.standard.SmsRequestDto;
import com.server.whaledone.sms.dto.standard.SmsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final ApplicationYmlConfig config;
    private final CertificationManager certificationManager;

    private static final String SMS_SIGNUP_MESSAGE = " 웨일던에서 보내는 인증번호입니다. \n" +
            "\n" +
            "* 멀리 떨어진 가족의 일상과 마음을 공유하는 소통서비스, WhaleDone";

    private static final String SMS_PW_MESSAGE_PREFIX = "괜찮아요, 저희도 가끔 잊곤 해요 :)\n" +
            "\n" +
            "웨일던에서 보낸 임시 비밀번호 \n[ ";

    private static final String SMS_PW_MESSAGE_SUFFIX = " ]를 입력하고, 비밀번호를 다시 설정하세요." +
            "\n" +
            "* 멀리 떨어진 가족의 일상과 마음을 공유하는 소통 서비스, WhaleDone";

    public SmsResponseDto sendSignUpSms(SendSmsRequestDto dto) throws ParseException, JsonProcessingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException, JsonProcessingException {
        SmsCodeDto smsCodeDto =  certificationManager.createSmsCode(dto.getRecipientPhoneNumber());

        SmsResponseDto smsResponseDto = sendSms(dto.getSmsType(), dto.getCountryCode(), dto.getRecipientPhoneNumber(), smsCodeDto.getCode());

        smsResponseDto.setMinute(smsCodeDto.getMinute());
        smsResponseDto.setSecond(smsCodeDto.getSecond());

        return smsResponseDto;
    }

    public void sendPWSms(SendSmsRequestDto dto, String tempPassword) throws URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        sendSms(dto.getSmsType(), dto.getCountryCode(), dto.getRecipientPhoneNumber(), tempPassword);
    }


    public SmsResponseDto sendSms(SmsType smsType, String countryCode, String phoneNumber, String content) throws URISyntaxException, JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Long time = System.currentTimeMillis();
        List<MessageDto> messages = new ArrayList<>(); // 보내는 사람에게 내용을 보냄.
        String message = "";
        if (smsType == SmsType.SIGNUP) {
            message += "[ " + content + " ]" + SMS_SIGNUP_MESSAGE;
        } else if (smsType == SmsType.PW) {
            message += SMS_PW_MESSAGE_PREFIX + content + SMS_PW_MESSAGE_SUFFIX;
        } else {
            throw new CustomException(CustomExceptionStatus.SMS_TYPE_NOT_EXISTS);
        }

        messages.add(new MessageDto(phoneNumber, message)); // content부분이 내용임

        // 전체 json에 대해 메시지를 만든다.
        SmsRequestDto smsRequestDto = new SmsRequestDto("LMS", "COMM", countryCode, config.getFrom(), "내용", messages);

        // 쌓아온 바디를 json 형태로 변환시켜준다.
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequestDto);

        // 헤더에서 여러 설정값들을 잡아준다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", config.getAccessKey()); // 제일 중요한 signature 서명하기.
        String sig = makeSignature(time);
        headers.set("x-ncp-apigw-signature-v2", sig); // 위에서 조립한 jsonBody와 헤더를 조립한다.

        HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

        // restTemplate로 post 요청을 보낸다. 별 일 없으면 202 코드 반환된다.
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        return restTemplate.postForObject(
                new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + config.getServiceId() + "/messages"), body, SmsResponseDto.class);
    }

    public void validateCode(ValidateSmsCodeRequestDto dto) {
        certificationManager.validateSmsCode(dto.getSmsCode(), dto.getPhoneNumber());
    }

    private String makeSignature(Long time) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
        String space = " "; // one space
        String newLine = "\n"; // new line
        String method = "POST"; // method
        String url = "/sms/v2/services/"+config.getServiceId()+"/messages"; // url (include query string)
        String timestamp = time.toString(); // current timestamp (epoch)
        String accessKey = config.getAccessKey(); // access key id (from portal or Sub Account)
        String secretKey = config.getSecretKey();

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        return Base64.encodeBase64String(rawHmac);
    }
}

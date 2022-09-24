package com.server.whaledone.certification.repository;

import com.server.whaledone.certification.redis.SmsCode;
import org.springframework.data.repository.CrudRepository;

public interface SmsCodeRedisRepository extends CrudRepository<SmsCode, String> {
}

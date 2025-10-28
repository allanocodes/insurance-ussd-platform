package com.USSD.USSD_SERVICE.service;

import com.USSD.USSD_SERVICE.dto.UssdSession;
import org.apache.catalina.SessionIdGenerator;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SessionService {

    public static final Duration SESSION_TTL = Duration.ofMinutes(3);

    private final RedisTemplate<String,Object> redisTemplate;
    public SessionService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public UssdSession getOrSet(String sessionId,String serviceCode,String phoneNumber){
     String key = buildKey(sessionId);

     UssdSession ussdSession =(UssdSession) redisTemplate.opsForValue().get(key);
        if (ussdSession == null) {
           ussdSession = new UssdSession(sessionId,serviceCode,phoneNumber);
           save(ussdSession);
            return  ussdSession;
        }

        redisTemplate.expire(key, SESSION_TTL);
        return ussdSession;
    }

    public void save(UssdSession ussdSession){
        redisTemplate.opsForValue().set(buildKey(ussdSession.getSessionId()),ussdSession);
    }

    public String buildKey(String sessionId){

        return "USSD:SESSION:"+sessionId;
    }
    public void end(String sessionId) {
        redisTemplate.delete(buildKey(sessionId));
    }
}

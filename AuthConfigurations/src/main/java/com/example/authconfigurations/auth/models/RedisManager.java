package com.example.authconfigurations.auth.models;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class RedisManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final int TOKEN_TTL_SECONDS = 1200;
    private final long TOKEN_TTL_MINUTES = 20L;

    public void storeJwt(String jwt){
        redisTemplate.opsForValue().set(getKey(jwt), LocalDateTime.now().toString());
        redisTemplate.expire(getKey(jwt), TOKEN_TTL_SECONDS, TimeUnit.SECONDS);
    }

    public LocalDateTime getLastUsed(String jwt){
        String lastUsed = redisTemplate.opsForValue().get(getKey(jwt));
        return (lastUsed != null)? LocalDateTime.parse(lastUsed) : null;
    }

    public boolean isValidToken(String jwt){
        LocalDateTime lastUsed = getLastUsed(jwt);
        if(lastUsed == null) return false;

        return updateLastUsed(jwt);
    }

    public boolean updateLastUsed(String jwt){
        LocalDateTime now = LocalDateTime.now();
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String previousJwtToken = valueOps.get(getKey(jwt));

        if (previousJwtToken != null) {
            Duration duration = Duration.ofMinutes(TOKEN_TTL_MINUTES);
            valueOps.set(getKey(jwt), now.toString(), duration);
            return true;
        }
        return false;
    }

    public String getKey(String jwt){
        return "JWT:" + jwt + ":LAST_USED";
    }

}

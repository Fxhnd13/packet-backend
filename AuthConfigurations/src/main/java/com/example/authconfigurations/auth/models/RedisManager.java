package com.example.authconfigurations.auth.models;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @implNote Esta clase es la encargada de manejar la conexión con Redis
 */
@Component
public class RedisManager {

    /**
     * @implNote Esta es la instancia de la clase RedisTemplate que se utiliza para conectarse con Redis
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @implNote Este es el tiempo de vida (en segundos) de los tokens en Redis
     */
    private final int TOKEN_TTL_SECONDS = 1200;

    /**
     * @implNote Este es el tiempo de vida (en minutos) de los tokens en Redis
     */
    private final long TOKEN_TTL_MINUTES = 20L;

    /**
     * @implNote Este método es el encargado de almacenar el token en Redis
     * @param jwt Es el token que se va a almacenar
     */
    public void storeJwt(String jwt){
        redisTemplate.opsForValue().set(getKey(jwt), LocalDateTime.now().toString());
        redisTemplate.expire(getKey(jwt), TOKEN_TTL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * @implNote Este método es el encargado de obtener la última vez que se utilizó el token
     * @param jwt Es el token del que se obtiene la última vez que se utilizó
     * @return Retorna la última vez que se utilizó el token
     */
    public LocalDateTime getLastUsed(String jwt){
        String lastUsed = redisTemplate.opsForValue().get(getKey(jwt));
        return (lastUsed != null)? LocalDateTime.parse(lastUsed) : null;
    }

    /**
     * @implNote Este método es el encargado de validar si el token es válido
     * @param jwt Es el token que se va a validar
     * @return Retorna true si el token es válido, false en caso contrario
     */
    public boolean isValidToken(String jwt){
        LocalDateTime lastUsed = getLastUsed(jwt);
        if(lastUsed == null) return false;

        return updateLastUsed(jwt);
    }

    /**
     * @implNote Este método es el encargado de actualizar la última vez que se utilizó el token
     * @param jwt Es el token del que se va a actualizar la última vez que se utilizó
     * @return Retorna true si se actualizó la última vez que se utilizó el token, false en caso contrario
     */
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

    /**
     * @implNote Este método es el encargado de obtener la llave (En redis) del token
     * @param jwt Es el token del que se va a obtener la llave
     * @return Retorna la llave del token
     */
    public String getKey(String jwt){
        return "JWT:" + jwt + ":LAST_USED";
    }

}

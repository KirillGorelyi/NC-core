package org.nc.core.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@EnableAsync
@EnableScheduling
public class StaticJWTCache {

    @Autowired
    private TokenProvider tokenProvider;

    private static final ConcurrentMap<String, String> jwtCache = new ConcurrentHashMap<>();

    public static void addJwt(String jwt, String userName){
        synchronized (jwtCache){
            jwtCache.put(userName, jwt);
        }
    }

    public static boolean checkJwt(String jwt, String username){
        return jwtCache.get(username).equals(jwt);
    }

    @Scheduled(fixedDelay = 5)
    @Async
    void cleanCache(){
        System.out.println("anus");
        for(String key : jwtCache.keySet()){
            var value =jwtCache.get(key);
            if(tokenProvider.isTokenExpired(key)){
                jwtCache.remove(key, value);
            }
        }
    }
}

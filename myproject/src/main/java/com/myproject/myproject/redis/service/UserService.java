package com.myproject.myproject.redis.service;

import com.myproject.myproject.redis.entity.User;
import com.myproject.myproject.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static final String CACHE_PREFIX = "USER_";

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public User saveUser(User user){

        User saveUser = userRepository.save(user);
        redisTemplate.opsForValue().set(CACHE_PREFIX + saveUser.getId(),
               saveUser,10, TimeUnit.MINUTES );
        return saveUser;
    }

    public User getUser(Long id){

        String key = CACHE_PREFIX + id;

        Object cachedUser =redisTemplate.opsForValue().get(key);

        if(cachedUser != null){

            System.out.println("fetched from redis cache");
            return (User) cachedUser;
        }

        Optional<User> dbUser = userRepository.findById(id);
        if(dbUser.isPresent()){
            System.out.println("fetched from oracle db");

            redisTemplate.opsForValue().set(key,dbUser.get(),
                    10,TimeUnit.MINUTES);
        }
        return dbUser.orElse(null);
    }




}



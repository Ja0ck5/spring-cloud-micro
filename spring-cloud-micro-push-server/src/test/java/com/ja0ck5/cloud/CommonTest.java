package com.ja0ck5.cloud;

import java.util.Optional;

import org.junit.Test;

import com.ja0ck5.cloud.model.session.Session;

/**
 * Created by Jack on 2018/1/16.
 */
public class CommonTest {

    @Test
    public void testOptional(){
        // session of
//        Session session = new Session();
//        Optional<Session> sessionOptional = Optional.of(session);
//        if(sessionOptional.isPresent()){
//            System.out.println("session is present "+ sessionOptional.get());
//        }else{
//            System.out.println("session is not present "+ sessionOptional.get());
//        }
//        Optional<Session> sessionNullOptional = Optional.of(null);
//        if(sessionNullOptional.isPresent()){
//            System.out.println("sessionNullOptional is present");
//        }else{
//            System.out.println(sessionNullOptional.orElseGet(() ->new Session()));
//            System.out.println(sessionNullOptional.orElse(new Session()));
//        }
        // nullable
        Optional<Session> empty = Optional.ofNullable(null);
        if(empty.isPresent()){
            System.out.println("is present");
        }else{
//            System.out.println(empty.get());
        }
    }

}

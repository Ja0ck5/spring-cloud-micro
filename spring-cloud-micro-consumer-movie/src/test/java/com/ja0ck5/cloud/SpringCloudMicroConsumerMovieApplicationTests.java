package com.ja0ck5.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCloudMicroConsumerMovieApplicationTests {

	@Autowired
	RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
		User user = new User();
		user.setAge(1);
		user.setName("HHAHAH");
		String key = "test:cluster:1";
//		redisTemplate.opsForValue().set(key,user);
		redisTemplate.opsForValue().set(key,user,10, TimeUnit.SECONDS);

		User userGet = (User)redisTemplate.opsForValue().get(key);
//		System.out.println(userGet.getName());
		redisTemplate.delete(key);

	}

}

class User {
	String name;
	int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

package com.lipeng.test;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lipeng.common.utils.RandomUtil;
import com.lipeng.common.utils.StringUtil;
import com.lipeng.common.utils.UserUtils;
import com.lipeng.domain.User;

/**
 * @作者：李鹏
 *@date
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class RedisToTest {
	//引入redisTemplate类
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void JDKUser() {
		//计算开始时间
		long start = System.currentTimeMillis();
		//存储10万个对象
		for(int i=0;i<50000;i++) {
			//创建一个User对象
			User user=new User();
			user.setId(i);
			user.setName(StringUtil.randomChineseString(3));
			//System.out.println(user);
			user.setPhoneNum(UserUtils.getPhone());
			user.setGender(UserUtils.getSex());
			user.setEmail(UserUtils.getMail());
			user.setBirthday(UserUtils.getBirthday());
			System.out.println(user);
			//保存到redis中
			redisTemplate.opsForValue().set("user"+i, user);
		}
		//计算结束时间
		long end = System.currentTimeMillis();
		System.err.println("存储到redis所耗时:"+(end-start));
		System.err.println("采用的是JDK的序列化方式");
		System.err.println("存储了5万个User对象");
	}
	
	@Test
	public void JSONUser() {
		//计算开始时间
		long start = System.currentTimeMillis();
		//存储5万个对象
		for(int i=0;i<50000;i++) {
			//创建一个User对象
			User user=new User();
			user.setId(i);
			user.setName(StringUtil.randomChineseString(3));
			//System.out.println(user);
			user.setPhoneNum(UserUtils.getPhone());
			user.setGender(UserUtils.getSex());
			user.setEmail(UserUtils.getMail());
			user.setBirthday(UserUtils.getBirthday());
			//System.out.println(user);
			//保存到redis中
			redisTemplate.opsForValue().set("user"+i, user);
		}
		//计算结束时间
		long end = System.currentTimeMillis();
		//计算结束所
		System.err.println("存储到redis所耗时:"+(end-start));
		System.err.println("采用的是JSON的序列化方式");
		System.err.println("存储了5万个User对象");
	}
	
	@Test
	public void HashUser() {
		//创建一个hashset
		HashMap<String,User> map = new HashMap<String, User>();
		//计算开始时间
		long start = System.currentTimeMillis();
		//存储5万个对象
		for(int i=0;i<50000;i++) {
			//创建一个User对象
			User user=new User();
			user.setId(i);
			user.setName(StringUtil.randomChineseString(3));
			//System.out.println(user);
			user.setPhoneNum(UserUtils.getPhone());
			user.setGender(UserUtils.getSex());
			user.setEmail(UserUtils.getMail());
			user.setBirthday(UserUtils.getBirthday());
			System.out.println(user);
			//保存到redis中
			redisTemplate.opsForHash().put("user"+i, "user"+i, user);
		}
		//将map集合添加到redis中
		redisTemplate.opsForHash().putAll("users", map);
		//计算结束时间
		long end = System.currentTimeMillis();
		System.err.println("存储到redis所耗时:"+(end-start));
		System.err.println("采用的是HASH的序列化方式");
		System.err.println("存储了5万个User对象");
	}
	
	
}

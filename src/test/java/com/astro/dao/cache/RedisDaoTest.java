package com.astro.dao.cache;

import com.astro.dao.SeckillDao;
import com.astro.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.SchemaOutputResolver;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml" })
public class RedisDaoTest {
   private long id =1000;

   @Autowired
   private RedisDao redisDao;

   @Autowired
   private SeckillDao seckillDao;

    @Test
    public void testSeckill() throws Exception {

        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null){
            seckill = seckillDao.queryById(id);
            if (seckill != null){
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill= redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }
    @Test
    public void testSeckill1() throws Exception {

        Seckill seckill = redisDao.getSeckill(id);
        System.out.println(seckill);
    }
    @Test
    public void testSeckill2() throws Exception {

        Seckill seckill =  seckillDao.queryById(id);
        redisDao.putSeckill(seckill);
        System.out.println(seckill);
    }

    @Test
    public void sss(){
        //byte a[]={"\b\xe8\a\x12\x141000\xe5\x85\x83\xe7\xa7\x92\xe6\x9d\x80iphone6\x18\\!\x00t\x9b\xe9_\x01\x00\x00)\x00\xf8\xa7\r`\x01\x00\x001x\xd4\xac\xdc_\x01\x00\x00"};


    }



}
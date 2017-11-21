package com.astro.dao;

import com.astro.dao.SeckillDao;
import com.astro.entity.Seckill;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        Date killTime=new Date();
        int i = seckillDao.reduceNumber(1000, killTime);
        System.out.println(i);
    }

    @Test
    public void queryById() throws Exception {
        long id=1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill.getNumber());
        System.out.println(seckill);
        System.out.println(seckill.getStartTime());
    }

    @Test
    public void queryAll() throws Exception {

        List<Seckill> seckills = seckillDao.queryAll(0, 2);
        for (Seckill seckill:seckills){
            System.out.println(seckill);
        }
    }

}
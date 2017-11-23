package com.astro.dao;

import com.astro.entity.Successkilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        int i = successKilledDao.insertSuccessKilled(1000, 111);
        System.out.println(i);

    }

    @Test
    public void queryByIdWithSeckill() throws Exception {

        Successkilled result = successKilledDao.queryByIdWithSeckill(1000,123);
        System.out.println(result);
        System.out.println(result.getSeckill());
    }

}
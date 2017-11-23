package com.astro.service;

import com.astro.dto.Exposer;
import com.astro.dto.SeckillExecution;
import com.astro.entity.Seckill;
import com.astro.exception.RepeatKillException;
import com.astro.exception.SeckillEndException;
import com.astro.exception.SeckillException;

import java.util.List;

public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);


    /**
     * 秒杀接口是否开启  输出秒杀接口
     * 否则输出秒杀时间和系统时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,SeckillEndException,RepeatKillException;
}

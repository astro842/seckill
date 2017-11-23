package com.astro.service.impl;

import com.astro.dao.SeckillDao;
import com.astro.dao.SuccessKilledDao;
import com.astro.dto.Exposer;
import com.astro.dto.SeckillExecution;
import com.astro.entity.Seckill;
import com.astro.entity.Successkilled;
import com.astro.enums.SeckillStateEnum;
import com.astro.exception.RepeatKillException;
import com.astro.exception.SeckillEndException;
import com.astro.exception.SeckillException;
import com.astro.service.SeckillService;
import com.astro.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.image.OffScreenImage;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (startTime.getTime() > nowTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, startTime.getTime(), endTime.getTime(), nowTime.getTime());
        }
        String md5 = Md5Util.getMd5(seckillId);

        return new Exposer(true, md5, seckillId);
    }

    /**
     * 执行秒杀
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws SeckillEndException
     * @throws RepeatKillException
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillEndException, RepeatKillException {

        if (md5 == null || !md5.equals(Md5Util.getMd5(seckillId))) {
            throw new SeckillException("md5错误");
        }
        //减库存 +记录

        Date nowTime = new Date();

        try {
            int i = seckillDao.reduceNumber(seckillId, nowTime);
            if (i <= 0) {
                throw new SeckillEndException("秒杀结束");
            } else {
                int insert = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insert <= 0) {
                    throw new RepeatKillException("重复秒杀");
                } else {
                    Successkilled successkilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successkilled);
                }
            }
        } catch (SeckillEndException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (SeckillException e) {
            logger.error(e.getMessage(),e);
            throw new SeckillException("秒杀错误:"+e.getMessage());
        }


    }
}
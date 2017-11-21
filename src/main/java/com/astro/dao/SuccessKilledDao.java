package com.astro.dao;

import com.astro.entity.Successkilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {


    /**
     * 插入购买明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled (@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     *
     * @param seckillId
     * @return
     */
    Successkilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);


}

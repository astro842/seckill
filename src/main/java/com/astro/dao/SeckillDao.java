package com.astro.dao;

import com.astro.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SeckillDao {

    /**
     *减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber (@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);


    /**
     *
     * @param offset
     * @param limit
     * @return
     */

    List<Seckill> queryAll(@Param("offset") int offset ,@Param("limit") int limit);

}

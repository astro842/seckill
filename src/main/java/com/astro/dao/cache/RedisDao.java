package com.astro.dao.cache;

import com.astro.entity.Seckill;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

    private JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }

    public Seckill getSeckill(Long seckillId){

       try {
           Jedis jedis = jedisPool.getResource();
           try {
               String key = "seckill:" + seckillId;
               //get ->byte[] ->反序列 ->object(seckill)
               byte[] bytes = jedis.get(key.getBytes());
               if (bytes != null){
                   //空对象
                   Seckill seckill = schema.newMessage();
                   //通过schema把bytes传到空对象seckill中  反序列
                   ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                   return seckill;
               }
           }finally {
                jedis.close();
           }
       }catch (Exception e){
            e.printStackTrace();
       }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60;//1小时
                String result = jedis.setex(key.getBytes(),timeout,bytes);
                System.out.println(result);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }




}

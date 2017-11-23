package com.astro.util;

import org.springframework.util.DigestUtils;

public class Md5Util {

    private static final String salt="adfadf87a987d98ahkadha8d";



    public static String getMd5(long seckillId){
        String base = seckillId +"/" +salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return  md5;
    }

}

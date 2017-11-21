package com.astro.entity;

import java.util.Date;

public class Successkilled {

    private long seckillId;

    private long username;

    private short state;

    private Date createTime;

    private Seckill seckill;

    @Override
    public String toString() {
        return "Successkilled{" +
                "seckillId=" + seckillId +
                ", username=" + username +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUsername() {
        return username;
    }

    public void setUsername(long username) {
        this.username = username;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

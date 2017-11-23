package com.astro.dto;

import com.astro.entity.Successkilled;
import com.astro.enums.SeckillStateEnum;

public class SeckillExecution {

    private long seckillId;

    private int state;

    private String stateInfo;

    private Successkilled successkilled;


    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, Successkilled successkilled) {
        this.seckillId = seckillId;
        this.state = stateEnum.getCode();
        this.stateInfo=stateEnum.getMsg();
        this.successkilled = successkilled;
    }


    public SeckillExecution(long seckillId,SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getCode();
        this.stateInfo = stateEnum.getMsg();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Successkilled getSuccesskilled() {
        return successkilled;
    }

    public void setSuccesskilled(Successkilled successkilled) {
        this.successkilled = successkilled;
    }
}

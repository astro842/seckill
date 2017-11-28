package service.impl;

import com.astro.dto.Exposer;
import com.astro.dto.SeckillExecution;
import com.astro.entity.Seckill;
import com.astro.exception.RepeatKillException;
import com.astro.exception.SeckillEndException;
import com.astro.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                       "classpath:spring/spring-service.xml"    })
public class SeckillServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000);
        logger.info("seckill={}",seckill);
       // exposer=Exposer{exposed=true, md5='059efb35e53e36be89c9d91c90fcd991', seckillId=1000,
    }

    @Test
    public void exportSeckillUrl() throws Exception {

        Exposer exposer = seckillService.exportSeckillUrl(1000);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long userPhone=1213;
        String md5="059efb35e53e36be89c9d91c90fcd991";
        SeckillExecution exposer = seckillService.executeSeckill(1000,userPhone,md5);
        logger.info("exposer={}",exposer);

    }

    @Test
    public void exportSeckillUrlAndExecuteSeckill() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1001);
        if (exposer.isExposed()){
            long userPhone=1216;
            String md5="059efb35e53e36be89c9d91c90fcd991";
            try {
                SeckillExecution exposer2 = seckillService.executeSeckill(1000,userPhone,md5);
                logger.info("exposer={}",exposer2);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillEndException e1){
                logger.error(e1.getMessage());
            }
        }
        else{
            logger.info("exposer={}",exposer);
        }

    }

}
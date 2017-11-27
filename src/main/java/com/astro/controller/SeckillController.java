package com.astro.controller;


import com.astro.dto.Exposer;
import com.astro.dto.SeckillExecution;
import com.astro.dto.SeckillResult;
import com.astro.entity.Seckill;
import com.astro.enums.SeckillStateEnum;
import com.astro.exception.RepeatKillException;
import com.astro.exception.SeckillEndException;
import com.astro.exception.SeckillException;
import com.astro.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    public String list(Model model){

        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId,
                        Model model){
        if (seckillId == null){
            return "redirect:/sectkill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null){
            return "forward:/sectkill/list";
        }
        model.addAttribute("seckill",seckill);

        return "detail";
    }
    @RequestMapping(value = "/{seckillId}/exposer" , method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;

        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (SeckillException e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long secKillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone",required = false) Long Phone){
            SeckillResult<SeckillExecution> result;
            if (Phone == null){
                return new SeckillResult<SeckillExecution>(false,"未注册");
            }

            try {
                SeckillExecution seckill = seckillService.executeSeckill(secKillId, Phone, md5);
                return new SeckillResult<SeckillExecution>(true,seckill);
            }catch (RepeatKillException e){
                SeckillExecution execution = new SeckillExecution(secKillId, SeckillStateEnum.REPEAT_KILL);
                return new SeckillResult<SeckillExecution>(true,execution);
            }
            catch (SeckillEndException e){
                SeckillExecution execution = new SeckillExecution(secKillId, SeckillStateEnum.END);
                return new SeckillResult<SeckillExecution>(true,execution);
            }
            catch (SeckillException e){
                logger.error(e.getMessage(),e);
                return new SeckillResult<SeckillExecution>(true,e.getMessage());
            }


    }


    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }

}

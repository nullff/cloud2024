package com.atguigu.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.cloud.service.FlowLimitService;
import io.micrometer.core.instrument.util.TimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA(){
        return "------testA------";
    }
    @GetMapping("/testB")
    public String testB(){
        return "------testB------";
    }

    @Resource
    private FlowLimitService flowLimitService;
    @GetMapping("/testC")
    public String testC(){
        flowLimitService.common();
        return "------testC------";
    }
    @GetMapping("/testD")
    public String testD(){
        flowLimitService.common();
        return "------testD------";
    }

    @GetMapping("/testE")
    public String testE(){
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("熔断规则-慢调用比例");
        return "------testE------";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "dealHandler_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,

                             @RequestParam(value = "p2",required = false) String p2){
        return "------testHotKey";
    }
    public String dealHandler_testHotKey(String p1, String p2, BlockException exception)
    {
        return "-----dealHandler_testHotKey";
    }


}

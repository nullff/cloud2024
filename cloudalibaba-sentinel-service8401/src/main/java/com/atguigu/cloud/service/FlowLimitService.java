package com.atguigu.cloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlowLimitService {
    @SentinelResource(value = "common")
    public void common(){
        System.out.println("------FlowLimitService come in------");
    }
}

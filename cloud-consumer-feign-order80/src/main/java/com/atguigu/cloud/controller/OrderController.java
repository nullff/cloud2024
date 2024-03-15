package com.atguigu.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.cloud.api.PayFeignApi;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@Slf4j
public class OrderController {
    public static final String PaymentSrv_URL = "http://cloud-payment-service";

    @Autowired
    private PayFeignApi payFeignApi;

    @PostMapping("/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){
        ResultData resultData = payFeignApi.addPay(payDTO);
        log.info("打印addOrder的ResultData:{}", resultData);
        return resultData;
    }

    @GetMapping("/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        System.out.println("=========支付微服务远程调用，feign根据id获取支付记录========");
        ResultData resultData = null;
        try {
            System.out.println("====调用开始===="+ DateUtil.now());
            resultData = payFeignApi.getPayById(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("====调用异常===="+ DateUtil.now());
            return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }

        log.info("打印getPayInfo的ResultData:{}", resultData);
        return resultData;
    }

    @DeleteMapping("/feign/pay/delete/{id}")
    public ResultData deletePayInfo(@PathVariable("id") Integer id){
        ResultData resultData = payFeignApi.deletePay(id);
        log.info("打印deletePayInfo的ResultData:{}", resultData);
        return resultData;
    }

    @PutMapping("/feign/pay/update")
    public ResultData updatePayInfo(@RequestBody PayDTO payDTO){
        ResultData resultData = payFeignApi.updatePay(payDTO);
        log.info("打印updatePayInfo的ResultData:{}", resultData);
        return resultData;
    }

    @GetMapping(value = "/feign/pay/getConfigInfo")
    private ResultData getInfoByConsul()
    {
        return payFeignApi.getConfigInfo();
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }

        return instances.get(0).getServiceId()+":"+instances.get(0).getPort()+"\t"+instances.get(1).getServiceId()+":"+instances.get(1).getPort();
    }


}

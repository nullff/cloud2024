package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@Slf4j
public class OrderController {
    public static final String PaymentSrv_URL = "http://cloud-payment-service";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){
        ResultData resultData = restTemplate.postForObject(PaymentSrv_URL + "/pay/add", payDTO, ResultData.class);
        log.info("打印addOrder的ResultData:{}", resultData);
        return resultData;
    }

    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        ResultData resultData = restTemplate.getForObject(PaymentSrv_URL + "/pay/get/" + id, ResultData.class);
        log.info("打印getPayInfo的ResultData:{}", resultData);
        return resultData;
    }

    @GetMapping("/consumer/pay/delete/{id}")
    public ResultData deletePayInfo(@PathVariable("id") Integer id){
        ResultData resultData = restTemplate.getForObject(PaymentSrv_URL + "/pay/delete/" + id, ResultData.class);
        log.info("打印deletePayInfo的ResultData:{}", resultData);
        return resultData;
    }

    @GetMapping("/consumer/pay/update")
    public ResultData updatePayInfo(@RequestBody PayDTO payDTO){
        ResultData resultData = restTemplate.postForObject(PaymentSrv_URL + "/pay/update", payDTO, ResultData.class);
        log.info("打印updatePayInfo的ResultData:{}", resultData);
        return resultData;
    }

    @GetMapping(value = "/consumer/pay/getConfigInfo")
    private ResultData getInfoByConsul()
    {
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/getConfigInfo", ResultData.class);
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

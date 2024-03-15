package com.atguigu.cloud.controller;


import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.PayService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Tag(name = "支付微服务模块", description = "支付CRUD操作")
public class PayController {
    @Resource
    private PayService payService;

    @Operation(summary = "添加支付记录", description = "添加支付记录,json串作为参数")
    @PostMapping("/pay/add")
    public ResultData<String> addPay(@RequestBody PayDTO payDTO){
        log.info(payDTO.toString());
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);
        int i = payService.add(pay);
        return ResultData.success("成功插入记录，返回值：" + i);

    }

    @Operation(summary = "删除支付记录", description = "根据id删除支付记录")
    @DeleteMapping("/pay/delete/{id}")
    public ResultData<Integer> deletePay(@PathVariable("id") Integer id){
        return ResultData.success(payService.delete(id));

    }

    @Operation(summary = "更新支付记录", description = "更新支付记录,json串作为参数")
    @PutMapping("/pay/update")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO){
        Pay pay = new Pay();
//        pay.setId(payDTO.getId());
//        pay.setPayNo(payDTO.getPayNo());
//        pay.setOrderNo(payDTO.getOrderNo());
//        pay.setUserId(payDTO.getUserId());
//        pay.setAmount(payDTO.getAmount());
        BeanUtils.copyProperties(payDTO, pay);
        int i = payService.update(pay);
        return ResultData.success("成功更新记录，返回值：" + i);

    }

    @Operation(summary = "根据id获取支付记录", description = "根据id获取支付记录")
    @GetMapping("/pay/get/{id}")
    public ResultData<Pay> getPayById(@PathVariable("id") Integer id){

        //测试超时bug

        try {
            TimeUnit.SECONDS.sleep(62);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResultData.success(payService.getById(id));
    }

    @Operation(summary = "获取所有支付记录", description = "获取所有支付记录")
    @GetMapping("/pay/getAll")
    public ResultData<List<PayDTO>> getAllPay(){

        List<Pay> payList = payService.getAll();
        List<PayDTO> payDTOList = new ArrayList<>(payList.size());
        for (Pay pay : payList) {
            PayDTO payDTO = new PayDTO();
            BeanUtils.copyProperties(pay, payDTO);
            payDTOList.add(payDTO);
        }
        return ResultData.success(payDTOList);

    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/pay/getConfigInfo")
    public ResultData<String> getConfigInfo(@Value("${atguigu.info}") String atguigu){
            return ResultData.success("port:" + port + ", atguigu:" + atguigu);
    }

}

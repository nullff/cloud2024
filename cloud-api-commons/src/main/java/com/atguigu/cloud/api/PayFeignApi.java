package com.atguigu.cloud.api;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//@FeignClient(value = "cloud-payment-service")
@FeignClient(value = "cloud-gateway")
public interface PayFeignApi {

    @PostMapping("/pay/add")
    public ResultData<String> addPay(@RequestBody PayDTO payDTO);

    @DeleteMapping("/pay/delete/{id}")
    public ResultData<Integer> deletePay(@PathVariable("id") Integer id);

    @PutMapping("/pay/update")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO);

    @GetMapping("/pay/get/{id}")
    public ResultData<PayDTO> getPayById(@PathVariable("id") Integer id);

    @GetMapping("/pay/getConfigInfo")
    public ResultData<String> getConfigInfo();

    @GetMapping("/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id);

    @GetMapping("/pay/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id);

    @GetMapping("/pay/ratelimit/{id}")
    public String myRatelimit(@PathVariable("id") Integer id);

    @GetMapping("/pay/micrometer/{id}")
    public String myMicrometer(@PathVariable("id") Integer id);

    @GetMapping("/pay/gateway/get/{id}")
    public ResultData<PayDTO> getById(@PathVariable("id") Integer id);

    @GetMapping("/pay/gateway/info")
    public ResultData<String> getGatewayInfo();
}

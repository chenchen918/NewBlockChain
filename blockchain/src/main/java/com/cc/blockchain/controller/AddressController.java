package com.cc.blockchain.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/getInfoByAddress")
    public JSONObject getInfoByAddress(@RequestParam String address){
        Integer  TransactionAmount  = transactionDetailService.getAmountByAddress(address);
        Double gain = transactionDetailService.getGainByAddres(address);
        Double payment = transactionDetailService.getPaymentByAddress(address);
        Double balance = gain + payment;


        JSONObject addressJson = new JSONObject();
        addressJson.put("address", address);
        addressJson.put("txSize", TransactionAmount);
        addressJson.put("gain", gain);
        addressJson.put("payment", Math.abs(payment));
        addressJson.put("balance", balance);

        return addressJson;
    }

}

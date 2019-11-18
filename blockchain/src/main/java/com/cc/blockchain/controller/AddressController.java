package com.cc.blockchain.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/getInfoByAddress")
    public JSONObject getInfoByAddress(@RequestParam String address){
        Integer  TransactionAmount  = transactionDetailService.getAmountByAddress(address);

        return null;
    }

}

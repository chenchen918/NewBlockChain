package com.cc.blockchain.controller;

import com.cc.blockchain.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/syncdata")
@EnableAutoConfiguration
public class SyncDataController {

    @Autowired
    private BlockService blockService;

    @PostMapping("/fullImport")
    public void fullImport(@RequestParam(required = false, defaultValue = "00000000000000a00547f126c4be1ab7143f0902618f9b1aee495fa1e4d7d759") String blockhash){
        blockService.syncBlocks(blockhash);
    }
}

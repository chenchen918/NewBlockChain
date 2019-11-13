package com.cc.blockchain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
@MapperScan("com.cc.blockchain.dao")
public class BlockchainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApplication.class, args);
    }

}

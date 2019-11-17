package com.cc.blockchain.service;

import com.cc.blockchain.po.Block;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;

public interface BlockService {

    String syncBlock(String blockhash);

    @Async
    void syncBlocks(String fromBlockhash);

    PageInfo<Block> getBlockPage(Integer pageNum);

    Block getBlockByhash(String blockhash);
}

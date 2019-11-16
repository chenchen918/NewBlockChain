package com.cc.blockchain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.client.BitcoinRest;
import com.cc.blockchain.dao.BlockMapper;
import com.cc.blockchain.po.Block;
import com.cc.blockchain.service.BlockService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Override
    public String syncBlock(String blockhash) {
        JSONObject blockJson = bitcoinRest.getBlockNoTxDetails(blockhash);
        Block block = new Block();
        block.setBlockhash(blockJson.getString("hash"));
        block.setConfirmations(blockJson.getInteger("confirmations"));
        block.setHeight(blockJson.getInteger("height"));
        block.setTime(blockJson.getLong("time"));
        block.setDifficulty(blockJson.getDouble("difficulty"));
        block.setSizeondisk(blockJson.getInteger("size"));
        block.setMerkleRoot(blockJson.getString("merkleroot"));
        block.setTxsize(blockJson.getInteger("nTx"));
        block.setVersion(blockJson.getString("versionHex"));
        block.setNonce(blockJson.getInteger("nonce").toString());
        block.setWeight(blockJson.getInteger("weight"));


        blockMapper.insert(block);
        Integer blockId = block.getBlockId();
        Long time = block.getTime();

        ArrayList<String> txids = (ArrayList<String>) blockJson.get("tx");
        for (String txid : txids) {
            transactionService.syncTransaction(txid, blockId, time);
        }

        String nextblockhash = blockJson.getString("nextblockhash");
        return nextblockhash;
    }

    @Override
    @Async
    public void syncBlocks(String fromBlockhash) {
        logger.info("同 步 开 始");
        String BlockHashs = fromBlockhash;
        while (BlockHashs != null){
            BlockHashs = syncBlock(BlockHashs);
        }
        logger.info("同 步 结 束 ");
    }

    @Override
    public PageInfo<Block> getBlockPage(Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        List<Block> listBlock=blockMapper.getBlockPage();
        PageInfo<Block> blockPageInfo = new PageInfo<>(listBlock);
        return blockPageInfo;
    }

    @Override
    public Block getBlockByhash(String blockhash) {
        Block blockByhash = blockMapper.getBlockByhash(blockhash);
        return blockByhash;
    }


    @Override
    public Block getBlockByHeight(Integer height) {

        Block blockByHeight = blockMapper.getBlockByHeight(height);
        return blockByHeight;
    }



}

package com.cc.blockchain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.client.BitcoinRest;
import com.cc.blockchain.dao.BlockMapper;
import com.cc.blockchain.po.Block;
import com.cc.blockchain.po.Transaction;
import com.cc.blockchain.service.BlockService;

import com.cc.blockchain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private TransactionService transactionService;
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
    public void syncBlocks(String fromBlockhash) {
        String tempBlockhash = fromBlockhash;
        while (tempBlockhash != null){
            tempBlockhash = syncBlock(tempBlockhash);
        }
    }
}

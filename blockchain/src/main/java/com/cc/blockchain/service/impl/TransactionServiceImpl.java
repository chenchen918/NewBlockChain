package com.cc.blockchain.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.client.BitcoinRest;
import com.cc.blockchain.dao.TransactionMapper;
import com.cc.blockchain.po.Transaction;
import com.cc.blockchain.service.TransactionDetailService;
import com.cc.blockchain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionDetailService transactionDetailService;

    @Override
    public void syncTransaction(String txid, Integer blockId, Long time) {
        JSONObject transactionJson = bitcoinRest.getTransaction(txid);
        Transaction transaction = new Transaction();

        transaction.setBlockId(blockId);
        transaction.setTxid(transactionJson.getString("txid"));
        transaction.setTxhash(transactionJson.getString("hash"));
        transaction.setSizeondisk(transactionJson.getInteger("size"));
        transaction.setWeight(transactionJson.getInteger("weight"));
        transaction.setStatus((byte)0);
        transaction.setTime(time);

        transactionMapper.insert(transaction);

        Integer transactionId = transaction.getTransactionId();

        List<JSONObject> outs = transactionJson.getJSONArray("vout").toJavaList(JSONObject.class);
        for (JSONObject out : outs) {
            transactionDetailService.syncTxDetailVout(out, transactionId);
        }



    }
}

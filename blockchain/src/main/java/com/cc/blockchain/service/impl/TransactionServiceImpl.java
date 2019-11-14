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
        transaction.setSizeondisk(transactionJson.getInteger("size"));
        transaction.setStatus((byte)0);
        transaction.setTime(time);
        transaction.setTxhash(transactionJson.getString("hash"));
        transaction.setTxid(transactionJson.getString("txid"));
        transaction.setWeight(transactionJson.getInteger("weight"));

        transactionMapper.insert(transaction);

        Integer transactionId = transaction.getTransactionId();

        List<JSONObject> vouts = transactionJson.getJSONArray("vout").toJavaList(JSONObject.class);
        for (JSONObject vout : vouts) {
            transactionDetailService.syncTxDetailVout(vout, transactionId);
        }



    }
}

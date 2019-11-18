package com.cc.blockchain.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.client.BitcoinRest;
import com.cc.blockchain.constants.PageConfig;
import com.cc.blockchain.dao.TransactionMapper;
import com.cc.blockchain.po.Transaction;
import com.cc.blockchain.service.TransactionDetailService;
import com.cc.blockchain.service.TransactionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    @Override
    public List<Transaction> getBlockById(Integer blockId) {
        List<Transaction> transactions = transactionMapper.getBlockById(blockId);
        return transactions;
    }

    @Override
    public Page<Transaction> getPageBlockByBlockId(Integer blockId, Integer pageNum) {
        PageHelper.startPage(pageNum, PageConfig.PAGE_SIZE);
        Page<Transaction> transactions = transactionMapper.selectPageBlockByBlockId(blockId);
        return transactions;
    }

    @Override
    public Transaction getTransactionByTxid(String txid) {
        Transaction transaction= transactionMapper.selectTransactionByTxid(txid);
        return transaction;
    }

    @Override
    public Page<Transaction> getTransactionByAddressPage(String address, Integer pageNum) {
        PageHelper.startPage(pageNum, PageConfig.PAGE_SIZE);
        Page<Transaction> transactions = transactionMapper.selectTransactionByAddress(address);
        return transactions;
    }
}

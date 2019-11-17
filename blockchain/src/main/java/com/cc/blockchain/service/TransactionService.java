package com.cc.blockchain.service;

import com.cc.blockchain.po.Transaction;

import java.util.List;

public interface TransactionService {
    void syncTransaction(String txid, Integer blockId, Long time);

    List<Transaction> getBlockById(Integer blockId);
}

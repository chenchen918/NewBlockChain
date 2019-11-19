package com.cc.blockchain.service;

import com.cc.blockchain.po.Transaction;
import com.github.pagehelper.Page;

import java.util.List;

public interface TransactionService {
    void syncTransaction(String txid, Integer blockId, Long time);

    List<Transaction> getBlockById(Integer blockId);

    Page<Transaction> getPageBlockByBlockId(Integer blockId, Integer pageNum);

    Transaction getTransactionByTxid(String txid);

    Page<Transaction> getTransactionByAddressPage(String address, Integer pageNum);

    List<Transaction> getTransactionList();


}

package com.cc.blockchain.dao;

import com.cc.blockchain.po.Transaction;
import com.github.pagehelper.Page;

import java.util.List;

public interface TransactionMapper {
    int deleteByPrimaryKey(Integer transactionId);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(Integer transactionId);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    List<Transaction> getBlockById(Integer blockId);

    Page<Transaction> selectPageBlockByBlockId(Integer blockId);

    Transaction selectTransactionByTxid(String txid);
}
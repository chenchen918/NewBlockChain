package com.cc.blockchain.dao;

import com.cc.blockchain.po.Transaction;

public interface TransactionMapper {
    int deleteByPrimaryKey(Integer transactionId);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(Integer transactionId);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);
}
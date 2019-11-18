package com.cc.blockchain.dao;

import com.cc.blockchain.po.TransactionDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionDetailMapper {
    int deleteByPrimaryKey(Long txDetailId);

    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    TransactionDetail selectByPrimaryKey(Long txDetailId);

    int updateByPrimaryKeySelective(TransactionDetail record);

    int updateByPrimaryKey(TransactionDetail record);

    List<TransactionDetail> selectTransactionById(@Param("transactionId") Integer transactionId);

    List<TransactionDetail> selectTransactionByTransactionId(Integer transactionId);

    Integer selectAmountByAddress(String address);

    Double selectGainByAddres(String address);

    Double selectPaymentByAddress(String address);
}
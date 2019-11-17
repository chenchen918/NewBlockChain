package com.cc.blockchain.service;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.po.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    void syncTxDetailVout(JSONObject vout, Integer transactionId);

    void syncTxDetailVin(JSONObject vin, Integer transactionId);


    List<TransactionDetail> getTransactionById(Integer transactionId);
}

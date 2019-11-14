package com.cc.blockchain.service;

import com.alibaba.fastjson.JSONObject;

public interface TransactionDetailService {
    void syncTxDetailVout(JSONObject vout, Integer transactionId);


}

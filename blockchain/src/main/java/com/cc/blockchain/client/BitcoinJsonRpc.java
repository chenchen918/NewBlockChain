package com.cc.blockchain.client;

import com.alibaba.fastjson.JSONObject;

public interface BitcoinJsonRpc {

    JSONObject getRawTransaction(String txid) throws Throwable;

}

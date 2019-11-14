package com.cc.blockchain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.client.BitcoinRest;
import com.cc.blockchain.dao.TransactionDetailMapper;
import com.cc.blockchain.enumType.TxDetailType;
import com.cc.blockchain.po.TransactionDetail;
import com.cc.blockchain.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @Override
    public void syncTxDetailVout(JSONObject vout, Integer transactionId) {
        TransactionDetail transactionDetail = new TransactionDetail();
        JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses != null){
            String address = (String) addresses.get(0);
            transactionDetail.setAddress(address);
            transactionDetail.setAmount(vout.getDouble("value"));
            transactionDetail.setType((byte) TxDetailType.Receive.ordinal());
            transactionDetail.setTransactionId(transactionId);

            transactionDetailMapper.insert(transactionDetail);
        }
    }





}

package com.cc.blockchain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.client.BitcoinJsonRpcImp;
import com.cc.blockchain.client.BitcoinRest;
import com.cc.blockchain.dao.TransactionDetailMapper;
import com.cc.blockchain.enumType.TxDetailType;
import com.cc.blockchain.po.TransactionDetail;
import com.cc.blockchain.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @Autowired
    private BitcoinJsonRpcImp bitcoinJsonRpcImp;

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

    @Override
    public void syncTxDetailVin(JSONObject vin, Integer transactionId) {
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTransactionId(transactionId);
        transactionDetail.setType((byte)TxDetailType.Send.ordinal());
        String txidVin = vin.getString("txid");
        Integer n = vin.getInteger("vout");
        if (txidVin != null && n != null){
            try {
                JSONObject transaction = bitcoinJsonRpcImp.getRawTransaction(txidVin);
                JSONArray outs = transaction.getJSONArray("vout");
                JSONObject out = outs.getJSONObject(n);
                Double amount = out.getDouble("value");
                transactionDetail.setAmount(-amount);
                JSONObject scriptKey = out.getJSONObject("scriptPubKey");
                JSONArray addresses = scriptKey.getJSONArray("addresses");
                if (addresses != null){
                    String address = addresses.getString(0);
                    transactionDetail.setAddress(address);
                    transactionDetailMapper.insert(transactionDetail);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }

    }

    @Override
    public List<TransactionDetail> getTransactionById(Integer transactionId) {
        List<TransactionDetail> transactionDetails = transactionDetailMapper.selectTransactionById(transactionId);
        return transactionDetails;
    }

    @Override
    public List<TransactionDetail> getTransactionByTransactionId(Integer transactionId) {
        List<TransactionDetail> transactionDetails = transactionDetailMapper.selectTransactionByTransactionId(transactionId);
        return transactionDetails;
    }

    public List<TransactionDetail> getByTransactionId(Integer transactionId) {
        List<TransactionDetail> transactionDetails = transactionDetailMapper.selectTransactionById(transactionId);
        return transactionDetails;
    }


}

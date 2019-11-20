package com.cc.blockchain.scheduler;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.client.BitcoinRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BitcoinScheduler {

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<JSONObject> deltaTransactions = new LinkedList<>();

    private JSONObject originMempoolTransaction  = new JSONObject();

    @Scheduled(cron = "${bitcoin.syncMempoolTx.interval}")
    public void syncMempoolTx(){
        logger.info("开始同步");

        JSONObject newMempoolTx = bitcoinRest.getMempoolContents();

        int originSize = originMempoolTransaction.size();
        int newSize = newMempoolTx.size();
        if (newSize <= originSize){
            return;
        }

        for (Map.Entry<String, Object> entry : newMempoolTx.entrySet()) {
            String key = entry.getKey();
            if (!originMempoolTransaction.containsKey(key)){
                JSONObject addJson = newMempoolTx.getJSONObject(key);
                addJson.put("txid", key);
                deltaTransactions.add(addJson);
            }
        }

        logger.info("delta tx: {}", deltaTransactions);
        logger.info("delta size: {}", deltaTransactions.size());

        List<JSONObject> deltaTxesJsons = deltaTransactions.stream().map(t -> {
            JSONObject tJson = new JSONObject();
            tJson.put("txid", t.getString("txid"));
            tJson.put("time", t.getLong("time"));
            tJson.put("wtxid", t.getString("wtxid"));


            return tJson;
        }).collect(Collectors.toList());
        List<JSONObject> sortedDeltaTxesJsons = deltaTxesJsons.stream().sorted(Comparator.comparingLong(t -> t.getLong("time"))).collect(Collectors.toList());
        simpMessagingTemplate.convertAndSend("/bitcoin/deltaTx", sortedDeltaTxesJsons);

        deltaTransactions = new LinkedList<>();
        originMempoolTransaction = newMempoolTx;

        logger.info("end sync mempool tx");
    }


}

package com.cc.blockchain.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.constants.PageConfig;
import com.cc.blockchain.dto.PageDTO;
import com.cc.blockchain.po.Block;
import com.cc.blockchain.po.Transaction;
import com.cc.blockchain.po.TransactionDetail;
import com.cc.blockchain.service.BlockService;
import com.cc.blockchain.service.TransactionDetailService;
import com.cc.blockchain.service.TransactionService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private BlockService blockService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/getRecentUnconfirmed")
    public List<JSONObject> getRecentUnconfirmed(@RequestParam(required = false, defaultValue = "20") Integer size){
        return null;
    }

    @GetMapping("/getByTxhash")
    public JSONObject getByTxhash(@RequestParam String txhash){
        return null;
    }

    @GetMapping("/getBlockByHeightPage")
    public PageDTO<JSONObject> getBlockByHeightPage(@RequestParam Integer height,
                                                      @RequestParam(defaultValue = "1") Integer pageNum){
        Block block = blockService.getBlockByHeight(height);
        Integer blockId = block.getBlockId();
        Page<Transaction> pageTransaction = transactionService.getPageBlockByBlockId(blockId, pageNum);

        List<JSONObject> TransactionJsons = pageTransaction.stream().map(Transaction -> {
            JSONObject TransactionJson = new JSONObject();
            TransactionJson.put("txid", Transaction.getTxid());
            TransactionJson.put("txhash", Transaction.getTxhash());
            TransactionJson.put("time", Transaction.getTime());
            TransactionJson.put("fees", Transaction.getFees());
            TransactionJson.put("totalOutput", Transaction.getTotalOutput());

            List<TransactionDetail> TransactionDetails = transactionDetailService.getTransactionByTransactionId(Transaction.getTransactionId());
            List<JSONObject> TransactionDetailJsons = TransactionDetails.stream().map(transactionDetail -> {
                JSONObject transactionDetailJson = new JSONObject();
                transactionDetailJson.put("address", transactionDetail.getAddress());
                transactionDetailJson.put("type", transactionDetail.getType());
                transactionDetailJson.put("amount", Math.abs(transactionDetail.getAmount()));
                return transactionDetailJson;
            }).collect(Collectors.toList());
            TransactionJson.put("TransactionDetailJsons", TransactionDetailJsons);
            return TransactionJson;
        }).collect(Collectors.toList());


        PageDTO<JSONObject> pageDTO = new PageDTO<>();
        pageDTO.setTotal(pageTransaction.getTotal());
        pageDTO.setPageSize(PageConfig.PAGE_SIZE);
        pageDTO.setCurrentPage(pageTransaction.getPageNum());
        pageDTO.setList(TransactionJsons);

        return pageDTO;

    }
    @GetMapping("/getTransactionByTxid")
    public JSONObject getTransactionByTxid(@RequestParam String txid){
        Transaction transaction = transactionService.getTransactionByTxid(txid);

        JSONObject TransactionJson = new JSONObject();
        TransactionJson.put("txid", transaction.getTxid());
        TransactionJson.put("txhash", transaction.getTxhash());
        TransactionJson.put("time", transaction.getTime());
        TransactionJson.put("fees", transaction.getFees());
        TransactionJson.put("totalOutput", transaction.getTotalOutput());

        List<TransactionDetail> TransactionDetails = transactionDetailService.getTransactionByTransactionId(transaction.getTransactionId());
        List<JSONObject> TransactionDetailJsons = TransactionDetails.stream().map(TransactionDetail -> {
            JSONObject TransactionDetailJson = new JSONObject();
            TransactionDetailJson.put("address", TransactionDetail.getAddress());
            TransactionDetailJson.put("type", TransactionDetail.getType());
            TransactionDetailJson.put("amount", Math.abs(TransactionDetail.getAmount()));
            return TransactionDetailJson;
        }).collect(Collectors.toList());
        TransactionJson.put("TransactionDetailJsons", TransactionDetailJsons);

        return TransactionJson;
    }


}

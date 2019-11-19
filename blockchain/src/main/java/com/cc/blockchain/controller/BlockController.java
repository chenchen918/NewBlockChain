package com.cc.blockchain.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.dao.BlockMapper;
import com.cc.blockchain.po.Block;
import com.cc.blockchain.po.Transaction;
import com.cc.blockchain.po.TransactionDetail;
import com.cc.blockchain.service.BlockService;
import com.cc.blockchain.service.TransactionDetailService;
import com.cc.blockchain.service.TransactionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Block")
@CrossOrigin
public class BlockController {
    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BlockService blockService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionDetailService transactionDetailService;


    @GetMapping("/getRecentblock")
    public List<JSONObject> getRecentblock(){
        List<Block> blocks=blockMapper.getRecentblock();
        List<JSONObject> BlockJsons = blocks.stream().map(block -> {
            JSONObject blockJson = new JSONObject();
            blockJson.put("height", block.getHeight());
            blockJson.put("blockhash", block.getBlockhash());
            blockJson.put("miner", block.getMiner());
            blockJson.put("time", block.getTime());
            blockJson.put("size", block.getSizeondisk());
            return blockJson;
        }).collect(Collectors.toList());
        return BlockJsons;
    }

    @GetMapping("/getWithPage")
    public PageInfo<JSONObject> getWithPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,3);
        List<JSONObject> recentList=blockMapper.getRecent();
        PageInfo<JSONObject> jsonObjectPageInfo = new PageInfo<>(recentList);
        return jsonObjectPageInfo;
    }

    @GetMapping("/getInfoByHash")
    public JSONObject getInfoByHash(@RequestParam String blockhash){
        JSONObject getInfoByHash=blockMapper.getInfoByHash(blockhash);
        return getInfoByHash;
    }

    @GetMapping("/getInfoByHeight")
    public JSONObject getInfoByHeight(@RequestParam Integer height){

        return null;
    }
    @GetMapping("/getBlockPage")
    public  PageInfo<Block> getBlockPage(@RequestParam(defaultValue = "1")Integer pageNum){
        PageInfo<Block> listBlock=blockService.getBlockPage(pageNum);
        return listBlock;
    }

    @GetMapping("/getBlockByHash")
    public JSONObject getBlockByHash(@RequestParam String blockhash){
        JSONObject BlockJson = new JSONObject();
        Block block=blockService.getBlockByhash(blockhash);
        BlockJson.put("blockhash",block.getBlockhash());
        BlockJson.put("height",block.getHeight());
        BlockJson.put("time",block.getTime());
        BlockJson.put("miner",block.getMiner());
        BlockJson.put("size",block.getSizeondisk());
        BlockJson.put("confirmations",null);
        BlockJson.put("txSize",block.getTxsize());
        BlockJson.put("difficulty",block.getDifficulty());
        BlockJson.put("merkleroot",block.getMerkleRoot());
        BlockJson.put("bits",block.getBits());
        BlockJson.put("version",block.getVersion());
        BlockJson.put("weight",block.getWeight());
        BlockJson.put("blockreward",block.getBlockReward());
        BlockJson.put("feereward",block.getFeeReward());
        BlockJson.put("transactionvolume",block.getTransactionVolume());
        BlockJson.put("nonce",block.getNonce());

        List<Transaction> transactions = transactionService.getBlockById(block.getBlockId());
        List<JSONObject> transactionJsons = transactions.stream().map(transaction -> {
            JSONObject transactionJson = new JSONObject();
            transactionJson.put("txid", transaction.getTxid());
            transactionJson.put("txhash", transaction.getTxhash());
            transactionJson.put("time", transaction.getTime());
            transactionJson.put("fees", transaction.getFees());
            transactionJson.put("totalOutput", transaction.getTotalOutput());

            List<TransactionDetail> Details = transactionDetailService.getTransactionById(transaction.getTransactionId());
            List<JSONObject> DetailJsons = Details.stream().map(Detail -> {
                JSONObject DetailJson = new JSONObject();
                DetailJson.put("address", Detail.getAddress());
                DetailJson.put("type", Detail.getType());
                DetailJson.put("amount", Math.abs(Detail.getAmount()));
                return DetailJson;
            }).collect(Collectors.toList());
            transactionJson.put("transactions", DetailJsons);
            return transactionJson;
        }).collect(Collectors.toList());
        BlockJson.put("transactions",transactionJsons);
        return BlockJson;

    }

    @GetMapping("/getBlockByHeight")
    public JSONObject getBlockByHeight(@RequestParam Integer height){
        JSONObject BlockJson = new JSONObject();
        Block block=blockService.getBlockByHeight(height);
        BlockJson.put("blockhash",block.getBlockhash());
        BlockJson.put("height",block.getHeight());
        BlockJson.put("time",block.getTime());
        BlockJson.put("miner",block.getMiner());
        BlockJson.put("size",block.getSizeondisk());
        BlockJson.put("confirmations",null);
        BlockJson.put("txSize",block.getTxsize());
        BlockJson.put("difficulty",block.getDifficulty());
        BlockJson.put("merkleroot",block.getMerkleRoot());
        BlockJson.put("bits",block.getBits());
        BlockJson.put("version",block.getVersion());
        BlockJson.put("weight",block.getWeight());
        BlockJson.put("blockreward",block.getBlockReward());
        BlockJson.put("feereward",block.getFeeReward());
        BlockJson.put("transactionvolume",block.getTransactionVolume());
        BlockJson.put("nonce",block.getNonce());
        return BlockJson;
    }
}

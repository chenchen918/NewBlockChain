package com.cc.blockchain.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.dao.BlockMapper;
import com.cc.blockchain.po.Block;
import com.cc.blockchain.service.BlockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Block")
public class BlockController {
    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BlockService blockService;


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
    public Object getBlockPage(@RequestParam(defaultValue = "1")Integer pageNum){
        PageInfo<Block> listBlock=blockService.getBlockPage(pageNum);
        return listBlock.getList();
    }
}

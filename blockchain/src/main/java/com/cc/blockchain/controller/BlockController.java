package com.cc.blockchain.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.dao.BlockMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Block")
public class BlockController {
    @Autowired
    private BlockMapper blockMapper;

    @GetMapping("/getRecent")
    public List<JSONObject> getRecent(){
        List<JSONObject> recentList=blockMapper.getRecent();
        return recentList;
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
}

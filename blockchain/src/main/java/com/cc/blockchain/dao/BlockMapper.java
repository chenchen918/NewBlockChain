package com.cc.blockchain.dao;

import com.alibaba.fastjson.JSONObject;
import com.cc.blockchain.po.Block;

import java.util.List;

public interface BlockMapper {
    int deleteByPrimaryKey(Integer blockId);

    int insert(Block record);

    int insertSelective(Block record);

    Block selectByPrimaryKey(Integer blockId);

    int updateByPrimaryKeySelective(Block record);

    int updateByPrimaryKey(Block record);

    List<JSONObject> getRecent();

    JSONObject getInfoByHash(String blockhash);

    List<Block> getRecentblock();

    List<Block> getBlockPage();
}
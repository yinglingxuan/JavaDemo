package com.example.client.dao;

import com.example.client.domain.FoodComment;
import com.example.client.responseFile.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodCommentMapper {
    //    商品评论的添加
    public int add(@Param("foodComment") FoodComment foodComment);
    //    商品评论的查询（可以根据是否有内容/全部/好评/差评）四种选择
    public List<FoodComment> query(@Param("foodComment") FoodComment foodComment, @Param("page") Page page);
    //     商家商品评论的总数
    public Long allCom(@Param("foodComment") FoodComment foodComment);
}

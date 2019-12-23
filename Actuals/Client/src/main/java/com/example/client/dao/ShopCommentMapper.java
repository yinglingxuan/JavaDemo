package com.example.client.dao;

import com.example.client.domain.ShopComment;
import com.example.client.responseFile.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopCommentMapper {
//    商家评论API
//
//    商家评论的添加
    public int add(@Param("shopComment") ShopComment shopComment);
//    商家评论的查询（可以根据是否有内容/全部/好评/差评）四种选择
    public List<ShopComment> query(@Param("shopComment") ShopComment shopComment, @Param("page") Page page);
//    商家评论的总数
    public Long allCom(@Param("shopComment") ShopComment shopComment);
}

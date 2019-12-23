package com.example.client.controller;

import com.alibaba.fastjson.JSON;
import com.example.client.dao.ShopMappers;
import com.example.client.dao.UserInfosMappers;
import com.example.client.domain.*;
import com.example.client.responseFile.BizPage;
import com.example.client.responseFile.BizResponse;
import com.example.client.responseFile.Page;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class ShopController {
    private final static double EARTH_RADIUS = 6378.137;//地球半径
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }
// 计算用户与商家相差多少公里
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2){
        double radLat1 = rad(lat1);//第一个点 纬度
        double radLat2 = rad(lat2);//第二个点 纬度
        double a = radLat1 - radLat2;//第一个点减去第二个点的纬度计算
        double b = rad(lng1) - rad(lng2);//第一个点减去第二个点的经度计算

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
// 根据圆心计算范围
    public static MapRange mapRange(double lon,double lat){
        int radius =10;//表示10公里内
        SpatialContext geo = SpatialContext.GEO;
        Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(
                geo.makePoint(lon, lat), radius * DistanceUtils.KM_TO_DEG, geo, null);
        System.out.println(rectangle.getMinX() + "-" + rectangle.getMaxX());// 经度范围
        System.out.println(rectangle.getMinY() + "-" + rectangle.getMaxY());// 纬度范围
        MapRange mapRange = new MapRange();
        mapRange.setMinLatitude(rectangle.getMinY());
        mapRange.setMaxLatitude(rectangle.getMaxY());
        mapRange.setMinLongitude(rectangle.getMinX());
        mapRange.setMaxLongitude(rectangle.getMaxX());
        System.err.println("范围"+mapRange);
        return mapRange;
    }
//根据距离进行排序
    public static void distanceOrder(List<ShopData> shopData,double lat,double lon){
        //修改距离
        for(ShopData data:shopData){
            double v = GetDistance(lat, lon, data.getShopLatitude(), data.getShopLongitude());//调用方法-》计算用户与商家相差多少公里
            data.setDistince(v);
        }
       //根据距离进行排序（集合的排序）
        Collections.sort(shopData, new Comparator<ShopData>(){
            public int compare(ShopData o1, ShopData o2) {
                //按照公里的进行升序排列
                if(o1.getDistince() > o2.getDistince()  ){
                    return 1;
                }
                if(o1.getDistince()  == o2.getDistince()){
                    return 0;
                }
                return -1;
            }
        });
    }
    //获取商家信息
	//合并

    @Autowired
    public ShopMappers  shopMappers;
    @Autowired
    public UserInfosMappers userInfosMappers;

    //通过uuid获取到店家的信息
    @GetMapping("/getShop")

    public BizResponse getShop(String uuid){
        ShopData shop = shopMappers.getShop(uuid);
//        shop.setInfos(JSON.parse(shop.getInfos().toString()));//商家图片
        shop.setInfos(JSON.parseArray(shop.getInfos().toString()));
        return BizResponse.of(shop);
    }

    //通过uuid获取到店家的菜系列表
    @GetMapping("/getShopList")
    public BizResponse getShopList(String uuid){
        List<GoodsType> shopList = shopMappers.getShopList(uuid);
        return BizResponse.of(shopList);
    }
   //店家的收藏
    @PostMapping("/addLike")
    @ResponseBody
    public BizResponse addLike(@RequestBody Receive receive){
        shopMappers.addLike(receive.getUuid(),receive.getOpenid());
        return BizResponse.success();
    }

     //查询当前用户是否收藏当前的店铺,如果是true表示有，falst表示没有
    @GetMapping("/getLike")
    @ResponseBody
    public BizResponse getLike(Receive receive){
        List<Receive> like = shopMappers.getLike(receive.getUuid(), receive.getOpenid());
        if (like.size()<1){
            return BizResponse.of(false);
        }
        return BizResponse.of(true);
    }

    //用户取消收藏
    @PostMapping("/deleteLike")
    @ResponseBody
    public BizResponse deleteLike(@RequestBody Receive receive){
        shopMappers.deleteLike(receive.getUuid(),receive.getOpenid());
        return BizResponse.success();
    }

    @GetMapping("/shop")
    public  BizResponse searchShop(@RequestParam("text")String text,@RequestParam("openid")String openid, BizPage bizPage){
        // 移动设备经纬度
        UserInfos userInfos = userInfosMappers.queryInfos(openid);
        if (userInfos!=null){
            double lon= userInfos.getUserLongitude();
            double lat = userInfos.getUserLatitude();
            MapRange mapRange = mapRange(lon,lat);//调用该方法进行计算范围
            List<ShopData> shopData =shopMappers.searchShop(text, mapRange,Page.of(bizPage.getNo(), bizPage.getSize()));//根据搜索的商家名称查询商家
            if(shopData!=null&&shopData.size()>0){
                distanceOrder(shopData,lat,lon);
                for(ShopData data:shopData)    {
                    System.err.println(data+"纬度="+data.getShopLatitude()+"经度="+data.getShopLongitude());
                    GetDistance(lat,lon,data.getShopLatitude(),data.getShopLongitude());
                }
                //筛选计算满足条件的商家总数以便于分页
                bizPage.setTotal(shopMappers.searchShopCount(text,mapRange));
                System.out.println(shopData+"\t"+text+"-----店铺-----"+bizPage);
                return BizResponse.of(shopData, bizPage);
            }else{
                List<ShopData> foodData = shopMappers.searchFood(text,mapRange, Page.of(bizPage.getNo(), bizPage.getSize()));//根据搜索的商品名称查询商家
                distanceOrder(foodData,lat,lon);
                for(ShopData data:shopData){
                    System.err.println(data+"纬度="+data.getShopLatitude()+"经度="+data.getShopLongitude());
                    GetDistance(lat,lon,data.getShopLatitude(),data.getShopLongitude());
                }
                //筛选计算满足条件的商家总数以便于分页
                bizPage.setTotal(shopMappers.searchFoodCount(text,mapRange));
                System.out.println(foodData+"\t"+text+"-----商品-----"+bizPage);
                return BizResponse.of(foodData, bizPage);
            }
        }else{
            return BizResponse.success();
        }


       /* List<ShopData> shopData =shopMappers.searchShop(text, Page.of(bizPage.getNo(), bizPage.getSize()));
//        List<ShopData> shopData =shopMappers.searchShop(, Page.of(bizPage.getNo(), bizPage.getSize()));
        if(shopData!=null&&shopData.size()>0){
            bizPage.setTotal(shopMappers.searchShopCount(text));
            System.out.println(text+"----------"+bizPage);
            return BizResponse.of(shopData, bizPage);
        }else{
            List<ShopData> foodData = shopMappers.searchFood(text, Page.of(bizPage.getNo(), bizPage.getSize()));
            bizPage.setTotal(shopMappers.searchFoodCount(text));
            System.out.println(text+"----------"+bizPage);
            return BizResponse.of(foodData, bizPage);
        }*/
    }



  /*  //商家后台试验 通过uuid获取到店家的信息
    @GetMapping("/getShopList2")
    public BizResponse getShopList2(String uuid){
        List<GoodsType> shopList = shopMappers.getShopType(uuid);
        System.out.println("进来"+uuid);
        return BizResponse.of(shopList, BizPage.of(shopMappers.getShopCountType(uuid),1,2));
    }*/
}

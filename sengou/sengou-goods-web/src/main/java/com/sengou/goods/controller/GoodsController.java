package com.sengou.goods.controller;

import com.sengou.goods.service.GoodsHtmlService;
import com.sengou.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("item")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转到商品详情页
     * @param model
     * @param skuId
     * @return
     */
    @GetMapping("{skuId}.html")
    public String toItemPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("skuId")Long skuId){
        // 加载所需的数据
        Map<String, Object> modelMap = this.goodsService.loadData(skuId);
       /* Iterator<Map.Entry<String,Object>> iterator = modelMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Object> maen = iterator.next();
            System.out.println(maen.getKey()+"      "+maen.getValue());
        }*/
        // 放入模型
        model.addAllAttributes(modelMap);
        // 页面静态化
        this.goodsHtmlService.asyncExcute(skuId);
        //手动渲染模板
        WebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        return thymeleafViewResolver.getTemplateEngine().process("item",context);
    }
}
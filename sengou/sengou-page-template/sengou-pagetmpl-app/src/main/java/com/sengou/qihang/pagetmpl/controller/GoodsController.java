package com.sengou.qihang.pagetmpl.controller;

import com.sengou.qihang.pagetmpl.service.GoodsHtmlService;
import com.sengou.qihang.pagetmpl.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String toItemPage(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long skuId){
        // 加载所需的数据
        Map<String, Object> modelMap = this.goodsService.loadData(skuId);
        // 放入模型
        model.addAllAttributes(modelMap);
        // 页面静态化
        this.goodsHtmlService.asyncExcute(skuId);
        //手动渲染模板
        WebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        return thymeleafViewResolver.getTemplateEngine().process("item",context);
    }

    @GetMapping(value = "/file/{skuId}.html",produces = "text/html")
    public String toResumePageFile(@PathVariable Long skuId) { ;
        // 页面静态化
        this.goodsHtmlService.asyncExcute(skuId);
        return "item/"+skuId+ ".html";
    }

    @DeleteMapping(value = "/{skuId}.html",produces = "text/html")
    public void deleteHtml(@PathVariable Long skuId) {
        this.goodsHtmlService.deleteHtml(skuId);
    }
}
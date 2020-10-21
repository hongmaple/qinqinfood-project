package com.sengou.qihang.pagetmpl.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author maple
 * @date 2020/8/15
 */
@FeignClient(value = "goods-web")
public interface ResumeHtmlClient {
    @GetMapping(value = "/item/file/{skuId}.html",produces = "text/html")
    String toResumePageFile(@PathVariable("skuId") Long skuId);

    @DeleteMapping(value = "/item/{skuId}.html",produces = "text/html")
    void deleteHtml(@PathVariable("skuId") Long skuId);
}

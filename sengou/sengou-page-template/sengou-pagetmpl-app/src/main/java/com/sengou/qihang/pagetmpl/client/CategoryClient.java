package com.sengou.qihang.pagetmpl.client;

import com.sengou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {

}
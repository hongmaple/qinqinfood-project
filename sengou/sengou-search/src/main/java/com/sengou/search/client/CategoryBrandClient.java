package com.sengou.search.client;

import com.sengou.item.api.CategoryBrandApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface CategoryBrandClient extends CategoryBrandApi {
}

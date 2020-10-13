package com.sengou.search.client;

import com.sengou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface GoodsClient  extends GoodsApi {
}
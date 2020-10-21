package com.sengou.qihang.pagetmpl.client;

import com.sengou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
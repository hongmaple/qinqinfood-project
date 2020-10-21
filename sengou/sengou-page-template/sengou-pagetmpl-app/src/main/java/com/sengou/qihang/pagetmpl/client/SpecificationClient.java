package com.sengou.qihang.pagetmpl.client;
import com.sengou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
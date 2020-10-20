package com.sengou.item.api;

import com.sengou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/brand")
public interface BrandApi {

    @GetMapping("/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);
}
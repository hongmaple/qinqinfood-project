package com.sengou.item.api;

import com.sengou.item.pojo.CategoryBrand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("CategoryBrand")
public interface CategoryBrandApi {
    @GetMapping("/list/{cid}")
    List<CategoryBrand> queryBrandIdByCid(@PathVariable("cid") Long cid);
}
package com.sengou.item.controller;

import com.sengou.item.pojo.CategoryBrand;
import com.sengou.item.service.CategoryBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("CategoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    @GetMapping("/list/{cid}")
    public ResponseEntity<List<CategoryBrand>> queryBrandIdByCid(@PathVariable("cid") Long cid) {
            return ResponseEntity.ok(categoryBrandService.queryBrandIdByCid(cid));
    }
}

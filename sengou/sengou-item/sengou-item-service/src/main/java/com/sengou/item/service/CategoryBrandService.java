package com.sengou.item.service;

import com.sengou.item.mapper.CategoryBrandMapper;
import com.sengou.item.pojo.CategoryBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    public List<CategoryBrand> queryBrandIdByCid(Long cid) {
        //1.初始化Example信息
        Example example = new Example(CategoryBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",cid);
        return categoryBrandMapper.selectByExample(example);
    }
}

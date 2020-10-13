package com.sengou.item.pojo;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name="tb_category_brand")
public class CategoryBrand {
    private Long categoryId;
    private Long brandId;
}

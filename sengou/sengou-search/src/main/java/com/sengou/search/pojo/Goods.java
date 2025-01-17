package com.sengou.search.pojo;

import lombok.Data;
import org.jboss.logging.Field;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class Goods {
    @Id
    private Long id; // spuId
    private String all; // 所有需要被搜索的信息，包含标题，分类，甚至品牌
    private String subTitle;// 卖点
    private Long brandId;// 品牌id
    private Long cid1;// 1级分类id
    private Long cid2;// 2级分类id
    private Long cid3;// 3级分类id
    private Date createTime;// 创建时间
    private List<Long> price;// 价格
    private String skus;// List<sku>信息的json结构
    private Map<String, Object> specs;// 可搜索的规格参数，key是参数名，值是参数值
}
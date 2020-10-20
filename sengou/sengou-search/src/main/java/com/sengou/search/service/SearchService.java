package com.sengou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sengou.common.pojo.PageResult;
import com.sengou.item.bo.SpuBo;
import com.sengou.item.pojo.*;
import com.sengou.search.client.*;
import com.sengou.search.pojo.Goods;
import com.sengou.search.pojo.SearchRequest;
import com.sengou.search.pojo.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private CategoryBrandClient categoryBrandClient;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Goods buildGoods(Spu spu) throws IOException {

        // 创建goods对象
        Goods goods = new Goods();

        // 查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        // 查询分类名称
        List<String> names = this.categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        // 查询spu下的所有sku
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spu.getId());
        List<Long> prices = new ArrayList<>();
        List<Map<String, Object>> skuMapList = new ArrayList<>();
        // 遍历skus，获取价格集合
        skus.forEach(sku ->{
            prices.add(sku.getPrice());
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getTitle());
            skuMap.put("price", sku.getPrice());
            skuMap.put("image", StringUtils.isNotBlank(sku.getImages()) ? StringUtils.split(sku.getImages(), ",")[0] : "");
            skuMapList.add(skuMap);
        });

        // 查询出所有的搜索规格参数
        List<SpecParam> params = this.specificationClient.queryParams(null, spu.getCid3(), null, true);
        // 查询spuDetail。获取规格参数值
        SpuDetail spuDetail = this.goodsClient.querySpuDetailById(spu.getId());
        // 获取通用的规格参数，反json序列化成map
        Map<Long, Object> genericSpecMap = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>() {
        });
        // 获取特殊的规格参数
        Map<Long, List<Object>> specialSpecMap = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<Object>>>() {
        });
        // 定义map接收{规格参数名，规格参数值}
        Map<String, Object> paramMap = new HashMap<>();
        params.forEach(param -> {
            // 判断是否通用规格参数
            if (param.getGeneric()) {
                // 获取通用规格参数值
                String value = genericSpecMap.get(param.getId()).toString();
                // 判断是否是数值类型
                if (param.getNumeric()){
                    // 如果是数值的话，判断该数值落在那个区间
                    value = chooseSegment(value, param);
                }
                // 把参数名和值放入结果集中
                paramMap.put(param.getName(), value);
            } else {
                paramMap.put(param.getName(), specialSpecMap.get(param.getId()));
            }
        });

        // 设置参数
        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setBrandId(spu.getBrandId());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        goods.setAll(spu.getTitle() + brand.getName() + StringUtils.join(names, " "));
        goods.setPrice(prices);
        goods.setSkus(MAPPER.writeValueAsString(skuMapList));
        goods.setSpecs(paramMap);

        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }


    public SearchResult search(SearchRequest request) {

        List<SpuBo> spuBos = new ArrayList<>();
        // 判断查询条件
        if (StringUtils.isBlank(request.getKey())) {
            // 返回默认结果集
            return null;
        }else {
            PageResult<SpuBo> spuBoPageResult = this.goodsClient.querySpuBoByPage(request.getKey(),true,request.getPage(), request.getSize()).getBody();
            spuBos = spuBoPageResult.getItems();
        }
        List<Brand> brands = new ArrayList<>();
        List<Long> cids = new ArrayList<>();
        List<Goods> goodsList = new ArrayList<>();
        spuBos.forEach(b ->{
            ModelMapper modelMapper = new ModelMapper();
            Spu spu = modelMapper.map(b,Spu.class);
            brands.add(this.brandClient.queryBrandById(spu.getBrandId()));
            try {
                Goods goods = this.buildGoods(spu);
                goodsList.add(goods);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cids.add(spu.getCid3());
        });
        for (int i = 0; i<cids.size();i++){
            List<CategoryBrand> categoryBrands = this.categoryBrandClient.queryBrandIdByCid(cids.get(i));
            categoryBrands.forEach(categoryBrand -> {
                //brands.add(this.brandClient.queryBrandById(categoryBrand.getBrandId()));
            });
        }
        List<Brand> brandList = brands.stream().distinct().collect(Collectors.toList());
        List<Map<String, Object>> categories = this.getCategoryAggResult(cids);

        // 获取分页参数
        Integer page = request.getPage();
        Integer size = request.getSize();

        //排序
        String sortBy = request.getSortBy();
        Boolean descending = request.getDescending();
        if (StringUtils.isNotBlank(sortBy)){
            //如果不为空则进行排序
        }

        // 判断分类聚合的结果集大小，等于1则聚合
        List<Map<String, Object>> specs = null;
        if (categories.size() == 1) {
            specs = new ArrayList<>();
        }
        String categoryAggName = "categories";
        String brandAggName = "brands";

        // 封装成需要的返回结果集
        return new SearchResult(goodsList, (long)goodsList.size(), (long)goodsList.size()/size, categories, brandList, specs);
    }
    /**
     * 解析分类
     * @return
     */
    private List<Map<String,Object>> getCategoryAggResult(List<Long> cids) {
        // 定义一个品牌集合，搜集所有的品牌对象
        List<Map<String, Object>> categories = new ArrayList<>();
        List<String> names = this.categoryClient.queryNameByIds(cids);
        for (int i = 0; i < names.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cids.get(i));
            map.put("name", names.get(i));
            categories.add(map);
        }
        return categories;
    }
}
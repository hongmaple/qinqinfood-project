package com.sengou.item.api;

import com.sengou.common.pojo.PageResult;
import com.sengou.item.bo.SpuBo;
import com.sengou.item.pojo.Sku;
import com.sengou.item.pojo.Spu;
import com.sengou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsApi {

    /**
     * 分页查询商品
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/spu/page")
    ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows
    );

    /**
     * 根据spu商品id查询详情
     * @param id
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    SpuDetail querySpuDetailById(@PathVariable("id") Long id);

    /**
     * 根据spu的id查询sku
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    List<Sku> querySkusBySpuId(@RequestParam("id") Long spuId);

    /**
     * 根据spu的id查询spu
     * @param id
     * @return
     */
    @GetMapping("/spu/{id}")
    Spu querySpuById(@PathVariable("id") Long id);

    /**
     * 根据skuid查询sku
     * @param id
     * @return
     */
    @GetMapping("/sku/{id}")
    Sku querySkuById(@PathVariable("id") Long id);
}
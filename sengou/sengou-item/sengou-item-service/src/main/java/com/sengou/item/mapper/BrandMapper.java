package com.sengou.item.mapper;

import com.sengou.item.pojo.Brand;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    /**
     * 新增商品分类和中间表数据
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid") Long cid,@Param("bid") Long bid);

    /**
     * 修改品牌，维护中间表
     * @param cids
     * @param bid
     * @return
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id=#{bid} and category_id NOT IN(#{cids})")
    int deleteBrandAndCategoryByCidNot(@Param("cids") String cids,@Param("bid") Long bid);

    /**
     * 根据品牌id删除中间表
     * @param bid
     * @return
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id=#{bid}")
    int deleteBrandAndCategory(@Param("bid") Long bid);

    /**
     * 根据分类id查询该分类下品牌列表
     * @param cid
     * @return
     */
    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);
}

package com.sengou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sengou.common.pojo.PageResult;
import com.sengou.item.mapper.BrandMapper;
import com.sengou.item.mapper.CategoryMapper;
import com.sengou.item.pojo.Brand;
import com.sengou.item.pojo.Category;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据查询条件分页并排序查询品牌信息
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
         //1.初始化Example信息
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //添加条件，根据name模糊查询或根据首字母查询
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }

        //添加分页条件
        PageHelper.startPage(page,rows);
        //添加排序条件
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy +" "+(desc ? "desc":"asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);
        //包装成pageinfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand,List<Long> cids){

        //先新增brand
        this.brandMapper.insertSelective(brand);

        //在新增中间表
        cids.forEach(cid ->{
            this.brandMapper.insertBrandAndCategory(cid,brand.getId());
        });
    }
    /**
     * 修改品牌
     *
     * @param brand
     * @param cids
     */
    @Transactional
    public void updateBrand(Brand brand,List<Long> cids){
        //先修改brand
        this.brandMapper.updateByPrimaryKeySelective(brand);

        //再删除中间表多余部分
        String cidsString = StringUtils.join(cids,",");
        this.brandMapper.deleteBrandAndCategoryByCidNot(cidsString,brand.getId());
        //再新增中间表缺少的部分
        List<Category> categories = categoryMapper.queryByBrandId(brand.getId());
        cids.forEach(cid ->{
            categories.forEach(category ->{
                if (cid!=category.getId()){
                    this.brandMapper.insertBrandAndCategory(cid,brand.getId());
                }
            });
        });
    }

    /**
     * 删除品牌
     * @param bid
     */
    @Transactional
    public void deleteBrand(Long bid){
        //先删除品牌表
        this.brandMapper.deleteByPrimaryKey(bid);
        this.brandMapper.deleteBrandAndCategory(bid);
    }

    /**
     * 根据分类id查询该分类下品牌列表
     * @param cid
     * @return
     */
    public List<Brand> queryBrandsByCid(Long cid){
          return this.brandMapper.selectBrandByCid(cid);
    }

    /**
     * 根据品牌id查询品牌信息
     * @param id
     * @return
     */
    public Brand queryBrandById(Long id){
        return this.brandMapper.selectByPrimaryKey(id);
    }

}

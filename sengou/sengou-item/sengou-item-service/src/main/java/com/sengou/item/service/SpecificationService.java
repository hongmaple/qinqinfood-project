package com.sengou.item.service;

import com.sengou.item.mapper.SpecGroupMapper;
import com.sengou.item.mapper.SpecParamMapper;
import com.sengou.item.pojo.SpecGroup;
import com.sengou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;
    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsByCid(Long cid){
            SpecGroup specGroup = new SpecGroup();
            specGroup.setCid(cid);
            return specGroupMapper.select(specGroup);
    }

    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching){
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.specParamMapper.select(record);
    }

    public List<SpecGroup> querySpecsByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> groups = this.queryGroupsByCid(cid);
        groups.forEach(g -> {
            // 查询组内参数
            g.setParams(this.queryParams(g.getId(), null, null, null));
        });
        return groups;
    }

    public Long addSpecGroup(Long cid , String name){
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        specGroup.setName(name);
        return Integer.toUnsignedLong(specGroupMapper.insert(specGroup));
    }

    public Boolean updateSpecGroup(Long id , String name){
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        specGroup.setName(name);
        return specGroupMapper.updateByPrimaryKeySelective(specGroup)>0;
    }

    public Boolean deleteSpecGroup(Long id){
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        return specGroupMapper.deleteByPrimaryKey(specGroup) > 0;
    }

    public Boolean addSpecParam(SpecParam specParam){
      return specParamMapper.insert(specParam)>0;
    }

    public Boolean updateSpecParam(SpecParam specParam){
        return specParamMapper.updateByPrimaryKeySelective(specParam)>0;
    }

    public Boolean deleteSpecParam(Long id){
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
        return specParamMapper.deleteByPrimaryKey(specParam)>0;
    }
}

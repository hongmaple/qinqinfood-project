package com.sengou.item.controller;

import com.sengou.item.pojo.SpecGroup;
import com.sengou.item.pojo.SpecParam;
import com.sengou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable Long cid){
        List<SpecGroup> groups = this.specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
    ) {
        List<SpecParam>  params = this.specificationService.queryParams(gid, cid, generic, searching);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable Long cid){
        List<SpecGroup> list = this.specificationService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/group")
    public ResponseEntity<Long>addSpecGroup(@RequestBody SpecGroup specGroup){
        return ResponseEntity.ok(this.specificationService.addSpecGroup(specGroup.getCid(),specGroup.getName()));
    }

    @PutMapping("/group")
    public ResponseEntity<Boolean>updateSpecGroup(@RequestBody SpecGroup specGroup){
        return ResponseEntity.ok(this.specificationService.updateSpecGroup(specGroup.getId(),specGroup.getName()));
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Boolean>deleteSpecGroup(@PathVariable Long id){
        return ResponseEntity.ok(this.specificationService.deleteSpecGroup(id));
    }

    @PostMapping("/param")
    public ResponseEntity<Boolean> addSpecParam(@RequestBody SpecParam specParam){
        return ResponseEntity.ok(this.specificationService.addSpecParam(specParam));
    }

    @PutMapping("/param")
    public ResponseEntity<Boolean> updateSpecParam(@RequestBody SpecParam specParam){
        return ResponseEntity.ok(this.specificationService.updateSpecParam(specParam));
    }

    @DeleteMapping("/param/{id}")
    public ResponseEntity<Boolean>deleteSpecParam(@PathVariable Long id){
        return ResponseEntity.ok(this.specificationService.deleteSpecParam(id));
    }




}
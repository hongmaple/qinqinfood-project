package com.sengou.item.api;

import com.sengou.item.pojo.SpecGroup;
import com.sengou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("spec")
public interface SpecificationApi {
        @GetMapping("groups/{cid}")
         ResponseEntity<List<SpecGroup>> querySpecGroups(@PathVariable("cid") Long cid);

        @GetMapping("params")
         List<SpecParam> queryParams(
                @RequestParam(value = "gid", required = false) Long gid,
                @RequestParam(value = "cid", required = false) Long cid,
                @RequestParam(value = "generic", required = false) Boolean generic,
                @RequestParam(value = "searching", required = false) Boolean searching
        );

        // 查询规格参数组，及组内参数
        @GetMapping("{cid}")
        List<SpecGroup> querySpecsByCid(@PathVariable("cid") Long cid);
}
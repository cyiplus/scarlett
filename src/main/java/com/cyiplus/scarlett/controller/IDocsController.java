package com.cyiplus.scarlett.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyiplus.scarlett.common.lang.Result;
import com.cyiplus.scarlett.entity.IDocs;
import com.cyiplus.scarlett.service.IDocsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-28
 */
@RestController
public class IDocsController {

    @Autowired
    IDocsService iDocsService;

    @GetMapping("api/setting")
    public Result setting(@RequestParam("category") String category) {

        IDocs iDocs = iDocsService.getOne(new QueryWrapper<IDocs>().eq("category", category));
        return Result.succ(iDocs);
    }

    @GetMapping("sys/setting")
    public Result getData(@RequestParam(value = "category", required = false) String category) {
        IDocs iDocs = new IDocs();
        if (category != null) {
            iDocs = iDocsService.getOne(new QueryWrapper<IDocs>().eq("category", category));
            return Result.succ(iDocs);
        } else {
            return Result.fail("请输入类型");
        }
        
    }

    @PostMapping("sys/setting")
    public Result save(@RequestBody IDocs iDocs) {
        iDocsService.saveOrUpdate(iDocs);
        return Result.succ();
    }

}

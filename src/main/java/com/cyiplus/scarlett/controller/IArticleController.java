package com.cyiplus.scarlett.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyiplus.scarlett.common.lang.Result;
import com.cyiplus.scarlett.entity.IArticle;
import com.cyiplus.scarlett.entity.IArticleBody;
import com.cyiplus.scarlett.entity.IComments;
import com.cyiplus.scarlett.entity.SysRecord;
import com.cyiplus.scarlett.entity.SysUser;
import com.cyiplus.scarlett.service.IArticleBodyService;
import com.cyiplus.scarlett.service.IArticleService;
import com.cyiplus.scarlett.service.ICommentsService;
import com.cyiplus.scarlett.service.SysRecordService;
import com.cyiplus.scarlett.service.SysUserService;
import com.cyiplus.scarlett.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.map.MapUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-22123
 */
@RestController
public class IArticleController {

    @Autowired
    IArticleService iArticleService;

    @Autowired
    IArticleBodyService iArticleBodyService;

    @Autowired
    ICommentsService iCommentsService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRecordService sysRecordService;

@GetMapping("/api/articles")
public Result list(@RequestParam(value = "category", required = false) String category,@RequestParam(value = "key" ,required = false) String key) {

    List<IArticle> iArticles = iArticleService.list(
            new QueryWrapper<IArticle>()
            .eq("publish", true)
            .like(category!=null, "category", category)
            .like(key!=null, "keyword", key)
            .le("publish_time",LocalDateTime.now())
            .orderByDesc("create_time")
        );
    return Result.succ(iArticles);
}

@GetMapping("/api/article")
public Result singleview(@RequestParam("id") String id,HttpServletRequest request) {

    IArticle iArticle = iArticleService.getById(id);
    IArticleBody iArticleBody = iArticleBodyService.getOne(new QueryWrapper<IArticleBody>().eq("article_id", id));
    List<IComments> iComments = iCommentsService.list(new QueryWrapper<IComments>().eq("article_id", id));
    SysUser sysUser = sysUserService.getById(iArticle.getUserId());
    sysUser.setPassword(null);

    // view field update
    iArticle.setViews(iArticle.getViews() + 1);
    iArticleService.updateById(iArticle);

    // sys Article record - View
    SysRecord sysRecord = new SysRecord();
    sysRecord.setCategory("Article");
    sysRecord.setKeyword("View");
    sysRecord.setArticleId(id);
    sysRecord.setIp(IPUtils.getIpAddr(request));
    sysRecord.setCreateTime(LocalDateTime.now());
    sysRecordService.Record(sysRecord);

    return Result.succ(
            MapUtil.builder()
                .put("article", iArticle)
                .put("comment",iComments)
                .put("body", iArticleBody)
                .put("author", sysUser)
                .build()
        );
}

@GetMapping("sys/articles")
public Result allList(@RequestParam(value = "category", required = false) String category,@RequestParam(value = "key" ,required = false) String key) {
    List<IArticle> iArticles = null;
    if (key != null) {
        iArticles = iArticleService.list(
            new QueryWrapper<IArticle>().like("keyword", key).orderByDesc("create_time")
        );
    } else {
        iArticles = iArticleService.list();
    }
    
    // iArticles = iArticleService.list();
    return Result.succ(iArticles);
}

@GetMapping("sys/article")
public Result edit(@RequestParam(value = "id", required=false) String id) {
    if (id != null) {
        IArticle iArticle = iArticleService.getById(id);
        IArticleBody iArticleBody = iArticleBodyService.getOne(new QueryWrapper<IArticleBody>().eq("article_id", id));
        iArticle.setBodyId(iArticleBody.getId());
        iArticle.setBody(iArticleBody.getContent());
        return Result.succ(iArticle);
    } else {
        return Result.fail("没有找到数据");
    }

}

@PostMapping("sys/article")
public Result save(@RequestBody IArticle iArticle,Principal principal,HttpServletRequest request) {

    iArticle.setUserId(sysUserService.getUserByUsername(principal.getName()).getId());
    boolean save = iArticleService.saveOrUpdate(iArticle);
    IArticleBody iArticleBody = new IArticleBody();

    if (iArticle.getBodyId() != null) {
        iArticleBody.setId(iArticle.getBodyId());
    }
    iArticleBody.setArticleId(iArticle.getId());
    iArticleBody.setContent(iArticle.getBody());

    boolean savebody = iArticleBodyService.saveOrUpdate(iArticleBody);

    if (save && savebody) {
            // sys Article record - View
        SysRecord sysRecord = new SysRecord();
        sysRecord.setCategory("Article");
        sysRecord.setKeyword("New");
        sysRecord.setArticleId(iArticle.getId());
        sysRecord.setIp(IPUtils.getIpAddr(request));
        sysRecord.setCreateTime(LocalDateTime.now());
        sysRecordService.Record(sysRecord);
        return Result.succ(iArticle);
    } else {
        SysRecord sysRecord = new SysRecord();
        sysRecord.setCategory("Article");
        sysRecord.setKeyword("New_Fail");
        sysRecord.setArticleId(iArticle.getId());
        sysRecord.setIp(IPUtils.getIpAddr(request));
        sysRecord.setCreateTime(LocalDateTime.now());
        sysRecordService.Record(sysRecord);
        return Result.fail("失败");
    }
}


@PostMapping("sys/article/del")
public Result del(@RequestBody IArticle iArticle) {
    if (iArticle.getId() != null) {
        
    }
    boolean remove =  iArticleService.removeById(iArticle.getId());
    boolean removebody =  iArticleBodyService.removeById(iArticle.getBodyId());
    if (remove &&  removebody){
        return Result.succ("删除成功！");
    } else {
        return Result.fail("删除失败！");
    }
}

@PostMapping("sys/article/publish")
public Result publish(@RequestBody IArticle iArticle,HttpServletRequest request) {

    if (iArticle.getPublish()) {
        iArticle.setPublishTime(LocalDateTime.now());
    }else{
        iArticle.setPublishTime(null);
    }
    
    boolean save = iArticleService.updateById(iArticle);

    if (save) {
        // sys Article record - View
        SysRecord sysRecord = new SysRecord();
        sysRecord.setCategory("Article");
        sysRecord.setKeyword("Publish");
        sysRecord.setArticleId(iArticle.getId());
        sysRecord.setIp(IPUtils.getIpAddr(request));
        sysRecord.setCreateTime(LocalDateTime.now());
        sysRecordService.Record(sysRecord);
        return Result.succ("成功发布文章！");
    } else {
        SysRecord sysRecord = new SysRecord();
        sysRecord.setCategory("Article");
        sysRecord.setKeyword("Publish_Fail");
        sysRecord.setArticleId(iArticle.getId());
        sysRecord.setIp(IPUtils.getIpAddr(request));
        sysRecord.setCreateTime(LocalDateTime.now());
        sysRecordService.Record(sysRecord);
        return Result.fail("发布失败！");
    }
    
}
}

package com.cyiplus.scarlett.controller;


import java.util.List;

import com.cyiplus.scarlett.common.lang.Result;
import com.cyiplus.scarlett.entity.IArticleDraft;
import com.cyiplus.scarlett.service.IArticleDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-27
 */
@RestController
public class IArticleDraftController {

    @Autowired
    IArticleDraftService iArticleDraftService;

@GetMapping("sys/draft")
public Result alldraft() {
    List<IArticleDraft> iArticleDrafts = iArticleDraftService.list();
    return Result.succ(iArticleDrafts);
    
}


}

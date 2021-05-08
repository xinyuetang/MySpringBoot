package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

/**
 * Created by Xinyue.Tang at 2021-05-03 02:27:52
 */
@Api(tags = "首页")
@RestController
public class IndexController extends BaseController {

    @ApiOperation(value = "默认", notes = "默认Note")
    @GetMapping("/")
    public JsonResult<?> index() {
        Object greeting = Collections.singletonMap("datetime", DateExUtils.formatDatetimeMilli(new Date()));
        return JsonResult.buildSuccess(greeting);
    }
}

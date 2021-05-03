package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

/**
 * Created by Xinyue.Tang at 2021-05-03 02:27:52
 */
@RestController
public class IndexController extends BaseController {

    @RequestMapping("/")
    public JsonResult<?> index() {
        Object greeting = Collections.singletonMap("datetime", DateExUtils.formatDatetimeMilli(new Date()));
        return JsonResult.buildSuccess(greeting);
    }
}

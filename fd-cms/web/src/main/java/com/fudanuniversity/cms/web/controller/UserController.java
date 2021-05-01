package com.fudanuniversity.cms.web.controller;

import com.fudanuniversity.cms.combi.service.CmsUserAccountService;
import com.fudanuniversity.cms.combi.service.CmsUserService;
import com.fudanuniversity.cms.combi.vo.CmsUserAccountVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.inner.entity.CmsUser;
import com.fudanuniversity.cms.inner.entity.deprecated.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsUserAccountService cmsUserAccountService;

    //添加新用户，stuID是唯一的标识
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public JsonResult<?> addNewUser(@RequestBody User user) {
//        //用户权限验证
//        String token  = UserRequest.getToken();
//        Integer roleId = TokenUtil.getRoleId(token);
//        if(roleId!= Role.Administrator.ordinal()) return "";
//
//        //  means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//        JSONObject json=new JSONObject();
//        //用户已经存在
//        if(userRepository.findByStudentID(user.getStudentID())!=null) {json.put("result","fail");return json.toString();}
//        //密码初始化为学号
//        try {
//            String encryptedPassword = Md5.EncoderByMd5(user.getStudentID());
//            user.setPassword(encryptedPassword);
//        } catch (NoSuchAlgorithmException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        userRepository.save(user);
//        json.put("result","success");
//        return json.toString();
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/login")
    public JsonResult<?> login(@Valid CmsUserAccountVo userAccountVo) {
        cmsUserAccountService.loginAccount(userAccountVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/delete")
    public JsonResult<?> delete(@RequestParam Integer id) {
//        //用户权限验证
//        String token  = UserRequest.getToken();
//        Integer roleId = TokenUtil.getRoleId(token);
//        if(roleId!= Role.Administrator.ordinal()) return "";
//
//        JSONObject json=new JSONObject();
//        if(userRepository.findById(id).isPresent()) {
//            User user = userRepository.findById(id).get();
//            userRepository.delete(user);
//            json.put("result", "success");
//        }
//        else json.put("result", "fail");
//        return json.toString();
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/all")
    public JsonResult<?> getAllUsers(Paging paging) {
        PagingResult<CmsUser> allUsers = cmsUserService.getAllUsers(paging);
        return JsonResult.buildSuccess(allUsers);
    }

}
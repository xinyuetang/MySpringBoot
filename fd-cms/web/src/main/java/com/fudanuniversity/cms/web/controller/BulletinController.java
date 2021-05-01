package com.fudanuniversity.cms.web.controller;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.inner.entity.deprecated.Bulletin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/bulletin")
public class BulletinController {
//    @Autowired
//    BulletinRepository bulletinRepository;
//    @Autowired
//    BulletinUserRepository bulletinUserRepository;

    @PostMapping(path = "/add")
    public JsonResult<?> addNewBulletin (@RequestBody Bulletin bulletin) {
//        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
//        String today  = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
//        bulletin.setDate(today);
//        bulletinRepository.save(bulletin);
//        return "New Bulletin";
        return JsonResult.buildSuccess();
    }



    @GetMapping(path = "/newBulletinNumber")
    public JsonResult<?> getNewBulletinNumber () {
//        //step1, 获得发起请求的用户Id
//        String token  = UserRequest.getToken();
//        Integer userId = TokenUtil.getUserId(token);
//        Iterable<Bulletin> bulletins = bulletinRepository.findAll ();
//
//        JSONObject result=new JSONObject();
//        Integer counter = 0;
//        for (Bulletin b:bulletins) {
//            //查询用户已读或未读
//            if (bulletinUserRepository.findBulletinUserByBulletinIdAndUserId(b.getId(),userId)==null)
//                counter++;
//        }
//
//        result.put("number",counter);//所有未读通知数目
//        return result.toString();
        return JsonResult.buildSuccess();
    }

    //获取所有通知，并附加isRead属性
    @GetMapping(path="/all")
    public JsonResult<?> getAllBulletin () {
//        //step1, 获得发起请求的用户Id
//        String token  = UserRequest.getToken();
//        Integer userId = TokenUtil.getUserId(token);
//        Iterable<Bulletin> bulletins = bulletinRepository.findByOrderByDateDesc();
//
//        JSONArray bulletinsArray = new JSONArray();
//        for (Bulletin b:bulletins) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("title",b.getTitle());
//            jsonObject.put("date",b.getDate());
//            jsonObject.put("content",b.getContent());
//            jsonObject.put("id",b.getId());
//            //查询用户已读或未读
//            if (bulletinUserRepository.findBulletinUserByBulletinIdAndUserId(b.getId(),userId)!=null)
//                jsonObject.put("isRead",1);//已读
//            else {
//                jsonObject.put("isRead",0);//未读
//              }
//            bulletinsArray.put(jsonObject);
//        }
//
//        return bulletinsArray.toString();
        return JsonResult.buildSuccess();

    }
    //获取所有通知，并附加isRead属性
    @GetMapping(path="/markAsRead")
    public JsonResult<?> markAsRead (@RequestParam Integer bulletinId) {
//        // 获得发起请求的用户Id
//        String token  = UserRequest.getToken();
//        Integer userId = TokenUtil.getUserId(token);
//
//        BulletinUser bulletinUser = new BulletinUser();
//        bulletinUser.setBulletinId(bulletinId);
//        bulletinUser.setUserId(userId);
//        bulletinUser.setIsRead(1);
//        bulletinUserRepository.save(bulletinUser);
//
//        JSONObject rst = new JSONObject();
//        rst.put("result","success");
//        return rst.toString();
        return JsonResult.buildSuccess();
    }

}

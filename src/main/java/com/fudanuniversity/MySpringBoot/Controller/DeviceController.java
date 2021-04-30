package com.fudanuniversity.MySpringBoot.Controller;

import com.fudanuniversity.MySpringBoot.Entity.Device;
import com.fudanuniversity.MySpringBoot.Entity.User;
import com.fudanuniversity.MySpringBoot.Repository.DeviceRepository;
import com.fudanuniversity.MySpringBoot.Repository.UserRepository;
import com.fudanuniversity.MySpringBoot.utils.TokenUtil;
import com.fudanuniversity.MySpringBoot.utils.UserRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller // This means that this class is a Controller
@RequestMapping(path="/device")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addDevice(@RequestBody Device device){
        //获得发起请求的用户Id
        String token  = UserRequest.getToken();
        Integer userId = TokenUtil.getUserId(token);
        device.setUserId(userId);

        //获取当前日期
        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        String today  = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        device.setDate(today);

        deviceRepository.save(device);
        JSONObject rst = new JSONObject();
        rst.put("result","success");
        return  rst.toString();
    }
    @GetMapping(path = "delete")
    public @ResponseBody String deleteDevice(@RequestParam Integer id){
        deviceRepository.deleteById(id);
        JSONObject rst = new JSONObject();
        rst.put("result","success");
        return  rst.toString();
    }

    @GetMapping(path = "all")
    public @ResponseBody String getAllDevice(){
        JSONObject result = new JSONObject();

        Iterable<Device> devices_type0 = deviceRepository.findAllByType(0);
        Iterable<Device> devices_type1 = deviceRepository.findAllByType(1);
        Iterable<Device> devices_type2 = deviceRepository.findAllByType(2);
        Iterable<Device> devices_type3 = deviceRepository.findAllByType(3);
        JSONArray devices_type0_array = new JSONArray();
        JSONArray devices_type1_array = new JSONArray();
        JSONArray devices_type2_array = new JSONArray();
        JSONArray devices_type3_array = new JSONArray();

        for(Device d: devices_type0){
            JSONObject deviceojb = new JSONObject();
            User user = userRepository.findById(d.getUserId()).get();
            deviceojb.put("name",user.getName());//使用者姓名
            deviceojb.put("studentId",user.getStudentID());//使用者学号
            deviceojb.put("date",d.getDate());
            deviceojb.put("deviceVersion",d.getDeviceVersion());// 设备型号
            deviceojb.put("id",d.getId());
            deviceojb.put("userId",user.getId());
            devices_type0_array.put(deviceojb);
        }
        for(Device d: devices_type1){
            JSONObject deviceojb = new JSONObject();
            User user = userRepository.findById(d.getUserId()).get();
            deviceojb.put("name",user.getName());//使用者姓名
            deviceojb.put("studentId",user.getStudentID());//使用者学号
            deviceojb.put("date",d.getDate());
            deviceojb.put("deviceVersion",d.getDeviceVersion());// 设备型号
            deviceojb.put("id",d.getId());
            deviceojb.put("userId",user.getId());
            devices_type1_array.put(deviceojb);
        }
        for(Device d: devices_type2){
            JSONObject deviceojb = new JSONObject();
            User user = userRepository.findById(d.getUserId()).get();
            deviceojb.put("name",user.getName());//使用者姓名
            deviceojb.put("studentId",user.getStudentID());//使用者学号
            deviceojb.put("date",d.getDate());
            deviceojb.put("personInCharge",d.getPersonInCharge());// 负责人姓名
            deviceojb.put("serverName",d.getServerName());// 服务器名字
            deviceojb.put("memory",d.getMemory());// 使用内存
            deviceojb.put("id",d.getId());
            deviceojb.put("userId",user.getId());
            devices_type2_array.put(deviceojb);
        }
        for(Device d: devices_type3){
            JSONObject deviceojb = new JSONObject();
            User user = userRepository.findById(d.getUserId()).get();
            deviceojb.put("name",user.getName());//使用者姓名
            deviceojb.put("studentId",user.getStudentID());//使用者学号
            deviceojb.put("date",d.getDate());
            deviceojb.put("deviceType",d.getDeviceType());// 设备类型
            deviceojb.put("deviceModel",d.getDeviceModel());// 设备型号
            deviceojb.put("id",d.getId());
            deviceojb.put("userId",user.getId());
            devices_type3_array.put(deviceojb);
        }


        result.put("type0",devices_type0_array);
        result.put("type1",devices_type1_array);
        result.put("type2",devices_type2_array);
        result.put("type3",devices_type3_array);
        return result.toString();

    }

}

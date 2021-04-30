package com.fudanuniversity.MySpringBoot.Controller;

import com.fudanuniversity.MySpringBoot.Entity.Seminar;
import com.fudanuniversity.MySpringBoot.Entity.User;
import com.fudanuniversity.MySpringBoot.Repository.SeminarRepository;
import com.fudanuniversity.MySpringBoot.Repository.UserRepository;
import com.fudanuniversity.MySpringBoot.utils.Md5;
import com.fudanuniversity.MySpringBoot.utils.Role;
import com.fudanuniversity.MySpringBoot.utils.TokenUtil;
import com.fudanuniversity.MySpringBoot.utils.UserRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller // This means that this class is a Controller
@RequestMapping(path="/seminar")
public class SeminarController {
    @Autowired
    private SeminarRepository seminarRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping(path="/add")
    public @ResponseBody String addNewSeminar (@RequestBody Seminar seminar) {
        //用户权限验证
        String token  = UserRequest.getToken();
        Integer roleId = TokenUtil.getRoleId(token);
        if(roleId!= Role.Administrator.ordinal() && roleId!=Role.Seminar.ordinal()) return "";

        JSONObject json=new JSONObject();

        seminarRepository.save(seminar);
        json.put("result","success");
        return json.toString();
    }

    @GetMapping(path="/addlink")
    public @ResponseBody String addSeminarLink (@RequestParam String link, @RequestParam Integer id) {
        JSONObject json=new JSONObject();
        if(seminarRepository.findById(id).isPresent()) {
            Seminar seminar = seminarRepository.findById(id).get();
            seminar.setLink(link);
            seminarRepository.save(seminar);
            json.put("result", "success");
        }
        else json.put("result","fail");
        return json.toString();
    }
    @GetMapping(path = "/all")
    public @ResponseBody String allSeminar  (){
        JSONArray result = new JSONArray();
        Iterable<Seminar> r = seminarRepository.findAll();
        for (Seminar s:r
             ) {
            JSONObject json = new JSONObject();
            json.put("id",s.getId());
            json.put("date",s.getDate());
            json.put("theme",s.getTheme());
            json.put("link",s.getLink());
            json.put("speakerID",s.getSpeakerID());
            json.put("speakerName", userRepository.findById(s.getSpeakerID()).get().getName());
            result.put(json);
        }
        return result.toString();
    }
    @GetMapping(path = "/delete")
    public @ResponseBody String deleteSeminar  (@RequestParam Integer id){
        //用户权限验证
        String token  = UserRequest.getToken();
        Integer roleId = TokenUtil.getRoleId(token);
        if(roleId!= Role.Administrator.ordinal() && roleId!=Role.Seminar.ordinal()) return "";

        JSONObject json=new JSONObject();
        if(seminarRepository.findById(id).isPresent()) {
            Seminar seminar = seminarRepository.findById(id).get();
            seminarRepository.delete(seminar);
            json.put("result", "success");
        }
        else json.put("result", "fail");
        return json.toString();
    }

}

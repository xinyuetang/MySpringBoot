package com.fudanuniversity.cms.web.controller;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.inner.entity.deprecated.Seminar;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/seminar")
public class SeminarController {
//    @Autowired
//    private SeminarRepository seminarRepository;
//    @Autowired
//    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public JsonResult<?> addNewSeminar(@RequestBody Seminar seminar) {
//        //用户权限验证
//        String token = UserRequest.getToken();
//        Integer roleId = TokenUtil.getRoleId(token);
//        if (roleId != Role.Administrator.ordinal() && roleId != Role.Seminar.ordinal()) return "";
//
//        JSONObject json = new JSONObject();
//
//        seminarRepository.save(seminar);
//        json.put("result", "success");
//        return json.toString();
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/addlink")
    public JsonResult<?> addSeminarLink(@RequestParam String link, @RequestParam Integer id) {
//        JSONObject json = new JSONObject();
//        if (seminarRepository.findById(id).isPresent()) {
//            Seminar seminar = seminarRepository.findById(id).get();
//            seminar.setLink(link);
//            seminarRepository.save(seminar);
//            json.put("result", "success");
//        } else json.put("result", "fail");
//        return json.toString();
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/all")
    public JsonResult<?> allSeminar() {
//        JSONArray result = new JSONArray();
//        Iterable<Seminar> r = seminarRepository.findAll();
//        for (Seminar s : r
//        ) {
//            JSONObject json = new JSONObject();
//            json.put("id", s.getId());
//            json.put("date", s.getDate());
//            json.put("theme", s.getTheme());
//            json.put("link", s.getLink());
//            json.put("speakerID", s.getSpeakerID());
//            json.put("speakerName", userRepository.findById(s.getSpeakerID()).get().getName());
//            result.put(json);
//        }
//        return result.toString();
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/delete")
    public JsonResult<?> deleteSeminar(@RequestParam Integer id) {
//        //用户权限验证
//        String token = UserRequest.getToken();
//        Integer roleId = TokenUtil.getRoleId(token);
//        if (roleId != Role.Administrator.ordinal() && roleId != Role.Seminar.ordinal()) return "";
//
//        JSONObject json = new JSONObject();
//        if (seminarRepository.findById(id).isPresent()) {
//            Seminar seminar = seminarRepository.findById(id).get();
//            seminarRepository.delete(seminar);
//            json.put("result", "success");
//        } else json.put("result", "fail");
//        return json.toString();
        return JsonResult.buildSuccess();
    }

}

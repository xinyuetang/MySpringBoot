package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.repository.entity.deprecated.Recorder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping(path = "/recorder")
public class RecorderController extends BaseController{
//    @Autowired
//    RecorderRepository recorderRepository;
//    @Autowired
//    UserRepository userRepository;

    @GetMapping(path = "/all")
    public JsonResult<?> getAllRecorders() {
//        JSONArray result = new JSONArray();
//        Iterable<Recorder> recorders = recorderRepository.findAll();
//        for (Recorder r : recorders) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id", r.getId());
//            jsonObject.put("date", r.getDate());
//            jsonObject.put("recorder1ID", r.getRecorder1ID());
//
//            if (userRepository.findById(r.getRecorder1ID()).isPresent())
//                jsonObject.put("recorder1Name", userRepository.findById(r.getRecorder1ID()).get().getName());
//            jsonObject.put("recorder2ID", r.getRecorder2ID());
//            if (userRepository.findById(r.getRecorder2ID()).isPresent())
//                jsonObject.put("recorder2Name", userRepository.findById(r.getRecorder2ID()).get().getName());
//            jsonObject.put("summaryerID", r.getSummaryerID());
//            if (userRepository.findById(r.getSummaryerID()).isPresent())
//                jsonObject.put("summaryerName", userRepository.findById(r.getSummaryerID()).get().getName());
//
//            if (r.getRecord1() != null)
//                jsonObject.put("recorder1", r.getRecord1FileName());
//            if (r.getRecord2() != null)
//                jsonObject.put("recorder2", r.getRecord2FileName());
//            if (r.getSummary() != null)
//                jsonObject.put("summary", r.getSummaryFileName());
//            result.put(jsonObject);
//        }
//        return result.toString();
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/add")
    public JsonResult<?> addRecorder(@RequestBody Recorder recorder) {
//        //用户权限验证
//        String token = UserRequest.getToken();
//        Integer roleId = TokenUtil.getRoleId(token);
//        if (roleId != Role.Administrator.ordinal() && roleId != Role.Seminar.ordinal()) return "";
//
//        recorderRepository.save(recorder);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("result", "success");
//        return jsonObject.toString();
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/delete")
    public JsonResult<?> deleteRecorder(@RequestParam Integer id) {
//        //用户权限验证
//        String token = UserRequest.getToken();
//        Integer roleId = TokenUtil.getRoleId(token);
//        if (roleId != Role.Administrator.ordinal() && roleId != Role.Seminar.ordinal()) return "";
//
//        recorderRepository.deleteById(id);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("result", "success");
//        return jsonObject.toString();
        return JsonResult.buildSuccess();
    }

    @PutMapping(path = "/uploadRecorder1")
    public JsonResult<?> uploadRecorder1(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
//        JSONObject jsonObject = new JSONObject();
//        if (recorderRepository.findById(id).isPresent()) {
//            Recorder recorder = recorderRepository.findById(id).get();
//            recorder.setRecord1(file.getBytes());
//            recorder.setRecord1FileName(file.getOriginalFilename());
//            recorderRepository.save(recorder);
//            jsonObject.put("result", "success");
//        } else
//            jsonObject.put("result", "fail");
//        return jsonObject.toString();
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/downloadRecorder1")
    public JsonResult<?> downRecorder1(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
//        ServletOutputStream out = null;
//        if (recorderRepository.findById(id).isPresent()) {
//            Recorder recorder = recorderRepository.findById(id).get();
//            InputStream is = new ByteArrayInputStream(recorder.getRecord1());
//            response.setContentType("application/*");
//
//            out = response.getOutputStream();
//            int len = 0;
//            byte[] buf = new byte[1024];
//            while ((len = is.read(buf, 0, 1024)) != -1) {
//                out.write(buf, 0, len);
//            }
//            out.flush();
//            out.close();
//        }
        return JsonResult.buildSuccess();
    }

    @PutMapping(path = "/uploadRecorder2")
    public JsonResult<?> uploadRecorder2(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
//        JSONObject jsonObject = new JSONObject();
//        if (recorderRepository.findById(id).isPresent()) {
//            Recorder recorder = recorderRepository.findById(id).get();
//            recorder.setRecord2(file.getBytes());
//            recorder.setRecord2FileName(file.getOriginalFilename());
//            recorderRepository.save(recorder);
//            jsonObject.put("result", "success");
//        } else
//            jsonObject.put("result", "fail");
//        return jsonObject.toString();
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/downloadRecorder2")

    public JsonResult<?> downRecorder2(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
//        ServletOutputStream out = null;
//        if (recorderRepository.findById(id).isPresent()) {
//            Recorder recorder = recorderRepository.findById(id).get();
//            InputStream is = new ByteArrayInputStream(recorder.getRecord2());
//            response.setContentType("application/*");
//            out = response.getOutputStream();
//            int len = 0;
//            byte[] buf = new byte[1024];
//            while ((len = is.read(buf, 0, 1024)) != -1) {
//                out.write(buf, 0, len);
//            }
//            out.flush();
//            out.close();
//        }
        return JsonResult.buildSuccess();
    }

    @PutMapping(path = "/uploadSummary")
    public JsonResult<?> uploadSummary(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException, SQLException {
//        JSONObject jsonObject = new JSONObject();
//        if (recorderRepository.findById(id).isPresent()) {
//            Recorder recorder = recorderRepository.findById(id).get();
//            recorder.setSummary(file.getBytes());
//            recorder.setSummaryFileName(file.getOriginalFilename());
//            recorderRepository.save(recorder);
//            jsonObject.put("result", "success");
//        } else
//            jsonObject.put("result", "fail");
//        return jsonObject.toString();
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/downloadSummary")
    public JsonResult<?> downSummary(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
//        ServletOutputStream out = null;
//        if (recorderRepository.findById(id).isPresent()) {
//            Recorder recorder = recorderRepository.findById(id).get();
//            InputStream is = new ByteArrayInputStream(recorder.getSummary());
//            response.setContentType("application/*");
//            out = response.getOutputStream();
//            int len = 0;
//            byte[] buf = new byte[1024];
//            while ((len = is.read(buf, 0, 1024)) != -1) {
//                out.write(buf, 0, len);
//            }
//            out.flush();
//            out.close();
//        }
        return JsonResult.buildSuccess();
    }
}

package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.HocPhan;
import com.example.n5_qlsv_admin.model.MonHoc;
import com.example.n5_qlsv_admin.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/monhoc")
public class MonHocController {

    @Autowired
    private MonHocService monHocService;


    @GetMapping
    String danhSachMonHoc(Model theModel, @RequestParam(defaultValue = "0") int pageIndex) {

        int pageSize = 5;
        int totalPage = 0;
        int count = monHocService.getAllMonHoc().size();

        if (count % pageSize == 0) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }

        if(pageIndex < 0){
            pageIndex = 0;
        }else if(pageIndex > totalPage - 1){
            pageIndex = totalPage - 1;
        }

        theModel.addAttribute("totalPage", totalPage);
        theModel.addAttribute("currentPage", pageIndex);

        theModel.addAttribute("monHocs", monHocService.getAllMonHocByPageAndSize(pageIndex, pageSize));

        theModel.addAttribute("monHoc", new MonHoc());
        return "monhoc";
    }

    @PostMapping
    String luuThongTinMH(MonHoc monHoc){
        monHocService.saveMonHoc(monHoc);
        return "redirect:/monhoc";
    }

    @ResponseBody
    @GetMapping("/findMH")
    MonHoc findMonHoc(long ma_mh){
        System.out.println(ma_mh);
        return monHocService.findById(ma_mh);
    };

    @GetMapping(value = "/deleteMonHocs")
    String deleteMonHocs(HttpServletRequest req) {
        String[] ma_mhs = req.getParameterValues("idMH");
        if (ma_mhs != null) {
            for (String ma_mh : ma_mhs) {
                monHocService.deleteMonHocs(Long.parseLong(ma_mh));
            }
        }
        return "redirect:/monhoc";
    }

}

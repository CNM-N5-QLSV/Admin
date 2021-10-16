package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.LopHoc;
import com.example.n5_qlsv_admin.service.LopHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/lophoc")
public class LopHocController {

    @Autowired
    private LopHocService lopHocService;

    @GetMapping
    public String danhSachLopHoc(Model model, @RequestParam(defaultValue = "0") int pageIndex){
        int pageSize = 5;
        int totalPage = 0;
        int count = lopHocService.getAllLopHocs().size();

        if (count % pageSize == 0){
            totalPage = count / pageSize;
        }else {
            totalPage = count / pageSize + 1;
        }

        if (pageIndex < 0) {
            pageIndex = 0;
        }else if (pageIndex > totalPage - 1){
            pageIndex = totalPage - 1;
        }

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("lophocs", lopHocService.getAllLopHocsByPageAndSize(pageIndex, pageSize));
        model.addAttribute("lophoc", new LopHoc());
        return "lophoc";
    }

    @PostMapping
    public String luuThongTinLopHoc(LopHoc lopHoc){
        lopHocService.saveLopHoc(lopHoc);
        return "redirect:/lophoc";
    }

    @ResponseBody
    @GetMapping("/findLopHoc")
    public LopHoc findLopHoc(long maLopHoc){
        return lopHocService.findById(maLopHoc);
    }

    @GetMapping("/deleteLopHocs")
    public String deleteLopHoc(HttpServletRequest request){
        String[] maLopHocs = request.getParameterValues("maLop");
        if (maLopHocs != null){
            for (String maLopHoc : maLopHocs){
                lopHocService.deleteLopHocs(Long.parseLong(maLopHoc));
            }
        }
        return "redirect:/lophoc";
    }

}

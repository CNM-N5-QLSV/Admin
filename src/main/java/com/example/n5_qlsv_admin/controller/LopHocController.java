package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.LopHoc;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.LopHocService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import com.example.n5_qlsv_admin.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/lophoc")
public class LopHocController {

    @Autowired
    private LopHocService lopHocService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String danhSachLopHoc(Model model, @RequestParam(defaultValue = "0") int pageIndex, Principal principal){
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
    public String deleteLopHoc(HttpServletRequest request, RedirectAttributes redirectAttributes){
        try{
            String[] maLopHocs = request.getParameterValues("maLop");
            if (maLopHocs != null){
                for (String maLopHoc : maLopHocs){
                    lopHocService.deleteLopHocs(Long.parseLong(maLopHoc));
                }
            }
            redirectAttributes.addFlashAttribute("success", "Xóa lớp học thành công");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Lớp học đã có trong sinh viên, không thể xóa");
        }

        return "redirect:/lophoc";
    }

}

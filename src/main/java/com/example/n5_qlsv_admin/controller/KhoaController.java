package com.example.n5_qlsv_admin.controller;


import com.example.n5_qlsv_admin.model.Khoa;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.KhoaService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import com.example.n5_qlsv_admin.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/khoa")
public class KhoaController {

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String danhSachKhoa(Model model, @RequestParam(defaultValue = "0") int pageIndex, Principal principal){
        int pageSize = 5;
        int totalPage = 0;
        int count = khoaService.getAllKhoas().size();

        if (count % pageSize == 0){
            totalPage = count / pageSize;
        }else {
            totalPage = count / pageSize + 1;
        }

        if (pageIndex < 0){
            pageIndex = 0;
        }else if (pageIndex > totalPage - 1){
            pageIndex = totalPage - 1;
        }

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", pageIndex);

        model.addAttribute("khoas", khoaService.getAllKhoasByPageAndSize(pageIndex, pageSize));
        model.addAttribute("khoa", new Khoa());

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        SinhVien sinhVien = sinhVienService.findById(Long.valueOf(loginedUser.getUsername()));
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("tensinhvien", sinhVien.getTenSV());

        return "khoa";
    }

    @PostMapping
    public String luuThongTinKhoa(Khoa khoa){
        khoaService.saveKhoa(khoa);
        return "redirect:/khoa";
    }

    @ResponseBody
    @GetMapping("/findKhoa")
    public Khoa findKhoa(long maKhoa){
        return khoaService.findById(maKhoa);
    }

    @GetMapping("/deleteKhoas")
    public String deleteKhoa(HttpServletRequest request){
        String[] ma_khoas = request.getParameterValues("maKhoa");
        if (ma_khoas != null){
            for (String ma_khoa : ma_khoas){
                khoaService.deleteKhoas(Long.parseLong(ma_khoa));
            }
        }
        return "redirect:/khoa";
    }
}

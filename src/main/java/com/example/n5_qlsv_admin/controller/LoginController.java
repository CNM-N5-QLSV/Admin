package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @Autowired
    private MonHocService monHocService;

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @GetMapping
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        SinhVien sinhVien = sinhVienService.findById(loginedUser.getUsername());
        model.addAttribute("tensinhvien", sinhVien.getTenSV());

//        String userInfo = WebUtils.toString(loginedUser);
//        model.addAttribute("userInfo", userInfo);

        //Thống kê
        model.addAttribute("slSinhVien", sinhVienService.getAllSinhViens().size());
        model.addAttribute("slHocKy", hocKyService.getAllHocKys().size());
        model.addAttribute("slHocPhan", hocPhanService.getAllHocPhans().size());
        model.addAttribute("slLHP", lopHocPhanService.getAllLopHocPhans().size());
        model.addAttribute("slMonHoc", monHocService.getAllMonHoc().size());
        model.addAttribute("slKhoa", khoaService.getAllKhoas().size());
        model.addAttribute("slChuyenN", chuyenNganhService.getAllChuyenNganhs().size());

        return "index";
    }

    @GetMapping(value = "/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("messege", "Sai tài khoản hoặc mật khẩu");
        return "login";
    }

//    @GetMapping("/userInfo")
//    public String userInfo(Model model, Principal principal) {
//
//        // Sau khi user login thanh cong se co principal
//        String userName = principal.getName();
//
//        System.out.println("User Name: " + userName);
//
//        User loginedUser = (User) ((Authentication) principal).getPrincipal();
//
//        String userInfo = WebUtils.toString(loginedUser);
//        model.addAttribute("userInfo", userInfo);
//
//        return "index";
//    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
//            User loginedUser = (User) ((Authentication) principal).getPrincipal();

//            String userInfo = WebUtils.toString(loginedUser);

//            model.addAttribute("userInfo", userInfo);

            SinhVien sinhVien = sinhVienService.findById(principal.getName());

                String message = "<b style='font-size: 20px;'>Xin lỗi</b> <br><b style='color: red; font-size: 25px'>" + sinhVien.getTenSV()
                    + "</b><br> <p style='font-size: 20px;'>Bạn không có quyền truy cập vào trang này!</p>";
            model.addAttribute("message", message);
        }

        return "403Page";
    }
}

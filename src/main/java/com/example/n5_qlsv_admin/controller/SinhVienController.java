package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.ChuyenNganhService;
import com.example.n5_qlsv_admin.service.KhoaService;
import com.example.n5_qlsv_admin.service.LopHocService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import com.example.n5_qlsv_admin.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/sinhVien")
public class SinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private LopHocService lopHocService;

    @GetMapping
    String danhSachSinhVien(Model theModel, @RequestParam(defaultValue = "0") int pageIndex,
                            Principal principal, HttpSession session) {

        int pageSize = 5;
        int totalPage = 0;
        int count = sinhVienService.getAllSinhViens().size();

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

        theModel.addAttribute("sinhViens", sinhVienService.getAllSinhViensByPageAndSize(pageIndex, pageSize));
        theModel.addAttribute("chuyenNganhs", chuyenNganhService.getAllChuyenNganhs());
        theModel.addAttribute("khoas", khoaService.getAllKhoas());
        theModel.addAttribute("lopHocs", lopHocService.getAllLopHocs());
        theModel.addAttribute("sinhVien", new SinhVien());

        return "sinhvien";
    }

//    @GetMapping("/search")
//    String searchSinhVien(Model theModel, @RequestParam("mssv") long mssv) {
//        SinhVien sinhVien = sinhVienService.findById(mssv);
//        theModel.addAttribute("sinhViens", sinhVien);
//        theModel.addAttribute("mssv", mssv);
//        return "sinhvien";
//    }

    @PostMapping
    String luuThongTinSV(SinhVien sinhVien) {
        sinhVienService.saveSinhVien(sinhVien);
        return "redirect:/sinhVien";
    }

    @ResponseBody
    @GetMapping("/findSV")
    SinhVien findSV(long id){
        return sinhVienService.findById(id);
    }

    @GetMapping(value = "/deleteSinhViens")
    String deleteSinhViens(HttpServletRequest req) {
        String[] ma_svs = req.getParameterValues("idSV");
        if (ma_svs != null) {
            for (String ma_sv : ma_svs) {
                sinhVienService.deleteSinhVien(Long.parseLong(ma_sv));
            }
        }
        return "redirect:/";
    }
}

package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.ChuyenNganhService;
import com.example.n5_qlsv_admin.service.KhoaService;
import com.example.n5_qlsv_admin.service.LopHocService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private LopHocService lopHocService;

    @GetMapping("/sv")
    String searchSinhViens(Model model, String keyword){
        if(keyword == ""){
            return "redirect:/sinhVien";
        }else {
            model.addAttribute("sinhViens", sinhVienService.search(keyword));

            model.addAttribute("chuyenNganhs", chuyenNganhService.getAllChuyenNganhs());
            model.addAttribute("khoas", khoaService.getAllKhoas());
            model.addAttribute("lopHocs", lopHocService.getAllLopHocs());
            model.addAttribute("sinhVien", new SinhVien());

            model.addAttribute("word", keyword);

            return "/common/findsinhvien";
        }
    }
}

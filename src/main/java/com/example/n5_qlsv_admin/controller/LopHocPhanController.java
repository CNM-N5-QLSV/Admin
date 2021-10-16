package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.LopHocPhan;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.HocKyService;
import com.example.n5_qlsv_admin.service.HocPhanService;
import com.example.n5_qlsv_admin.service.LopHocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/lophocphan")
public class LopHocPhanController {

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private HocPhanService hocPhanService;

    @GetMapping
    String danhSachLopHocPhan(Model theModel, @RequestParam(defaultValue = "0") int pageIndex) {

        int pageSize = 5;
        int totalPage = 0;
        int count = lopHocPhanService.getAllLopHocPhans().size();

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

        theModel.addAttribute("lopHocPhans", lopHocPhanService.getAllLopHocPhansByPageAndSize(pageIndex, pageSize));
        theModel.addAttribute("hocKys", hocKyService.getAllHocKys());
        theModel.addAttribute("hocPhans", hocPhanService.getAllHocPhans());

        theModel.addAttribute("lopHocPhan", new LopHocPhan());
        return "lophocphan";

    }
    @PostMapping
    String luuThongTinLHP(LopHocPhan lopHocPhan) {
        lopHocPhanService.saveLopHocPhan(lopHocPhan);
        return "redirect:/lophocphan";
    }

    @ResponseBody
    @GetMapping("/findLHP")
    LopHocPhan findLHP(long ma_lhp){
        return lopHocPhanService.findById(ma_lhp);
    }

    @GetMapping(value = "/deleteLopHocPhans")
    String deleteLopHocPhans(HttpServletRequest req) {
        String[] ma_lhps = req.getParameterValues("idLHP");
        if (ma_lhps != null) {
            for (String ma_lhp : ma_lhps) {
                lopHocPhanService.deleteLopHocPhan(Long.parseLong(ma_lhp));
            }
        }
        return "redirect:/lophocphan";
    }

}


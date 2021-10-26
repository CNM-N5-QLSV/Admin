package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.ChuyenNganh;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.ChuyenNganhService;
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
@RequestMapping("/chuyenNganh")
public class ChuyenNganhController {

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    String danhSachChuyenNganh(Model theModel, @RequestParam(defaultValue = "0") int pageIndex, Principal principal) {

        int pageSize = 10;
        int totalPage = 0;
        int count = chuyenNganhService.getAllChuyenNganhs().size();

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

        theModel.addAttribute("khoas", khoaService.getAllKhoas());
        theModel.addAttribute("chuyenNganhs", chuyenNganhService.getAllChuyenNganhsByPageAndSize(pageIndex, pageSize));
        theModel.addAttribute("chuyenNganh", new ChuyenNganh());

        return "chuyennganh";
    }

    @PostMapping
    String luuThongTinChuyenNganh(ChuyenNganh chuyenNganh) {
        chuyenNganhService.saveChuyenNganh(chuyenNganh);
        return "redirect:/chuyenNganh";
    }

    @ResponseBody
    @GetMapping("/findCN")
    ChuyenNganh findHK(long id){
        return chuyenNganhService.findById(id);
    }

    @GetMapping(value = "/deleteChuyenNganhs")
    String deleteHocKys(HttpServletRequest req) {
        String[] ids = req.getParameterValues("idCN");
        if (ids != null) {
            for (String id : ids) {
                chuyenNganhService.deleteChuyenNganh(Long.parseLong(id));
            }
        }
        return "redirect:/chuyenNganh";
    }
}
package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.HocKy;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.HocKyService;
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
@RequestMapping("/hocKy")
public class HocKyController {

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    String danhSachHocKy(Model theModel, @RequestParam(defaultValue = "0") int pageIndex, Principal principal) {

        int pageSize = 10;
        int totalPage = 0;
        int count = hocKyService.getAllHocKys().size();

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

        theModel.addAttribute("hocKys", hocKyService.getAllHocKysByPageAndSize(pageIndex, pageSize));
        theModel.addAttribute("hocKy", new HocKy());

        return "hocky";
    }

    @PostMapping
    String luuThongTinHK(HocKy hocKy) {
        hocKyService.saveHocKy(hocKy);
        return "redirect:/hocKy";
    }

    @ResponseBody
    @GetMapping("/findHK")
    HocKy findHK(long id){
        return hocKyService.findById(id);
    }

    @GetMapping(value = "/deleteHocKys")
    String deleteHocKys(HttpServletRequest req) {
        String[] ids = req.getParameterValues("idHK");
        if (ids != null) {
            for (String id : ids) {
                hocKyService.deleteHocKy(Long.parseLong(id));
            }
        }
        return "redirect:/hocKy";
    }

}

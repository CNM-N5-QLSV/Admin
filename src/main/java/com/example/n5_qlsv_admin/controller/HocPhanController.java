package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.HocPhan;
import com.example.n5_qlsv_admin.service.ChuyenNganhService;
import com.example.n5_qlsv_admin.service.HocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/hocphan")
public class HocPhanController {

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @GetMapping
    public String danhSachHocPhan(Model model, @RequestParam(defaultValue = "0") int pageIndex){
        int pageSize = 5;
        int totalPage = 0;
        int count = hocPhanService.getAllHocPhans().size();

        if (count % pageSize == 0){
            totalPage = count / pageSize;
        }else {
            totalPage = count / pageSize + 1;
        }

        if (pageIndex < 0){
            pageIndex = 0;
        }else if (pageIndex > totalPage - 1){
            pageIndex = totalPage -1;
        }
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", pageIndex);

        model.addAttribute("chuyenNganhs", chuyenNganhService.getAllChuyenNganhs());
        model.addAttribute("hocphans", hocPhanService.getAllHocPhansByPageAndSize(pageIndex, pageSize));
        model.addAttribute("hocPhan", new HocPhan());
        return "hocphan";
    }

    @PostMapping
    public String luuThongTinHocPhan(HocPhan hocPhan){
        hocPhanService.saveHocPhan(hocPhan);
        return "redirect:/hocphan";
    }

    @ResponseBody
    @GetMapping("/findHocPhan")
    public HocPhan findHocPhan(long maHocPhan){
        return hocPhanService.findById(maHocPhan);
    }

    @GetMapping("/deleteHocPhans")
    public String deleteHocPhan(HttpServletRequest request){
        String[] maHocPhans = request.getParameterValues("maHocPhan");
        if(maHocPhans != null){
            for(String maHocPhan : maHocPhans){
                hocPhanService.deleteHocPhans(Long.parseLong(maHocPhan));
            }
        }
        return "redirect:/hocphan";
    }

}

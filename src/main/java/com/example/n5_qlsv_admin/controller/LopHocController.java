package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.LopHoc;
import com.example.n5_qlsv_admin.service.LopHocService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public String luuThongTinLopHoc(LopHoc lopHoc, RedirectAttributes redirectAttributes){
        try {
            lopHocService.saveLopHoc(lopHoc);
            redirectAttributes.addFlashAttribute("mess", "Lưu thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "warning");
        }

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
                redirectAttributes.addFlashAttribute("mess", "Xóa lớp học thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Lớp học đã có trong sinh viên, không thể xóa");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/lophoc";
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        try {
            lopHocService.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/lophoc";
    }
}

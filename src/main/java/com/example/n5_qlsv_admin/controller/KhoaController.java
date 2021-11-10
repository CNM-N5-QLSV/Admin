package com.example.n5_qlsv_admin.controller;


import com.example.n5_qlsv_admin.model.Khoa;
import com.example.n5_qlsv_admin.service.KhoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/khoa")
public class KhoaController {

    @Autowired
    private KhoaService khoaService;

    @GetMapping
    public String danhSachKhoa(Model model, @RequestParam(defaultValue = "0") int pageIndex){
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

        return "khoa";
    }

    @PostMapping
    public String luuThongTinKhoa(Khoa khoa, RedirectAttributes redirectAttributes){
        try{
            khoaService.saveKhoa(khoa);
            redirectAttributes.addFlashAttribute("mess", "Lưu thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/khoa";
    }

    @ResponseBody
    @GetMapping("/findKhoa")
    public Khoa findKhoa(long maKhoa){
        return khoaService.findById(maKhoa);
    }

    @GetMapping("/deleteKhoas")
    public String deleteKhoa(HttpServletRequest request, RedirectAttributes redirectAttributes){
        try{
            String[] ma_khoas = request.getParameterValues("maKhoa");
            if (ma_khoas != null){
                for (String ma_khoa : ma_khoas){
                    khoaService.deleteKhoas(Long.parseLong(ma_khoa));
                }
                redirectAttributes.addFlashAttribute("mess", "Xóa khoa thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Khoa nằm trong chuyên ngành, sinh viên hoặc giảng viên, không thể xóa");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/khoa";
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        try {
            khoaService.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/khoa";

    }
}

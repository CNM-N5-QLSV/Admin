package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.MonHoc;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.KhoaService;
import com.example.n5_qlsv_admin.service.MonHocService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/monhoc")
public class MonHocController {

    @Autowired
    private MonHocService monHocService;

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    String danhSachMonHoc(Model theModel, @RequestParam(defaultValue = "0") int pageIndex, Long mk
            , Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        SinhVien sinhVien = sinhVienService.findById(loginedUser.getUsername());
        theModel.addAttribute("tensinhvien", sinhVien.getTenSV());

        int pageSize = 5;
        int totalPage = 0;
        int count = 0;

        if(mk != null){
            count = monHocService.findAllByKhoa(mk, 0, 0).size();
        }else {
            count = 20;
        }

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

        if(mk != null){
            theModel.addAttribute("monHocs",
                    monHocService.findAllByKhoa(mk, pageIndex, pageSize));
        }else {
            theModel.addAttribute("monHocs",
                    monHocService.getAllMonHoc().subList(
                            monHocService.getAllMonHoc().size() - 5,
                            monHocService.getAllMonHoc().size()
                    ));
        }

        theModel.addAttribute("totalPage", totalPage);
        theModel.addAttribute("currentPage", pageIndex);
        theModel.addAttribute("khoas", khoaService.getAllKhoas());
        theModel.addAttribute("mk", mk);
        theModel.addAttribute("monHoc", new MonHoc());

        return "monhoc";
    }

    @PostMapping
    String luuThongTinMH(MonHoc monHoc, RedirectAttributes redirectAttributes){
        try{
            monHocService.saveMonHoc(monHoc);
            redirectAttributes.addFlashAttribute("mess", "Lưu thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/monhoc";
    }

    @ResponseBody
    @GetMapping("/findMH")
    MonHoc findMonHoc(long ma_mh){
        return monHocService.findById(ma_mh);
    };

    @GetMapping(value = "/deleteMonHocs")
    String deleteMonHocs(HttpServletRequest req, RedirectAttributes redirectAttributes) {
        try{
            String[] ma_mhs = req.getParameterValues("idMH");
            if (ma_mhs != null) {
                for (String ma_mh : ma_mhs) {
                    monHocService.deleteMonHocs(Long.parseLong(ma_mh));
                }
                redirectAttributes.addFlashAttribute("mess", "Xóa môn học thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/monhoc";
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        try {
            monHocService.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/monhoc";

    }
}

package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.ChuyenNganhService;
import com.example.n5_qlsv_admin.service.KhoaService;
import com.example.n5_qlsv_admin.service.LopHocService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

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
                            String keyword, Long mk) {

        int pageSize = 5;
        int totalPage = 0;
        int count = 0;

        if(keyword != null && keyword != ""){
            count = sinhVienService.search(keyword, 0, 0).size();
        }else if (mk != null){
            count =  sinhVienService.findAllSinhViensByKhoa(mk, 0, 0).size();
        }else {
            count = 10;
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

        if (mk != null){
            theModel.addAttribute("sinhViens", sinhVienService.findAllSinhViensByKhoa(mk, pageIndex, pageSize));
        }else if(keyword != null && keyword != ""){
            theModel.addAttribute("sinhViens", sinhVienService.search(keyword, pageIndex, pageSize));
        } else {
            theModel.addAttribute("sinhViens", sinhVienService.getAllSinhViensByPageAndSize(pageIndex, pageSize));
        }

        theModel.addAttribute("mk", mk);
        theModel.addAttribute("keyword", keyword);

        theModel.addAttribute("totalPage", totalPage);
        theModel.addAttribute("currentPage", pageIndex);

        theModel.addAttribute("chuyenNganhs", chuyenNganhService.getAllChuyenNganhs());
        theModel.addAttribute("khoas", khoaService.getAllKhoas());
        theModel.addAttribute("lopHocs", lopHocService.getAllLopHocs());
        theModel.addAttribute("sinhVien", new SinhVien());

        return "sinhvien";
    }

    @PostMapping
    String luuThongTinSV(SinhVien sinhVien, RedirectAttributes redirectAttributes) {
        try{
            sinhVienService.saveSinhVien(sinhVien);
            redirectAttributes.addFlashAttribute("mess", "Lưu thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/sinhVien";
    }

    @ResponseBody
    @GetMapping("/findSV")
    SinhVien findSV(String id){
        return sinhVienService.findById(id);
    }

    @GetMapping(value = "/deleteSinhViens")
    String deleteSinhViens(HttpServletRequest req, RedirectAttributes redirectAttributes) {
        try{
            String[] ma_svs = req.getParameterValues("idSV");
            if (ma_svs != null) {
                for (String ma_sv : ma_svs) {
                    sinhVienService.deleteSinhVien(ma_sv);
                }
                redirectAttributes.addFlashAttribute("mess", "Xóa sinh viên thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sinh viên này đã đăng ký lớp học phần, không thể xóa");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/sinhVien";
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        try {
            sinhVienService.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/sinhVien";

    }
}

package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.GiangVien;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.GiangVienService;
import com.example.n5_qlsv_admin.service.KhoaService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import com.example.n5_qlsv_admin.util.WebUtils;
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
@RequestMapping("/giangvien")
public class GiangVienController {

    @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    String danhSachMonHoc(Model theModel, @RequestParam(defaultValue = "0") int pageIndex, Principal principal) {

        int pageSize = 8;
        int totalPage = 0;
        int count = giangVienService.getAllGiangVien().size();

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

        theModel.addAttribute("giangViens", giangVienService.getAllGiangVienByPageAndSize(pageIndex, pageSize));

        theModel.addAttribute("khoas", khoaService.getAllKhoas());

        theModel.addAttribute("giangVien", new GiangVien());

        return "giangvien";
    }

    @PostMapping
    String luuThongTinGV(GiangVien giangVien, RedirectAttributes redirectAttributes){
        try{
            giangVienService.saveGiangVien(giangVien);
            redirectAttributes.addFlashAttribute("mess", "Lưu thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/giangvien";
    }

    @ResponseBody
    @GetMapping("/findGV")
    GiangVien findGiangVien(long ma_gv){
        System.out.println(ma_gv);
        return giangVienService.findById(ma_gv);
    };

    @GetMapping(value = "/deleteGiangViens")
    String deleteGiangViens(HttpServletRequest req, RedirectAttributes redirectAttributes) {
        try{
            String[] ma_gvs = req.getParameterValues("idGV");
            if (ma_gvs != null) {
                for (String ma_gv : ma_gvs) {
                    giangVienService.deleteGiangViens(Long.parseLong(ma_gv));
                }
                redirectAttributes.addFlashAttribute("mess", "Xóa giảng viên thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Giảng viên này đã được phân công trong lớp học phần, không thể xóa");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/giangvien";
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        try {
            giangVienService.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/giangvien";

    }
}

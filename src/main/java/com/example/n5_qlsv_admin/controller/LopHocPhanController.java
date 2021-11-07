package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.ChiTietLopHocPhan;
import com.example.n5_qlsv_admin.model.LopHocPhan;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.*;
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
@RequestMapping("/lophocphan")
public class LopHocPhanController {

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private CTLHPService ctlhpService;

    @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    String danhSachLopHocPhan(Model theModel, @RequestParam(defaultValue = "0") int pageIndex, Principal principal) {

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
    String luuThongTinLHP(LopHocPhan lopHocPhan, RedirectAttributes redirectAttributes) {
        try{
            lopHocPhanService.saveLopHocPhan(lopHocPhan);
            redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/lophocphan";
    }

    @ResponseBody
    @GetMapping("/findLHP")
    LopHocPhan findLHP(long ma_lhp){
        return lopHocPhanService.findById(ma_lhp);
    }

    @GetMapping(value = "/deleteLopHocPhans")
    String deleteLopHocPhans(HttpServletRequest req, RedirectAttributes redirectAttributes) {
        try{
            String[] ma_lhps = req.getParameterValues("idLHP");
            if (ma_lhps != null) {
                for (String ma_lhp : ma_lhps) {
                    lopHocPhanService.deleteLopHocPhan(Long.parseLong(ma_lhp));
                }
                redirectAttributes.addFlashAttribute("mess", "Xóa lớp học phần thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Lớp học phần này đã có sinh viên đăng ký, không thể xóa");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/lophocphan";
    }

    @GetMapping("/CTLHP")
    String xemCTLHP(Model model, @RequestParam long idLHP){
        model.addAttribute("CTLHPs", ctlhpService.getAllCTLHPsByLHP(idLHP));
        model.addAttribute("chiTietLopHocPhan", new ChiTietLopHocPhan());
        model.addAttribute("giangViens", giangVienService.getAllGiangVien());
        model.addAttribute("idLopHocPhan", idLHP);
        return "chitietlophocphan";
    }

    @PostMapping("/CTLHP")
    String themCTLHP(ChiTietLopHocPhan chiTietLopHocPhan, long idLHP, RedirectAttributes redirectAttributes){
        try{
            ctlhpService.saveCTLHP(chiTietLopHocPhan, idLHP);
            redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/lophocphan/CTLHP?idLHP=" + idLHP;
    }

    @GetMapping(value = "/delCTLHPs")
    String deleteCTLHPs(HttpServletRequest req, @RequestParam long idLHP, RedirectAttributes redirectAttributes) {
        try{
            String[] ma_ctlhps = req.getParameterValues("idCTLHP");
            if (ma_ctlhps != null) {
                for (String ma_ctlhp : ma_ctlhps) {
                    ctlhpService.deleteCTLHP(Long.parseLong(ma_ctlhp));
                }
                redirectAttributes.addFlashAttribute("mess", "Xóa chi tiết lớp học phần thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Lớp học phần này đã có sinh viên đăng ký, không thể xóa");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/lophocphan/CTLHP?idLHP=" + idLHP;
    }

    @ResponseBody
    @GetMapping("/findCTLHP")
    ChiTietLopHocPhan findCTLHP(long ma_ctlhp){
        return ctlhpService.findById(ma_ctlhp);
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        try {
            lopHocPhanService.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/lophocphan";

    }
}


package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.HocPhanKhung;
import com.example.n5_qlsv_admin.service.HocPhanKhungService;
import com.example.n5_qlsv_admin.service.HocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/hoc-phan-khung")
public class HocPhanKhungController {

    @Autowired
    private HocPhanKhungService service;

    @Autowired
    private HocPhanService hocPhanService;

    @GetMapping
    public String danhSachHPK(Model model, @RequestParam(defaultValue = "0") int pageIndex){
        int pageSize = 8;
        int totalPage = 0;
        int count = service.getAllHPKByPageAndSize(pageIndex, 0).size();

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

        model.addAttribute("hpks", service.getAllHPKByPageAndSize(pageIndex, pageSize));
        model.addAttribute("hocPhans", hocPhanService.findHocPhanNotInHPK());
        model.addAttribute("hpk", new HocPhanKhung());

        return "hocphankhung";
    }

    @PostMapping
    public String luuThongTinHPK(HocPhanKhung hpk, RedirectAttributes redirectAttributes){
        try{
            service.saveHPK(hpk);
            redirectAttributes.addFlashAttribute("mess", "Lưu thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/hoc-phan-khung";
    }

    @ResponseBody
    @GetMapping("/findHPK")
    public HocPhanKhung findHPK(long maHPK){
        return service.findHPKById(maHPK);
    }

    @GetMapping("/deleteHPKs")
    public String deleteHPK(HttpServletRequest request, RedirectAttributes redirectAttributes){
        try{
            String[] ids = request.getParameterValues("idHPK");
            if (ids != null){
                for (String id : ids){
                    service.deleteHPK(Long.parseLong(id));
                }
                redirectAttributes.addFlashAttribute("mess", "Xóa thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/hoc-phan-khung";
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        try {
            service.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/hoc-phan-khung";

    }

}

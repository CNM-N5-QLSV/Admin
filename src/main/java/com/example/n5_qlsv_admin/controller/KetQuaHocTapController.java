package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.KetQuaHocTap;
import com.example.n5_qlsv_admin.service.KetQuaHocTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ketquahoctap")
public class KetQuaHocTapController {

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;

    @GetMapping
    String danhSachMonHoc(Model model, @RequestParam(defaultValue = "0") int pageIndex,
                          String maSV_KQHT) {
        int pageSize = 5;
        int totalPage = 0;
        int count = 0;

        if(maSV_KQHT != null && maSV_KQHT != ""){
            count = ketQuaHocTapService.findKQHTByMaSV(maSV_KQHT, 0, 0).size();
        }else {
            count = ketQuaHocTapService.getAllKQHTsByPageAndSize(0, 0).size();
        }

        if (count % pageSize == 0) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }

        if (pageIndex < 0) {
            pageIndex = 0;
        } else if (pageIndex > totalPage - 1) {
            pageIndex = totalPage - 1;
        }

        if(maSV_KQHT != null && maSV_KQHT != ""){
            model.addAttribute("kqhts", ketQuaHocTapService.findKQHTByMaSV(maSV_KQHT, pageIndex, pageSize));
        }else {
            model.addAttribute("kqhts", ketQuaHocTapService.getAllKQHTsByPageAndSize(pageIndex, pageSize));
        }

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("maSVKQHT", maSV_KQHT);
        model.addAttribute("kqht", new KetQuaHocTap());

        return "ketquahoctap";
    }

    @PostMapping
    public String luuKQHT(KetQuaHocTap kqht, RedirectAttributes redirectAttributes) {
        try {
            ketQuaHocTapService.saveKetQuaHT(kqht);
            redirectAttributes.addFlashAttribute("mess", "Lưu thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/ketquahoctap";
    }

    @ResponseBody
    @GetMapping("/findKQHT")
    public KetQuaHocTap findKhoa(long maKQHT) {
        return ketQuaHocTapService.findById(maKQHT);
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes) {
        try {
            ketQuaHocTapService.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/ketquahoctap";

    }
}

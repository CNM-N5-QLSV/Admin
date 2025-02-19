package com.example.n5_qlsv_admin.controller;

import com.example.n5_qlsv_admin.model.HocPhan;
import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.ChuyenNganhService;
import com.example.n5_qlsv_admin.service.HocPhanService;
import com.example.n5_qlsv_admin.service.MonHocService;
import com.example.n5_qlsv_admin.service.SinhVienService;
import com.example.n5_qlsv_admin.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/hocphan")
public class HocPhanController {

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @Autowired
    private MonHocService monHocService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String danhSachHocPhan(Model model, @RequestParam(defaultValue = "0") int pageIndex,
                                  String keyword, Long maCN, Principal principal){

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        SinhVien sinhVien = sinhVienService.findById(loginedUser.getUsername());
        model.addAttribute("tensinhvien", sinhVien.getTenSV());

        int pageSize = 5;
        int totalPage = 0;
        int count = 0;

        if(keyword != null && keyword != ""){
            count = hocPhanService.searchAllByKeyword(keyword, 0, 0).size();
        }else if (maCN != null){
            count =  hocPhanService.findAllByChuyenNganh(maCN, 0, 0).size();
        }else {
            count = 5;
        }

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

        if (maCN != null){
            model.addAttribute("hocphans", hocPhanService.findAllByChuyenNganh(maCN, pageIndex, pageSize));
        }else if(keyword != null && keyword != ""){
            model.addAttribute("hocphans", hocPhanService.searchAllByKeyword(keyword, pageIndex, pageSize));
        } else {
            model.addAttribute("hocphans", hocPhanService.getAllHocPhans().subList(
                    hocPhanService.getAllHocPhans().size() - 5,
                    hocPhanService.getAllHocPhans().size()
            ));
        }

        model.addAttribute("mcn", maCN);
        model.addAttribute("keyword", keyword);

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", pageIndex);

        model.addAttribute("monHocs", monHocService.getAllMonHoc());
        model.addAttribute("chuyenNganhs", chuyenNganhService.getAllChuyenNganhs());
        model.addAttribute("hocPhan", new HocPhan());

        return "hocphan";
    }

    @PostMapping
    public String luuThongTinHocPhan(HocPhan hocPhan, RedirectAttributes redirectAttributes){
        try{
            hocPhanService.saveHocPhan(hocPhan);
            redirectAttributes.addFlashAttribute("mess", "Lưu thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/hocphan";
    }

    @ResponseBody
    @GetMapping("/findHocPhan")
    public HocPhan findHocPhan(String maHocPhan){
        return hocPhanService.findById(maHocPhan);
    }

    @GetMapping("/deleteHocPhans")
    public String deleteHocPhan(HttpServletRequest request, RedirectAttributes redirectAttributes){
        try{
            String[] maHocPhans = request.getParameterValues("mahp");
            if(maHocPhans != null){
                for(String maHocPhan : maHocPhans){
                    hocPhanService.deleteHocPhans(maHocPhan);
                }
                redirectAttributes.addFlashAttribute("mess", "Xóa học phần thành công");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Bạn chưa chọn dòng để xóa");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Học phần này đã có trong lớp học phần, không thể xóa");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/hocphan";
    }

    @PostMapping("/upload")
    String saveObjectsByFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        try {
            hocPhanService.uploadFile(fileUpload);
            redirectAttributes.addFlashAttribute("mess", "Tải file lên thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Sai định dạng file (.xlsx) hoặc lớn hơn 5MB");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/hocphan";

    }
}

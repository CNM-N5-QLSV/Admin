package com.example.n5_qlsv_admin.service;


import com.example.n5_qlsv_admin.model.LopHocPhan;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LopHocPhanService {

    List<LopHocPhan> getAllLopHocPhans();

    List<LopHocPhan> getAllLopHocPhansByPageAndSize(int pageIndex, int pageSize);

    void saveLopHocPhan(LopHocPhan lopHocPhan);

    void deleteLopHocPhan(long ma_lhp);

    LopHocPhan findById(long ma_lhp);

    void uploadFile(MultipartFile file);
}

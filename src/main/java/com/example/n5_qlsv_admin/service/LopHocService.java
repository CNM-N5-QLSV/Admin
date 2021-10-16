package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.LopHoc;

import java.util.List;

public interface LopHocService {

    List<LopHoc> getAllLopHocs();

    List<LopHoc> getAllLopHocsByPageAndSize(int pageIndex, int pageSize);

    void saveLopHoc(LopHoc lopHoc);

    void deleteLopHocs(long maLopHoc);

    LopHoc findById(long maLopHoc);
}

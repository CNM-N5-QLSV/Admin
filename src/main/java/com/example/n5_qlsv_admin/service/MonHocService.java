package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.MonHoc;

import java.util.List;

public interface MonHocService {

    List<MonHoc> getAllMonHoc();

    List<MonHoc> getAllMonHocByPageAndSize(int pageIndex, int pageSize);

    void saveMonHoc(MonHoc monHoc);

    void deleteMonHocs(long maMonHoc);

    MonHoc findById(long maMonHoc);

    List<MonHoc> getAllMonHocNotInHocPhan();
}

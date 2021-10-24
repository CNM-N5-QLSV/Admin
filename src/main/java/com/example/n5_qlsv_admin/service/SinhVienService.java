package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.SinhVien;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface SinhVienService {

    List<SinhVien> getAllSinhViens();

    List<SinhVien> getAllSinhViensByPageAndSize(int pageIndex, int pageSize);

    void saveSinhVien(SinhVien sinhVien);

    void deleteSinhVien(long ma_sv);

    SinhVien findById(long ma_sv);
}

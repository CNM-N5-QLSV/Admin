package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.GiangVien;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GiangVienService {

    List<GiangVien> getAllGiangVien();

    List<GiangVien> getAllGiangVienByPageAndSize(int pageIndex, int pageSize);

    void saveGiangVien(GiangVien giangVien);

    void deleteGiangViens(long maGiangVien);

    GiangVien findById(long maGiangVien);

    void uploadFile(MultipartFile file);
}

package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.Khoa;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface KhoaService {

    List<Khoa> getAllKhoas();

    List<Khoa> getAllKhoasByPageAndSize(int pageIndex, int pageSize);

    void saveKhoa(Khoa khoa);

    void deleteKhoas(long ma_khoa);

    Khoa findById(long ma_khoa);

    void uploadFile(MultipartFile file);
}

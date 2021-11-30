package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.HocPhanKhung;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HocPhanKhungService {

    List<HocPhanKhung> getAllHPK();

    List<HocPhanKhung> getAllHPKByPageAndSize(int pageIndex, int pageSize);

    void saveHPK(HocPhanKhung hpk);

    void deleteHPK(long id);

    HocPhanKhung findHPKById(long id);

    void uploadFile(MultipartFile file);

}

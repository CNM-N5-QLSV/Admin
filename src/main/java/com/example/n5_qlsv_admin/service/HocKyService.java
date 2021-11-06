package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.HocKy;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HocKyService {

    List<HocKy> getAllHocKys();

    List<HocKy> getAllHocKysByPageAndSize(int pageIndex, int pageSize);

    void saveHocKy(HocKy hocKy);

    void deleteHocKy(long id);

    HocKy findById(long id);

    void uploadFile(MultipartFile file);
}

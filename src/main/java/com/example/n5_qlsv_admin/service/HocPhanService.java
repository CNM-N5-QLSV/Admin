package com.example.n5_qlsv_admin.service;



import com.example.n5_qlsv_admin.model.HocPhan;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HocPhanService {

    void saveHocPhan(HocPhan hocPhan);

    void deleteHocPhans(String maHocPhan);

    void uploadFile(MultipartFile file);

    HocPhan findById(String maHocPhan);

    List<HocPhan> getAllHocPhans();

    List<HocPhan> getAllHocPhansByPageAndSize(int pageIndex, int pageSize);

    List<HocPhan> findHocPhanNotInHPK();

    List<HocPhan> searchAllByKeyword(String keyword, int pageIndex, int pageSize);

    List<HocPhan> findAllByChuyenNganh(Long maCN, int pageIndex, int pageSize);
}

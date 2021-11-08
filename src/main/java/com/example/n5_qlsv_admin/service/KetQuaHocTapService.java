package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.HocPhan;
import com.example.n5_qlsv_admin.model.KetQuaHocTap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface KetQuaHocTapService {

    List<KetQuaHocTap> findKQHTByMaSV(String maSV);

    Set<Long> findMaHKByMaSV(String maSV);

    void saveKetQuaHT(KetQuaHocTap ketQuaHocTap);

    void deleteKQHT(Long maKQHT);

    KetQuaHocTap findKQHTByMaSVAndMaLHP(String maSV, Long maLHP);

    void uploadFile(MultipartFile file);

    List<KetQuaHocTap> getAllKQHTsByPageAndSize(int pageIndex, int pageSize);

}

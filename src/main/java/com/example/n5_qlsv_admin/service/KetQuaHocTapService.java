package com.example.n5_qlsv_admin.service;

import com.example.n5_qlsv_admin.model.KetQuaHocTap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface KetQuaHocTapService {

    List<KetQuaHocTap> findKQHTByMaSV(String maSV, int pageIndex, int pageSize);

    Set<Long> findMaHKByMaSV(String maSV);

    void saveKetQuaHT(KetQuaHocTap ketQuaHocTap);

    void deleteKQHT(Long maKQHT);

    KetQuaHocTap findKQHTByMaSVAndMaLHP(String maSV, Long maLHP);

    KetQuaHocTap findById(long ma_kqht);

    void uploadFile(MultipartFile file);

    List<KetQuaHocTap> getAllKQHTsByPageAndSize(int pageIndex, int pageSize);

}

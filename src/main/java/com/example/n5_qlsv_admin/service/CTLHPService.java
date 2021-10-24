package com.example.n5_qlsv_admin.service;


import com.example.n5_qlsv_admin.model.ChiTietLopHocPhan;

import java.util.List;

public interface CTLHPService {

    List<ChiTietLopHocPhan> getAllCTLHPsByLHP(Long idLHP);

    void saveCTLHP(ChiTietLopHocPhan chiTietLopHocPhan, Long maLHP);

    void deleteCTLHP(long ma_ctlhp);

    ChiTietLopHocPhan findById(long ma_ctlhp);

}

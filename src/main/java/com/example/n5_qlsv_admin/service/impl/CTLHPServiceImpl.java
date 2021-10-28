package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.ChiTietLopHocPhan;
import com.example.n5_qlsv_admin.model.LopHocPhan;
import com.example.n5_qlsv_admin.service.CTLHPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CTLHPServiceImpl implements CTLHPService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.lophocphan}")
    private String urlLHP;

    @Value("${app.url.ctlhp}")
    private String urlCTLHP;

    @Override
    public List<ChiTietLopHocPhan> getAllCTLHPsByLHP(Long idLHP) {
        ResponseEntity<List<ChiTietLopHocPhan>> responseEntity
                = restTemplate.exchange(urlLHP + "/" + idLHP + "/CTLHP", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ChiTietLopHocPhan>>() {
                });
        List<ChiTietLopHocPhan> chiTietLopHocPhanList = responseEntity.getBody();
        return chiTietLopHocPhanList;
    }

    @Override
    public void saveCTLHP(ChiTietLopHocPhan chiTietLopHocPhan, Long maLHP) {
        long maCTLHP = chiTietLopHocPhan.getMaCTLHP();
        if(maCTLHP == 0){
            if(chiTietLopHocPhan.getGiangVien().getMaGV() == 0){
                chiTietLopHocPhan.setGiangVien(null);
            }
            restTemplate.postForEntity(urlLHP + "/" + maLHP + "/CTLHP", chiTietLopHocPhan, String.class);
        }else {
            if(chiTietLopHocPhan.getGiangVien().getMaGV() == 0){
                chiTietLopHocPhan.setGiangVien(null);
            }
            restTemplate.put(urlCTLHP + "/" + maCTLHP, chiTietLopHocPhan);
        }
    }

    @Override
    public void deleteCTLHP(long ma_ctlhp) {
        restTemplate.delete(urlCTLHP + "/" + ma_ctlhp);
    }

    @Override
    public ChiTietLopHocPhan findById(long ma_ctlhp) {
        ChiTietLopHocPhan chiTietLopHocPhan = restTemplate.getForObject(urlCTLHP + "/" + ma_ctlhp,
                ChiTietLopHocPhan.class);
        return chiTietLopHocPhan;
    }
}

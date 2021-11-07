package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.SinhVien;
import com.example.n5_qlsv_admin.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.url.sinhvien}")
    private String url;

    @Override
    public List<SinhVien> getAllSinhViens() {
        ResponseEntity<List<SinhVien>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SinhVien>>() {
                });
        List<SinhVien> sinhVienList = responseEntity.getBody();
        return sinhVienList;
    }

    @Override
    public List<SinhVien> getAllSinhViensByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<SinhVien>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SinhVien>>() {
                });
        List<SinhVien> sinhVienList = responseEntity.getBody();
        return sinhVienList;
    }

    @Override
    public void saveSinhVien(SinhVien sinhVien) {
        String ma_sv = sinhVien.getMaSV();
        if (ma_sv == "") {
//            sinhVien.setNgayVaoTruong(new Date(System.currentTimeMillis()));
            sinhVien.setPassword(passwordEncoder.encode("1111"));
            sinhVien.setRoleName("ROLE_USER");
            if(sinhVien.getChuyenNganh().getMaChuyenNganh() == 0){
                sinhVien.setChuyenNganh(null);
            }
            if(sinhVien.getLopHoc().getMaLop() == 0){
                sinhVien.setLopHoc(null);
            }
            if(sinhVien.getKhoa().getMaKhoa() == 0){
                sinhVien.setKhoa(null);
            }
            restTemplate.postForEntity(url, sinhVien, String.class);
        } else {
            if(sinhVien.getChuyenNganh().getMaChuyenNganh() == 0){
                sinhVien.setChuyenNganh(null);
            }
            if(sinhVien.getLopHoc().getMaLop() == 0){
                sinhVien.setLopHoc(null);
            }
            if(sinhVien.getKhoa().getMaKhoa() == 0){
                sinhVien.setKhoa(null);
            }
            restTemplate.put(url + "/" + ma_sv, sinhVien);
        }
    }

    @Override
    public void deleteSinhVien(String ma_sv) {
        restTemplate.delete(url + "/" + ma_sv);
    }

    @Override
    public SinhVien findById(String ma_sv) {
        SinhVien sinhVien = restTemplate.getForObject(url + "/" + ma_sv, SinhVien.class);
        return sinhVien;
    }

    @Override
    public List<SinhVien> search(String keyword, int pageIndex, int pageSize) {
        ResponseEntity<List<SinhVien>> responseEntity
                = restTemplate.exchange(url + "/keyword=" + keyword + "?page=" + pageIndex + "&size=" + pageSize
                , HttpMethod.GET, null
                , new ParameterizedTypeReference<List<SinhVien>>() {
                });
        List<SinhVien> sinhVienList = responseEntity.getBody();
        return sinhVienList;
    }

    @Override
    public List<SinhVien> findAllSinhViensByKhoa(Long maKhoa, int pageIndex, int pageSize) {
        ResponseEntity<List<SinhVien>> responseEntity
                = restTemplate.exchange(url + "/khoa=" + maKhoa + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SinhVien>>() {
                });
        List<SinhVien> sinhVienList = responseEntity.getBody();
        return sinhVienList;
    }

    @Override
    public void uploadFile(MultipartFile file) {
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(convert(file)));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> response = restTemplate.exchange(url + "/upload",
                HttpMethod.POST, requestEntity, String.class);
    }

    private File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }
}

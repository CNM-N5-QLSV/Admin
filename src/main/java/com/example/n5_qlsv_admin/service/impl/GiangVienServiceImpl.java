package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.GiangVien;
import com.example.n5_qlsv_admin.service.GiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GiangVienServiceImpl implements GiangVienService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.giangvien}")
    private String url;

    @Override
    public List<GiangVien> getAllGiangVien() {
        ResponseEntity<List<GiangVien>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GiangVien>>() {
                });
        List<GiangVien> giangVienList = responseEntity.getBody();
        return giangVienList;
    }

    @Override
    public List<GiangVien> getAllGiangVienByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<GiangVien>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GiangVien>>() {
                });
        List<GiangVien> giangVienList = responseEntity.getBody();
        return giangVienList;
    }

    @Override
    public void saveGiangVien(GiangVien giangVien) {
        long maGiangVien = giangVien.getMaGV();
        if(maGiangVien == 0){
            if(giangVien.getKhoa().getMaKhoa() == 0){
                giangVien.setKhoa(null);
            }
            restTemplate.postForEntity(url, giangVien, String.class);
        }else
        {
            if(giangVien.getKhoa().getMaKhoa() == 0){
                giangVien.setKhoa(null);
            }
            restTemplate.put(url + "/" + maGiangVien, giangVien);
        }
    }

    @Override
    public void deleteGiangViens(long maGiangVien) {
        restTemplate.delete(url + "/" + maGiangVien);
    }

    @Override
    public GiangVien findById(long maGiangVien) {
        GiangVien giangVien = restTemplate.getForObject(url + "/" + maGiangVien, GiangVien.class);
        return giangVien;
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

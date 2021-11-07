package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.LopHocPhan;
import com.example.n5_qlsv_admin.service.LopHocPhanService;
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
public class LopHocPhanServiceImpl implements LopHocPhanService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.lophocphan}")
    private String url;


    @Override
    public List<LopHocPhan> getAllLopHocPhans() {
        ResponseEntity<List<LopHocPhan>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LopHocPhan>>() {
                });
        List<LopHocPhan> lopHocPhanList = responseEntity.getBody();
        return lopHocPhanList;
    }

    @Override
    public List<LopHocPhan> getAllLopHocPhansByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<LopHocPhan>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LopHocPhan>>() {
                });
        List<LopHocPhan> lopHocPhanList = responseEntity.getBody();
        return lopHocPhanList;
    }

    @Override
    public void saveLopHocPhan(LopHocPhan lopHocPhan) {
        long maLopHocPhan = lopHocPhan.getMaLHP();
        if(maLopHocPhan == 0){
            if(lopHocPhan.getHocKy().getMaHK() == 0){
                lopHocPhan.setHocKy(null);
            }
            if(lopHocPhan.getHocPhan().getMaHocPhan() == ""){
                lopHocPhan.setHocPhan(null);
            }
            restTemplate.postForEntity(url, lopHocPhan, String.class);
        }else {
            if(lopHocPhan.getHocKy().getMaHK() == 0){
                lopHocPhan.setHocKy(null);
            }
            if(lopHocPhan.getHocPhan().getMaHocPhan() == ""){
                lopHocPhan.setHocPhan(null);
            }
            restTemplate.put(url + "/" + maLopHocPhan, lopHocPhan);
        }
    }

    @Override
    public void deleteLopHocPhan(long ma_lhp) {
        restTemplate.delete(url + "/" + ma_lhp);
    }

    @Override
    public LopHocPhan findById(long ma_lhp) {
        LopHocPhan lopHocPhan = restTemplate.getForObject(url + "/" + ma_lhp, LopHocPhan.class);
        return lopHocPhan;
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

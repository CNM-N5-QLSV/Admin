package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.LopHoc;
import com.example.n5_qlsv_admin.service.LopHocService;
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
public class LopHocServiceImpl implements LopHocService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.lophoc}")
    private String url;

    @Override
    public List<LopHoc> getAllLopHocs() {
        ResponseEntity<List<LopHoc>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LopHoc>>() {
                });
        List<LopHoc> lopHocList = responseEntity.getBody();
        return lopHocList;
    }

    @Override
    public List<LopHoc> getAllLopHocsByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<LopHoc>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LopHoc>>() {
                });
        List<LopHoc> lopHocList = responseEntity.getBody();
        return lopHocList;
    }

    @Override
    public void saveLopHoc(LopHoc lopHoc) {
        long maLopHoc = lopHoc.getMaLop();
        if (maLopHoc == 0){
            restTemplate.postForEntity(url, lopHoc, String.class);
        }else{
            restTemplate.put(url + "/" + maLopHoc, lopHoc);
        }
    }

    @Override
    public void deleteLopHocs(long maLopHoc) {
        restTemplate.delete(url + "/" + maLopHoc);
    }

    @Override
    public LopHoc findById(long maLopHoc) {
        LopHoc lopHoc = restTemplate.getForObject(url + "/" + maLopHoc, LopHoc.class);
        return lopHoc;
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

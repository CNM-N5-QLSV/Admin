package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.HocKy;
import com.example.n5_qlsv_admin.service.HocKyService;
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
public class HocKyServiceImpl implements HocKyService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.hocky}")
    private String url;

    @Override
    public List<HocKy> getAllHocKys() {
        ResponseEntity<List<HocKy>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocKy>>() {
                });
        List<HocKy> hocKyList = responseEntity.getBody();
        return hocKyList;
    }

    @Override
    public List<HocKy> getAllHocKysByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<HocKy>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocKy>>() {
                });
        List<HocKy> hocKyList = responseEntity.getBody();
        return hocKyList;
    }

    @Override
    public void saveHocKy(HocKy hocKy) {
        long id = hocKy.getMaHK();
        if(id == 0){
            restTemplate.postForEntity(url, hocKy, String.class);
        }else {
            restTemplate.put(url + "/" + id, hocKy);
        }
    }

    @Override
    public void deleteHocKy(long id) {
        restTemplate.delete(url + "/" + id);
    }

    @Override
    public HocKy findById(long id) {
        HocKy hocKy = restTemplate.getForObject(url + "/" + id, HocKy.class);
        return hocKy;
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

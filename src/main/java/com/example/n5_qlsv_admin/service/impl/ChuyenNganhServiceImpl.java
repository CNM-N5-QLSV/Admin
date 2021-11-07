package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.ChuyenNganh;
import com.example.n5_qlsv_admin.service.ChuyenNganhService;
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
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.chuyennganh}")
    private String url;

    @Override
    public List<ChuyenNganh> getAllChuyenNganhs() {
        ResponseEntity<List<ChuyenNganh>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ChuyenNganh>>() {
                });
        List<ChuyenNganh> chuyenNganhList = responseEntity.getBody();
        return chuyenNganhList;
    }

    @Override
    public List<ChuyenNganh> getAllChuyenNganhsByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<ChuyenNganh>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ChuyenNganh>>() {
                });
        List<ChuyenNganh> chuyenNganhList = responseEntity.getBody();
        return chuyenNganhList;
    }

    @Override
    public void saveChuyenNganh(ChuyenNganh chuyenNganh) {
        long id = chuyenNganh.getMaChuyenNganh();
        if(id == 0){
            restTemplate.postForEntity(url, chuyenNganh, String.class);
        }else {
            restTemplate.put(url + "/" + id, chuyenNganh);
        }
    }

    @Override
    public void deleteChuyenNganh(long id) {
        restTemplate.delete(url + "/" + id);
    }

    @Override
    public ChuyenNganh findById(long id) {
        ChuyenNganh chuyenNganh = restTemplate.getForObject(url + "/" + id, ChuyenNganh.class);
        return chuyenNganh;
    }

    @Override
    public List<ChuyenNganh> findAllChuyenNganhsByKhoa(Long maKhoa, int pageIndex, int pageSize) {
        ResponseEntity<List<ChuyenNganh>> responseEntity
                = restTemplate.exchange(url + "/khoa=" + maKhoa + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ChuyenNganh>>() {
                });
        List<ChuyenNganh> chuyenNganhList = responseEntity.getBody();
        return chuyenNganhList;
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

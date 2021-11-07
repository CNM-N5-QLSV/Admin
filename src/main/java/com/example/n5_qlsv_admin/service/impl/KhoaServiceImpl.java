package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.Khoa;
import com.example.n5_qlsv_admin.service.KhoaService;
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
public class KhoaServiceImpl implements KhoaService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.khoa}")
    private String url;

    @Override
    public List<Khoa> getAllKhoas() {
        ResponseEntity<List<Khoa>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Khoa>>() {
                });
        List<Khoa> khoaList = responseEntity.getBody();
        return khoaList;
    }

    @Override
    public List<Khoa> getAllKhoasByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<Khoa>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Khoa>>() {
                });
        List<Khoa> khoaList = responseEntity.getBody();
        return khoaList;
    }

    @Override
    public void saveKhoa(Khoa khoa) {
        long ma_khoa = khoa.getMaKhoa();
        if(ma_khoa == 0){
            restTemplate.postForEntity(url, khoa, String.class);
        }else {
            restTemplate.put(url + "/" + ma_khoa, khoa);
        }
    }

    @Override
    public void deleteKhoas(long ma_khoa) {
        restTemplate.delete(url + "/" + ma_khoa);
    }

    @Override
    public Khoa findById(long ma_khoa) {
        Khoa khoa = restTemplate.getForObject(url + "/" + ma_khoa, Khoa.class);
        return khoa;
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

package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.MonHoc;
import com.example.n5_qlsv_admin.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MonHocServiceImpl implements MonHocService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.url.monhoc}")
    private String url;

    @Override
    public List<MonHoc> getAllMonHoc() {
        ResponseEntity<List<MonHoc>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MonHoc>>() {
                });
        List<MonHoc> monHocList = responseEntity.getBody();
        return monHocList;
    }

    @Override
    public List<MonHoc> getAllMonHocByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<MonHoc>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MonHoc>>() {
                });
        List<MonHoc> monHocList = responseEntity.getBody();
        return monHocList;
    }

    @Override
    public void saveMonHoc(MonHoc monHoc) {
        long maMonHoc = monHoc.getMaMonHoc();
        if(maMonHoc == 0){
            restTemplate.postForEntity(url, monHoc, String.class);
        }else
        {
            restTemplate.put(url + "/" + maMonHoc, monHoc);
        }
    }

    @Override
    public void deleteMonHocs(long maMonHoc) {
        restTemplate.delete(url + "/" + maMonHoc);
    }

    @Override
    public MonHoc findById(long maMonHoc) {
        MonHoc monHoc = restTemplate.getForObject(url + "/" + maMonHoc, MonHoc.class);
        return monHoc;
    }

    @Override
    public List<MonHoc> getAllMonHocNotInHocPhan() {
        ResponseEntity<List<MonHoc>> responseEntity
                = restTemplate.exchange(url + "/notinhocphan", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MonHoc>>() {
                });
        List<MonHoc> monHocList = responseEntity.getBody();
        return monHocList;
    }
}
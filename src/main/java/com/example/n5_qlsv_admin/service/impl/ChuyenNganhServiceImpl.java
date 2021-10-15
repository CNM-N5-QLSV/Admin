package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.ChuyenNganh;
import com.example.n5_qlsv_admin.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}

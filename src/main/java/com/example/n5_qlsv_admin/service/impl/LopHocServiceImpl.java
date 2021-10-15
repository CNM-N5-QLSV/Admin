package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.LopHoc;
import com.example.n5_qlsv_admin.service.LopHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}

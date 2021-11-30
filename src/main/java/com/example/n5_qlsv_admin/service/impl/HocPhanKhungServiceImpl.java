package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.HocPhanKhung;
import com.example.n5_qlsv_admin.service.HocPhanKhungService;
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
public class HocPhanKhungServiceImpl implements HocPhanKhungService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.hocphankhung}")
    private String url;

    @Override
    public List<HocPhanKhung> getAllHPK() {
        return null;
    }

    @Override
    public List<HocPhanKhung> getAllHPKByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<HocPhanKhung>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocPhanKhung>>() {
                });
        List<HocPhanKhung> hocPhanKhungList = responseEntity.getBody();
        return hocPhanKhungList;
    }

    @Override
    public void saveHPK(HocPhanKhung hpk) {
        long id = hpk.getMaHPK();
        if(id == 0){
            restTemplate.postForEntity(url, hpk, String.class);
        }else {
            restTemplate.put(url + "/" + id, hpk);
        }
    }

    @Override
    public void deleteHPK(long id) {
        restTemplate.delete(url + "/" + id);
    }

    @Override
    public HocPhanKhung findHPKById(long id) {
        HocPhanKhung hocPhanKhung = restTemplate.getForObject(url + "/" + id, HocPhanKhung.class);
        return hocPhanKhung;
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

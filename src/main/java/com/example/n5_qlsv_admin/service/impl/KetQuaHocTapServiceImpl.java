package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.KetQuaHocTap;
import com.example.n5_qlsv_admin.model.Khoa;
import com.example.n5_qlsv_admin.service.KetQuaHocTapService;
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
import java.util.Set;

@Service
public class KetQuaHocTapServiceImpl implements KetQuaHocTapService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.ketquahoctap}")
    private String url;

    @Override
    public List<KetQuaHocTap> findKQHTByMaSV(String maSV, int pageIndex, int pageSize) {
        ResponseEntity<List<KetQuaHocTap>> responseEntity
                = restTemplate.exchange(url + "/maSV=" + maSV + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<KetQuaHocTap>>() {
                });
        List<KetQuaHocTap> body = responseEntity.getBody();
        return body;
    }

    @Override
    public Set<Long> findMaHKByMaSV(String maSV) {
        return null;
    }

    @Override
    public void saveKetQuaHT(KetQuaHocTap ketQuaHocTap) {
        long kqht = ketQuaHocTap.getMaKQHT();
        if (kqht == 0) {
            restTemplate.postForEntity(url, ketQuaHocTap, String.class);
        } else {
            restTemplate.put(url + "/" + kqht, ketQuaHocTap);
        }
    }

    @Override
    public void deleteKQHT(Long maKQHT) {
        restTemplate.delete(url + "/" + maKQHT);
    }

    @Override
    public KetQuaHocTap findKQHTByMaSVAndMaLHP(String maSV, Long maLHP) {
        return null;
    }

    @Override
    public KetQuaHocTap findById(long ma_kqht) {
        return restTemplate.getForObject(url + "/" + ma_kqht, KetQuaHocTap.class);
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

    @Override
    public List<KetQuaHocTap> getAllKQHTsByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<KetQuaHocTap>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<KetQuaHocTap>>() {
                });
        List<KetQuaHocTap> ketQuaHocTapList = responseEntity.getBody();
        return ketQuaHocTapList;
    }
}

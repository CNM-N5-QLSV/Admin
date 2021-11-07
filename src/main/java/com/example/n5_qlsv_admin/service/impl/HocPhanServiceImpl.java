package com.example.n5_qlsv_admin.service.impl;

import com.example.n5_qlsv_admin.model.HocPhan;
import com.example.n5_qlsv_admin.service.HocPhanService;
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
public class HocPhanServiceImpl implements HocPhanService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.hocphan}")
    private String url;
    @Override
    public List<HocPhan> getAllHocPhans() {
        ResponseEntity<List<HocPhan>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocPhan>>() {
                });
        List<HocPhan> hocPhanList = responseEntity.getBody();
        return hocPhanList;
    }

    @Override
    public List<HocPhan> getAllHocPhansByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<HocPhan>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocPhan>>() {
                });
        List<HocPhan> hocPhanList = responseEntity.getBody();
        return hocPhanList;
    }

    @Override
    public void saveHocPhan(HocPhan hocPhan) {
        String maHocPhan = hocPhan.getMaHocPhan();
        if(maHocPhan == ""){
            if(hocPhan.getMonHoc().getMaMonHoc() == 0){
                hocPhan.setMonHoc(null);
            }
            if(hocPhan.getChuyenNganh().getMaChuyenNganh() == 0){
                hocPhan.setChuyenNganh(null);
            }
            restTemplate.postForEntity(url, hocPhan, String.class);
        }else {
            if(hocPhan.getMonHoc().getMaMonHoc() == 0){
                hocPhan.setMonHoc(null);
            }
            if(hocPhan.getChuyenNganh().getMaChuyenNganh() == 0){
                hocPhan.setChuyenNganh(null);
            }
            restTemplate.put(url + "/" + maHocPhan, hocPhan);
        }
    }

    @Override
    public void deleteHocPhans(String maHocPhan) {
        restTemplate.delete(url + "/" + maHocPhan);
    }

    @Override
    public HocPhan findById(String maHocPhan) {
        HocPhan hocPhan = restTemplate.getForObject(url + "/" + maHocPhan, HocPhan.class);
        return hocPhan;
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

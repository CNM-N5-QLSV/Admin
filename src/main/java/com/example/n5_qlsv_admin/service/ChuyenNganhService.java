package com.example.n5_qlsv_admin.service;


import com.example.n5_qlsv_admin.model.ChuyenNganh;

import java.util.List;

public interface ChuyenNganhService {

    List<ChuyenNganh> getAllChuyenNganhs();

    List<ChuyenNganh> getAllChuyenNganhsByPageAndSize(int pageIndex, int pageSize);

    void saveChuyenNganh(ChuyenNganh chuyenNganh);

    void deleteChuyenNganh(long id);

    ChuyenNganh findById(long id);

}

package com.example.n5_qlsv_admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChuyenNganh {

    private long maChuyenNganh;

    private String tenChuyenNganh;

    private int soTC;

    private Khoa khoa;

    private List<HocPhan> hocPhanList;

    private List<SinhVien> sinhVienList;
}

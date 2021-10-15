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
public class LopHoc {

    private long maLop;

    private String tenLop;

    private List<SinhVien> sinhVienList;
}

package com.example.n5_qlsv_admin.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonHoc {

    private long maMonHoc;

    private String tenMonHoc;

    private String moTa;

    private Khoa khoa;

    private List<HocPhan> hocPhanList;
}

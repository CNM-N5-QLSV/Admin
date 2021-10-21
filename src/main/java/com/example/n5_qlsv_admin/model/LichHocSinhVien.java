package com.example.n5_qlsv_admin.model;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LichHocSinhVien {

    private long maLHSV;

    private ChiTietLopHocPhan chiTietLopHocPhan;

    private SinhVien sinhVien;
}

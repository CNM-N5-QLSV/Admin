/* sự kiện create khoa */
$(document).ready(function () {
    $('#createKhoaBtn').on('click', function (event) {
        event.preventDefault();

        $('.myForm #maKhoa').val('0');
        $('.myForm #tenKhoa').val('');
        $('.myForm #moTa').val('');

        $('.myForm #myModal').modal();
    });
});
/*Sự kiện update Khoa*/
$(document).ready(function () {
    $('.btnUpdateKhoa').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (khoa, status) {
            $('.myForm #maKhoa').val(khoa.maKhoa);
            $('.myForm #tenKhoa').val(khoa.tenKhoa);
            $('.myForm #moTa').val(khoa.moTa);
        });
        $('.myForm #myModal').modal();
    });
});
/*Sự kiện Create Lớp học*/
$(document).ready(function () {
    $('#createLopHocBtn').on('click', function (event) {
        event.preventDefault();

        $('.myForm #maLop').val('0');
        $('.myForm #tenLop').val('');

        $('.myForm #myModal').modal();
    });
});

/*Sự kiện Update Lớp học*/
$(document).ready(function () {
    $('.btnUpdateLopHoc').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (lophoc, status) {
            $('.myForm #maLop').val(lophoc.maLop);
            $('.myForm #tenLop').val(lophoc.tenLop);
        });

        $('.myForm #myModal').modal();
    });
});

/*Sự kiện Create học phần*/
$(document).ready(function () {
    $('#createHocPhanBtn').on('click', function (event) {
        event.preventDefault();

        $('.myForm #maHocPhan').val('');
        $('.myForm #monHoc').val(0);
        $('.myForm #soTCLT').val(0);
        $('.myForm #soTCTH').val(0);
        $('.myForm #hocPhanBatBuoc').prop("checked", false);
        $('.myForm #tenChuyenNganh').val(0);

        $('.myForm #myModal').modal();
    });
});

/*Sự kiện Update học phần*/
$(document).ready(function () {
    $('.btnUpdateHocPhan').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (hocphan, status) {
            $('.myForm #maHocPhan').val(hocphan.maHocPhan);
            $('.myForm #soTCLT').val(hocphan.soTCLT);
            $('.myForm #soTCTH').val(hocphan.soTCTH);

            var hpbb = hocphan.hocPhanBatBuoc;
            if (hpbb === true){
                $('.myForm #hocPhanBatBuoc').prop("checked", true);
            }else {
                $('.myForm #hocPhanBatBuoc').prop("checked", false);
            }

            if(hocphan.monHoc === null){
                $('.myForm #monHoc').val(0);
            }else {
                $('.myForm #monHoc').val(hocphan.monHoc.maMonHoc);
            }

            if(hocphan.chuyenNganh === null){
                $('.myForm #tenChuyenNganh').val(0);
            }else {
                $('.myForm #tenChuyenNganh').val(hocphan.chuyenNganh.maChuyenNganh);
            }
        });

        $('.myForm #myModal').modal();
    });
});

/*Sự kiện Update kết quả học tập*/
$(document).ready(function () {
    $('.btnUpdateKQHT').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (kqht, status) {
            $('.myForm #maKQHT').val(kqht.maKQHT);
            $('.myForm #maKQHT_LHP').val(kqht.lopHocPhan.maLHP);
            $('.myForm #maKQHT_SV').val(kqht.sinhVien.maSV);

            $('.myForm #dtk1').val(kqht.diemTK1);
            $('.myForm #dtk2').val(kqht.diemTK2);
            $('.myForm #dtk3').val(kqht.diemTK3);

            $('.myForm #dth1').val(kqht.diemTH1);
            $('.myForm #dth2').val(kqht.diemTH2);

            $('.myForm #dgk').val(kqht.diemGK);
            $('.myForm #dck').val(kqht.diemCK);
        });

        $('.myForm #myModal').modal();
    });
});

/*Sự kiện Create học phần khung*/
$(document).ready(function () {
    $('.btnCreateHPK').on('click', function (event) {
        event.preventDefault();

        $('.myForm #hpkId').val(0);
        $('.myForm #hpk_tthk').val(1);
        $('.myForm #hpk_stlt').val(0);
        $('.myForm #hpk_stth').val(0);

        $('.myForm #myModal').modal();
    });
});

/*Sự kiện Update học phần khung*/
$(document).ready(function () {
    $('.btnHPK').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (hpk, status) {

            $('.myForm #hpkId').val(hpk.maHPK);

            $('.myForm #hpk_trangthai').val(hpk.trangThai);
            $('.myForm #hpk_tthk').val(hpk.thuTuHocKy);

            $('.myForm #hpk_hp').val(hpk.hocPhan.maHocPhan);

            $('.myForm #hpk_stlt').val(hpk.soTietLT);
            $('.myForm #hpk_stth').val(hpk.soTietTH);
        });

        $('.myForm #myModal').modal();
    });
});

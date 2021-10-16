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

        $('.myForm #maHocPhan').val('0');
        $('.myForm #monHoc').val('');
        $('.myForm #soTCLT').val('');
        $('.myForm #soTCTH').val('');
        $('.myForm #hocPhanBatBuoc').val('');
        $('.myForm #tenChuyenNganh').val('');

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
            $('.myForm #monHoc').val(hocphan.monHoc.tenMonHoc);
            $('.myForm #soTCLT').val(hocphan.soTCLT);
            $('.myForm #soTCTH').val(hocphan.soTCTH);
            $('.myForm #hocPhanBatBuoc').val(hocphan.hocPhanBatBuoc);
            $('.myForm #tenChuyenNganh').val(hocphan.chuyenNganh.maChuyenNganh);
        });

        $('.myForm #myModal').modal();
    });
});

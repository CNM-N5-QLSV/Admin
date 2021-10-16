$(document).ready(function () {
    $('.tbl .uBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (sv, status) {
            $('.myForm #myId').val(sv.maSV);
            $('.myForm #tensv').val(sv.tenSV);
            $('.myForm #cmnd').val(sv.soCMND);

            var gt = sv.gioiTinh;
            if (gt === true) {
                $('#male').prop("checked", true);
            } else if (gt === false) {
                $('#female').prop("checked", true);
            }

            $('.myForm #bdt').val(sv.bacDaoTao);
            $('.myForm #phone').val(sv.soDienThoai);
            $('.myForm #chuyenN').val(sv.chuyenNganh.maChuyenNganh);
            $('.myForm #classRoom').val(sv.lopHoc.maLop);
            $('.myForm #department').val(sv.khoa.maKhoa);
            $('.myForm #myEmail').val(sv.email);
            $('.myForm #address').val(sv.diaChi);
            $('.myForm #birthDate').val(sv.ngaySinh.split('T')[0]);

            $('.myForm #nvt').val(sv.ngayVaoTruong.split('T')[0]);
            $('.myForm #myPass').val(sv.password);
            $('.myForm #myRole').val(sv.roleName);
        });

        $('.myForm #myModal').modal();
    });
});

$(document).ready(function () {
    $('.createBtn').on('click', function (event) {
        event.preventDefault();

        $('.myForm #myId').val('0');
        $('.myForm #tensv').val('');
        $('.myForm #cmnd').val('');

        $('#male').prop("checked", false);

        $('#female').prop("checked", false);

        $('.myForm #bdt').val('');
        $('.myForm #phone').val('');
        $('.myForm #chuyenN').val('');
        $('.myForm #classRoom').val('');
        $('.myForm #department').val('');
        $('.myForm #myEmail').val('');
        $('.myForm #address').val('');
        $('.myForm #birthDate').val('');

        var d = new Date();

        var month = d.getMonth() + 1;
        var day = d.getDate();

        var output = d.getFullYear() + '-' +
            (month < 10 ? '0' : '') + month + '-' +
            (day < 10 ? '0' : '') + day;

        $('.myForm #nvt').val(output);

        $('.myForm #myModal').modal();
    });
});

//Môn học
$(document).ready(function () {
    $('.createBtnMH').on('click', function (event) {
        event.preventDefault();

        $('.myForm #myIdMH').val('0');
        $('.myForm #tenmonhoc').val('');
        $('.myForm #mota').val('');

        $('.myForm #myModal').modal();
    });
});

$(document).ready(function () {
    $('.uBtnMH').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (mh, status) {
            $('.myForm #myIdMH').val(mh.maMonHoc);
            $('.myForm #tenmonhoc').val(mh.tenMonHoc);
            $('.myForm #mota').val(mh.moTa);

        });

        $('.myForm #myModal').modal();
    });
});

//Giảng viên
$(document).ready(function () {
    $('.createBtn').on('click', function (event) {
        event.preventDefault();

        $('.myForm #myIdGV').val('0');
        $('.myForm #tengv').val('');

        $('#male').prop("checked", false);

        $('#female').prop("checked", false);

        $('.myForm #ns').val('');
        $('.myForm #email').val('');
        $('.myForm #department').val('');

        var d = new Date();

        var month = d.getMonth()+1;
        var day = d.getDate();

        var output = d.getFullYear() + '-' +
            (month<10 ? '0' : '') + month + '-' +
            (day<10 ? '0' : '') + day;

        $('.myForm #myModal').modal();
    });
});

$(document).ready(function () {
    $('.uBtnGV').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (gv, status) {
            $('.myForm #myIdGV').val(gv.maGV);
            $('.myForm #tengv').val(gv.tenGV);

            var gt = gv.gioiTinh;
            if (gt === true) {
                $('#male').prop("checked", true);
            } else if (gt === false) {
                $('#female').prop("checked", true);
            }

            $('.myForm #ns').val(gv.ngaySinh.split('T')[0]);
            $('.myForm #email').val(gv.email);
            $('.myForm #department').val(gv.khoa.maKhoa);

        });

        $('.myForm #myModal').modal();
    });
});

//Update hoc ky
$(document).ready(function () {
    $('.btnUpdateHocKy').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (hk, status) {
            $('.myForm #hocKyId').val(hk.maHK);
            $('.myForm #nbd').val(hk.namBatDau);
            $('.myForm #nkt').val(hk.namKetThuc);
            $('.myForm #tthk').val(hk.thuTuHocKy);
            $('.myForm #mota').val(hk.moTa);
        });

        $('.myForm #myModal').modal();
    });
});

//Create hoc ky
$(document).ready(function () {
    $('.btnCreateHocKy').on('click', function (event) {
        event.preventDefault();

        $('.myForm #hocKyId').val(0);
        $('.myForm #nbd').val('');
        $('.myForm #nkt').val('');
        $('.myForm #tthk').val('');
        $('.myForm #mota').val('');

        $('.myForm #myModal').modal();
    });
});

//Update chuyen nganh
$(document).ready(function () {
    $('.btnUpdateChuyenNganh').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (cn, status) {
            $('.myForm #chuyenNganhId').val(cn.maChuyenNganh);
            $('.myForm #tenCN').val(cn.tenChuyenNganh);
            $('.myForm #khoaId').val(cn.khoa.maKhoa);
        });

        $('.myForm #myModal').modal();
    });
});

//Create chuyen nganh
$(document).ready(function () {
    $('.btnCreateChuyenNganh').on('click', function (event) {
        event.preventDefault();

        $('.myForm #chuyenNganhId').val(0);
        $('.myForm #tenCN').val('');
        $('.myForm #khoaId').val('');

        $('.myForm #myModal').modal();
    });
});
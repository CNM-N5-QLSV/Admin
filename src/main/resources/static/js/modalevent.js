//Cap nhat sinh vien
$(document).ready(function () {
    $('.tbl .uBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (sv, status) {
            $('.myForm #myId').val(sv.maSV);
            $('.myForm #tensv').val(sv.tenSV);
            $('.myForm #cmnd').val(sv.soCMND);
            $('.myForm #nvt').val(sv.ngayVaoTruong.split('T')[0]);
            $('.myForm #myPass').val(sv.password);
            $('.myForm #myRole').val(sv.roleName);

            var gt = sv.gioiTinh;
            if (gt === true) {
                $('#male').prop("checked", true);
            } else if (gt === false) {
                $('#female').prop("checked", true);
            }

            $('.myForm #bdt').val(sv.bacDaoTao);
            $('.myForm #phone').val(sv.soDienThoai);
            $('.myForm #myEmail').val(sv.email);
            $('.myForm #address').val(sv.diaChi);

            if(sv.ngaySinh != null){
                $('.myForm #birthDate').val(sv.ngaySinh.split('T')[0]);
            }else{
                $('.myForm #birthDate').val("");
            }

            if(sv.chuyenNganh === null){
                $('.myForm #chuyenN').val(0);
            }else {
                $('.myForm #chuyenN').val(sv.chuyenNganh.maChuyenNganh);
            }

            if(sv.lopHoc === null){
                $('.myForm #classRoom').val(0);
            }else {
                $('.myForm #classRoom').val(sv.lopHoc.maLop);
            }

            if(sv.khoa === null){
                $('.myForm #myKhoa').val(0);
            }else {
                $('.myForm #myKhoa').val(sv.khoa.maKhoa);
            }
        });

        $('.myForm #myModal').modal();
    });
});

//Them sinh vien
$(document).ready(function () {
    $('.createBtn').on('click', function (event) {
        event.preventDefault();

        $('.myForm #myId').val('');
        $('.myForm #tensv').val('');
        $('.myForm #cmnd').val('');

        $('#male').prop("checked", false);

        $('#female').prop("checked", false);

        $('.myForm #bdt').val('');
        $('.myForm #phone').val('');
        $('.myForm #chuyenN').val(0);
        $('.myForm #classRoom').val(0);
        $('.myForm #myKhoa').val(0);
        $('.myForm #myEmail').val('');
        $('.myForm #address').val('');
        $('.myForm #birthDate').val('');

        var d = new Date();

        var month = d.getMonth()+1;
        var day = d.getDate();

        var output = d.getFullYear() + '-' +
            (month<10 ? '0' : '') + month + '-' +
            (day<10 ? '0' : '') + day;

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
        $('.myForm #myKhoa').val(0);

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

            if(mh.khoa === null){
                $('.myForm #myKhoa').val(0);
            }else {
                $('.myForm #myKhoa').val(mh.khoa.maKhoa);
            }

        });

        $('.myForm #myModal').modal();
    });
});

//Thêm Giảng viên
$(document).ready(function () {
    $('.createBtn').on('click', function (event) {
        event.preventDefault();

        $('.myForm #myIdGV').val('0');
        $('.myForm #tengv').val('');

        $('#male').prop("checked", false);

        $('#female').prop("checked", false);

        $('.myForm #ns').val('');
        $('.myForm #email').val('');
        $('.myForm #khoaGiangVien').val(0);

        var d = new Date();

        var month = d.getMonth()+1;
        var day = d.getDate();

        var output = d.getFullYear() + '-' +
            (month<10 ? '0' : '') + month + '-' +
            (day<10 ? '0' : '') + day;

        $('.myForm #myModal').modal();
    });
});

//Cập nhật giảng viên
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

            if(gv.khoa === null){
                $('.myForm #khoaGiangVien').val(0);
            }else {
                $('.myForm #khoaGiangVien').val(gv.khoa.maKhoa);
            }

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

//Thêm Lớp học phần
$(document).ready(function () {
    $('.createBtnLHP').on('click', function (event) {
        event.preventDefault();

        $('.myForm #myIdLHP').val(0);
        $('.myForm #tenviettat').val('');
        $('.myForm #tenlophocphan').val('');
        $('.myForm #soluongdangkyhientai').val(0);
        $('.myForm #soluongdangkytoida').val(40);
        $('.myForm #trangthai').val('');
        $('.myForm #hocky').val(0);
        $('.myForm #hocphan').val("");
        $('.myForm #mota').val('');

        $('.myForm #myModal').modal();
    });
});

//Cập nhật lớp học phần
$(document).ready(function () {
    $('.uBtnLHP').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (lhp, status) {
            $('.myForm #myIdLHP').val(lhp.maLHP);
            $('.myForm #tenviettat').val(lhp.tenVietTat);
            $('.myForm #tenlophocphan').val(lhp.tenLHP);
            $('.myForm #soluongdangkyhientai').val(lhp.soLuongDangKyHienTai);
            $('.myForm #soluongdangkytoida').val(lhp.soLuongDangKyToiDa);
            $('.myForm #trangthai').val(lhp.trangThai);
            $('.myForm #mota').val(lhp.moTa);

            if(lhp.hocKy === null){
                $('.myForm #hocky').val(0);
            }else {
                $('.myForm #hocky').val(lhp.hocKy.maHK);
            }

            if(lhp.hocPhan === null){
                $('.myForm #hocphan').val("");
            }else {
                $('.myForm #hocphan').val(lhp.hocPhan.maHocPhan);
            }

        });

        $('.myForm #myModal').modal();
    });
});

//Create chi tiet lop hoc phan
$(document).ready(function () {
    $('.createBtnCTLHP').on('click', function (event) {
        event.preventDefault();

        $('.myForm #myIdCTLHP').val(0);
        $('.myForm #idlhp').val(0);
        $('.myForm #tiethoc').val('');
        $('.myForm #coso').val('');
        $('.myForm #daynha').val('');
        $('.myForm #phong').val('');
        $('.myForm #ngaybatdau').val('');
        $('.myForm #ngayketthuc').val('');
        $('.myForm #gv').val(0);
        $('.myForm #mt').val('');
        $('.myForm #nhomthuchanh').val(0);

        $('.myForm #myModal').modal();
    });
});

//Update chi tiet lop hoc phan
$(document).ready(function () {
    $('.uBtnCTLHP').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (ctlhp, status) {
            $('.myForm #myIdCTLHP').val(ctlhp.maCTLHP);
            $('.myForm #idlhp').val(ctlhp.lopHocPhan.maLHP);
            $('.myForm #tiethoc').val(ctlhp.tietHoc);
            $('.myForm #coso').val(ctlhp.coSo);
            $('.myForm #daynha').val(ctlhp.dayNha);
            $('.myForm #phong').val(ctlhp.phong);
            $('.myForm #ngaybatdau').val(ctlhp.ngayBatDau.split('T')[0]);
            $('.myForm #ngayketthuc').val(ctlhp.ngayKetThuc.split('T')[0]);
            $('.myForm #mt').val(ctlhp.moTa);
            $('.myForm #nhomthuchanh').val(ctlhp.nhomTH);

            if(ctlhp.giangVien === null){
                $('.myForm #gv').val(0);
            }else {
                $('.myForm #gv').val(ctlhp.giangVien.maGV);
            }
        });

        $('.myForm #myModal').modal();
    });
});

// Import File
$(document).ready(function () {
    $('.btnImportFile').on('click', function (event) {
        event.preventDefault();
        $('.myForm1 #myModalImportFile').modal();
    });
});
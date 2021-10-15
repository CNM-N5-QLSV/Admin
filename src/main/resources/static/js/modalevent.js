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

        var month = d.getMonth()+1;
        var day = d.getDate();

        var output = d.getFullYear() + '-' +
            (month<10 ? '0' : '') + month + '-' +
            (day<10 ? '0' : '') + day;

        $('.myForm #nvt').val(output);

        $('.myForm #myModal').modal();
    });
});
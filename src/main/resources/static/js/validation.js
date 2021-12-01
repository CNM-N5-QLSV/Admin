// kiểm tra họ tên
function kiemtraten() {
    var regten = /^[A-Z]+[a-z]*/;
    var ten = document.getElementById("tensv").value;
    if (regten.test(ten)) {
        document.getElementById('s1').innerHTML = "";
    } else {
        document.getElementById('s1').innerHTML = "Tên sai !!! Dùng chữ hoa đầu";
    }
    if (ten) {
    } else {
        document.getElementById('s1').innerHTML = "Bắt buộc nhập !!!";
    }
}

// kiểm tra CMND
function kiemtraCMND() {
    var regcmnd = /^([0-9]{12})$/;
    var cmnd = document.getElementById("cmnd").value;
    if (regcmnd.test(cmnd)) {
        document.getElementById('s2').innerHTML = "";
    } else {
        document.getElementById('s2').innerHTML = "CMND không quá 12 số !!!";
    }
    if (cmnd) {
    } else {
        document.getElementById('s2').innerHTML = "Bắt buộc nhập !!!";
    }
}

// kiểm tra số điện thoại
function kiemtrasodienthoai() {
    var regsdt = /^((09|03|07|08|05)+([0-9]{8}))$/;
    var sdt = document.getElementById("phone").value;
    if (regsdt.test(sdt)) {
        document.getElementById('s3').innerHTML = "";
    } else {
        document.getElementById('s3').innerHTML = "Só điện thoại không đúng định dạng !!!";
    }
    if (sdt) {
    } else {
        document.getElementById('s3').innerHTML = "Bắt buộc nhập !!!";
    }
}

// kiểm tra email
function kiemtraemail() {
    var regemail = /^[A-Za-z0-9]{3,}([_.-]?\w)*(@gmail.com)$/;
    var email = document.getElementById("myEmail").value;
    if (regemail.test(email)) {
        document.getElementById('s4').innerHTML = "";
    } else {
        document.getElementById('s4').innerHTML = "Email không đúng định dạng !!!";
    }
    if (email) {
    } else {
        document.getElementById('s4').innerHTML = "Bắt buộc nhập !!!";
    }
}

// kiểm tra địa chỉ
function kiemtradiachi() {
    var diachi = document.getElementById("address").value;
    if (diachi) {
        document.getElementById('s5').innerHTML = "";
    } else {
        document.getElementById('s5').innerHTML = "Bắt buộc nhập !!!";
    }
}




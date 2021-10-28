(function ($) {
	"use strict";
	/*----------------------------------------*/
	/*  1.  Bar Chart
	/*----------------------------------------*/

	var ctx = document.getElementById("barchart1");

	var slSV = document.getElementById("slSV");
	var slHK = document.getElementById("slHK");
	var slHP = document.getElementById("slHP");
	var slLopHP = document.getElementById("slLopHP");
	var slMH = document.getElementById("slMH");
	var slK = document.getElementById("slK");
	var slCN = document.getElementById("slCN");

	var barchart1 = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ["Sinh viên", "Học kỳ", "Học phần", "Lớp học phần", "Môn học", "Khoa", "Chuyên ngành"],
			datasets: [{
				label: 'Dữ liệu',
				data: [slSV.textContent, slHK.textContent, slHP.textContent, slLopHP.textContent, slMH.textContent, slK.textContent, slCN.textContent],
				borderWidth: 1,
				yAxisID: "y-axis-1",
				backgroundColor: [
					'rgba(255, 99, 132, 0.2)',
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)',
					'rgba(153, 102, 255, 0.2)',
					'rgba(255, 159, 64, 0.2)',
					'rgba(238, 130, 238, 0.2)'
				],
				borderColor: [
					'rgba(255,99,132,1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(75, 192, 192, 1)',
					'rgba(153, 102, 255, 1)',
					'rgba(255, 159, 64, 1)',
					'rgba(238, 130, 238, 1)'
				],
			},]
		},
		options: {
			responsive: true,
			title:{
				display:true,
				text:"Biểu đồ cột đa nhiệm"
			},
			tooltips: {
				mode: 'index',
				intersect: true
			},
			scales: {
				yAxes: [{
					type: "linear",
					display: true,
					position: "left",
					id: "y-axis-1",
				}, {
					type: "linear",
					display: true,
					position: "right",
					id: "y-axis-2",
					gridLines: {
						drawOnChartArea: false
					}
				}],
			}
		}
	});


})(jQuery);
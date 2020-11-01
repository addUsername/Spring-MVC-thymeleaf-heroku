
window.onload = function () {

	console.log(weekChart);

	var maxTempPoints = [];
	var humidityPoints = [];
	var rainPoints = [];

	for(i=0; i<weekChart.length; i++){
		
		//Check out this "+" that parses to number (long maybe)
		var date = new Date(+weekChart[i].fint);
		//idk why, mmaybe bc java.util.Date is deprecated but..
		date.setFullYear( date.getFullYear() - 1900 );
		
		humidityPoints.push({x: date, y:weekChart[i].hr});
		rainPoints.push({x: date, y:weekChart[i].prec});
		maxTempPoints.push({x: date, y:[weekChart[i].tamax,weekChart[i].tamin] });	
	}

	var chart = new CanvasJS.Chart("chartContainer", {
		animationEnabled: true,
		zoomEnabled: true,
		theme: "light2",
		title: {
			text: "Histórico 24h"
		},
		axisX:[ {
			valueFormatString: "HH 'h'" //https://canvasjs.com/docs/charts/chart-options/axisx/valueformatstring/
		
		}],
		axisY: [
			{
				suffix: "ºC",
				//title: "Temperatura",
				titleFontColor: "red",
				labelFontColor: "red"
			},
			{
				suffix: "%",
				//title: "Humedad",
				titleFontColor: "green",
				labelFontColor: "green"
			},
			{
				//title: "Preciptaciones",
				suffix: " l/m2",
				titleFontColor: "blue",
				labelFontColor: "blue"
			}
		],
		toolTip: {
			shared: true
		},
		legend: {
			cursor: "pointer",
			itemclick: toggleDataSeries
		},
		data: [
		{
			type: "rangeColumn",
			name: "Variación T",
			showInLegend: true,
			xValueFormatString: "HH'h 'm' min 's' s.'",
			yValueFormatString: "#.# ºC",
			color:"red",
			axisYIndex: 0, //defaults to 0
			dataPoints: maxTempPoints
		},
		{
			type: "line",
			name: "Humedad ",
			showInLegend: true,
			yValueFormatString: "#.#",
			axisYIndex: 1,
			color: "green",
			dataPoints: humidityPoints
		},
		{
			type: "area",
			name: "Precipitaciones",
			markerBorderColor: "white",
			color:"lightblue",
			markerBorderThickness: 2,
			showInLegend: true,
			yValueFormatString: "#.# l/m3",
			axisYIndex: 2,
			dataPoints: rainPoints
		}]
	});	
	chart.render();

	// ++++++++++++++++++++WEEK CHART+++++++++++++++++++++++++++
	//plots
	maxTempPoints = [];
	var medTempPoints = [];
	var solPoints = [];
	rainPoints = []; //OK

	console.log(realWeekChart);

	for(i=0; i<realWeekChart.length; i++){
		
		//Check out this "+" that parses to number (long maybe)
		var date = new Date(realWeekChart[i].fecha);
		date.setMonth(date.getMonth() + 1);
		//idk why, maybe bc java.util.Date is deprecated but..
		//Not neccesary date.setFullYear( date.getFullYear() - 1900 ); 
		console.log(realWeekChart[i]);
		solPoints.push({x: date, y:realWeekChart[i].sol});
		rainPoints.push({x: date, y:realWeekChart[i].prec});
		maxTempPoints.push({x: date, y:[realWeekChart[i].tmax,realWeekChart[i].tmin] });
		console.log({y:[realWeekChart[i].tmax,realWeekChart[i].tmin] });
	}

	var chart2 = new CanvasJS.Chart("weeklyChartContainer", {
		animationEnabled: true,
		zoomEnabled: true,
		theme: "light",
		title: {
			text: "Ult. semana"
		},
		axisX:[ {
			valueFormatString: "DD DDDD"    
		}],
		axisY: [
			{
				suffix: "ºC",
				//title: "Temperatura",
				titleFontColor: "red",
				labelFontColor: "red"
			},
			{
				suffix: "h",
				//title: "sol",
				titleFontColor: "orange",
				labelFontColor: "orange"
			},
			{
				//title: "Preciptaciones",
				suffix: " l/m2",
				titleFontColor: "blue",
				labelFontColor: "blue"
			}
		],
		toolTip: {
			shared: true
		},
		legend: {
			cursor: "pointer",
			itemclick: toggleDataSeries
		},
		data: [
		{
			type: "rangeColumn",
			name: "Variación T",
			showInLegend: true,
			xValueFormatString: "DD-MM-YYYY",
			yValueFormatString: "#.# ºC",
			color:"red",
			axisYIndex: 0, //defaults to 0
			dataPoints: maxTempPoints
		},
		{
			type: "line",
			name: "Horas sol",
			showInLegend: true,
			yValueFormatString: "#.#",
			axisYIndex: 1,
			color: "orange",
			dataPoints: solPoints
		},
		{
			type: "area",
			name: "Precipitaciones",
			markerBorderColor: "white",
			color:"lightblue",
			markerBorderThickness: 2,
			showInLegend: true,
			yValueFormatString: "#.# l/m3",
			axisYIndex: 2,
			dataPoints: rainPoints
		}]
	});
	chart2.render();

	function toggleDataSeries(e) {
		if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
			e.dataSeries.visible = false;
		} else {
			e.dataSeries.visible = true;
		}
		e.chart.render();
	}

};
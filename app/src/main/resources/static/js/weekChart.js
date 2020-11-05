
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
	var chart2;
	console.log(realWeekChart);
	
	if(realWeekChart == null){
		alert("Algunas idemas devuelven este json.. \n prueba con capitales. Ej: Madrid-Aeropuerto");
		//Some idemas are not normalized.. if null this shows and img, amazing eh
		var imgUrl = "https://i.ibb.co/R46yQ3R/Aemet-error.jpg";
		var images = [];
		var chart = new CanvasJS.Chart("weeklyChartContainer", {
		  axisX:{
		    gridThickness: 0,
		    tickLength: 0,
		    lineThickness: 0,
		    labelFormatter: function(){
		      return " ";
		    }
		  },
		  axisY:{
		    gridThickness: 0,
		    tickLength: 0,
		    lineThickness: 0,
		    labelFormatter: function(){
		      return " ";
		    }
		  },
		  data: [{			
					type: "scatter",
					dataPoints: [
		        { x: 0 ,y: 0 },
					]
				}]
		});
		
		chart.render();
		addImages(chart);
		positionImages();
		
		function addImages(chart){
		    images.push(new Image(648,347));
		    images[0].style.cssText = "position: absolute; pointer-events: none";    
		    images[0].src = imgUrl;
		    chart.container.appendChild(images[0]);
		}
		//Position image based on dataPoint x & y value
		function positionImages() {
		    images[0].style.top = chart.axisY[0].convertValueToPixel(chart.data[0].dataPoints[i].y) - (images[0].width / 2) + "px";
		    images[0].style.left = chart.axisX[0].convertValueToPixel(chart.data[0].dataPoints[i].x) - (images[0].width / 2) + "px";
		}				
	}else{	
		for(i=0; i<realWeekChart.length; i++){
			
			//Check out this "+" that parses to number (long maybe)
			var date = new Date(realWeekChart[i].fecha);
			date.setMonth(date.getMonth() + 1);
			//idk why, maybe bc java.util.Date is deprecated but..
			//Not neccesary date.setFullYear( date.getFullYear() - 1900 );
			solPoints.push({x: date, y:realWeekChart[i].sol});
			rainPoints.push({x: date, y:realWeekChart[i].prec});
			maxTempPoints.push({x: date, y:[realWeekChart[i].tmax,realWeekChart[i].tmin] });
			medTempPoints.push({x: date, y:realWeekChart[i].tmed});
		}	
			chart2 = new CanvasJS.Chart("weeklyChartContainer", {
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
				name: "T media",
				showInLegend: true,
				yValueFormatString: "#.# ºC",
				color:"black",
				axisYIndex: 0, //defaults to 0
				dataPoints: medTempPoints
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
	} //else
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
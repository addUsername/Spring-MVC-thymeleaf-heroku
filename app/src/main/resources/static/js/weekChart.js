
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

console.log(humidityPoints);



var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
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

function toggleDataSeries(e) {
	if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	} else {
		e.dataSeries.visible = true;
	}
	e.chart.render();
}

}
// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';
let colors = {};
// Adding key-value pairs
colors["Normal"] = "#007bff";
colors["Moyen"] = "#ffc107";
colors["Danger"] = "#dc3545";
let typeColors = [];
let types =[]
if (typeof type === 'string') {
  typeColors.push(colors[type]);
  types.push(type)
}else{
  typeColors = type.map(t=> colors[t]);
  types = type;
}

// Pie Chart Example
var ctx = document.getElementById("myPieChart");
var myPieChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: types,
    datasets: [{
      data: temperatureData,
      backgroundColor: typeColors,
    }],
  },
});

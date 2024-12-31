// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';
let months =[]
let max =[]
if (typeof mois === 'string') {
  months.push(mois)
  max.push(maxTemp);
  console.log("String=>"+max);
  console.log("String=>"+maxTemp);
  console.log("String=>"+mois);
}else{
  months = mois;
  max = maxTemp;
  console.log("array=>"+max);
  console.log("array=>"+maxTemp);
  console.log("array=>"+mois);

}
console.log(months);
// Bar Chart Example
var ctx = document.getElementById("myBarChart");
var myLineChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: months,
    datasets: [{
      label: "Temperature Max",
      backgroundColor: "rgba(2,117,216,1)",
      borderColor: "rgba(2,117,216,1)",
      data: max,
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: 'month'
        },
        gridLines: {
          display: false
        },
        ticks: {
          maxTicksLimit: months.length
        }
      }],
      yAxes: [{
        ticks: {
          min: 0,
          max: Math.floor(Math.max(...max))+10,
          maxTicksLimit: 5
        },
        gridLines: {
          display: true
        }
      }],
    },
    legend: {
      display: false
    }
  }
});

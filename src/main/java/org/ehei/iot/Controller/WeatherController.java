package org.ehei.iot.Controller;

import org.ehei.iot.Entities.Weather;
import org.ehei.iot.Service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class WeatherController {

    @Autowired
    private IWeatherService weatherService;


    @GetMapping("/Weathers")
    public String getWeather(Model model,
      @RequestParam(value = "type",defaultValue = "0",required = false) int type,
      @RequestParam(value = "dateDebut",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
      @RequestParam(value = "dateFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        if(dateDebut !=null && dateFin != null && dateDebut.isAfter(dateFin))
        {
         model.addAttribute("List",weatherService.getWeather());
         model.addAttribute("eror",true);
         return "WeatherList";
        }
        model.addAttribute("List",weatherService.getWeatherByTypeAndDate(type,dateDebut,dateFin));
        return "WeatherList";
    }

    @GetMapping("/Charts")
    public String getWeatherStatistique(Model model,
                             @RequestParam(value = "type",defaultValue = "0",required = false) int type,
                             @RequestParam(value = "dateDebut",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
                             @RequestParam(value = "dateFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        if(dateDebut !=null && dateFin != null && dateDebut.isAfter(dateFin))
        {
            dateDebut = null;
            dateFin=null;
            model.addAttribute("eror",true);
        }
        model.addAttribute("temperature",
                weatherService.getWeatherByTypeAndDate(type,dateDebut,dateFin).stream().map(Weather::getTemperature).toList()
        );

        model.addAttribute("humidity",
                weatherService.getWeatherByTypeAndDate(type,dateDebut,dateFin).stream().map(Weather::getHumidite).toList()
        );

        model.addAttribute("date",
                weatherService.getWeatherByTypeAndDate(type,dateDebut,dateFin).stream().map(Weather::getDate).toList()
        );

        model.addAttribute("List",weatherService.getWeatherByTypeAndDate(type,dateDebut,dateFin));


        Map<Integer, String> monthMap = new HashMap<>();
        monthMap.put(1, "Janvier");
        monthMap.put(2, "Février");
        monthMap.put(3, "Mars");
        monthMap.put(4, "Avril");
        monthMap.put(5, "Mai");
        monthMap.put(6, "Juin");
        monthMap.put(7, "Juillet");
        monthMap.put(8, "Août");
        monthMap.put(9, "Septembre");
        monthMap.put(10, "Octobre");
        monthMap.put(11, "Novembre");
        monthMap.put(12, "Décembre");

        List<String> month = new ArrayList<>();
        List<Object> monthnumber = weatherService.getMaxTemperatureByMonth(dateDebut, dateFin).stream().map(objects -> objects[0]).toList();

        for (Object i:monthnumber) {
            month.add(monthMap.get(i));
        }

        model.addAttribute("mois",
                month

        );

        model.addAttribute("max",
                weatherService.getMaxTemperatureByMonth(dateDebut, dateFin).stream().map(objects -> objects[1]).toList()
        );

        List<String> types = List.of("Normal","Moyen","Danger");

        model.addAttribute("types",types);


        model.addAttribute("temperatureData",
                weatherService.getWeatherGroupedByType().stream().map(objects -> objects[1]).toList()
        );

        return "WeatherCharts";
    }



    @GetMapping("/TodayWeather")
    public String getTodayWeather(Model model) {
        Weather weather = weatherService.getTodayWeather();
        if(weather == null){
            model.addAttribute("date",null);
            model.addAttribute("time", null);
            model.addAttribute("Weather",null);
            return "TodayWeather";
        }
        Date date = weather.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);

        model.addAttribute("date",formattedDate);

        dateFormat = new SimpleDateFormat("HH:mm:ss");

        // Format the time to a string
        String formattedTime = dateFormat.format(date);

        // Add the formatted time to the Thymeleaf model
        model.addAttribute("time", formattedTime);

        model.addAttribute("Weather",weather);
        return "TodayWeather";
    }

    @GetMapping("/Weather/{id}")
    public String getWeatherById(@PathVariable int id){
        return "WeatherDetail";
    }

}

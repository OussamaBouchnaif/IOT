package org.ehei.iot.Controller;

import jakarta.mail.MessagingException;
import org.ehei.iot.Entities.Weather;
import org.ehei.iot.Service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private IWeatherService weatherService;

    @PostMapping("/Api")
    public void addWeather(@RequestBody Weather weather) throws MessagingException {
        System.out.println("test");
        weatherService.addWeather(weather);
    }
}

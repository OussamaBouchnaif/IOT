package org.ehei.iot.Service;

import jakarta.mail.MessagingException;
import org.ehei.iot.Entities.Weather;

import java.time.LocalDateTime;
import java.util.List;

public interface IWeatherService {
    public void addWeather(Weather weather) throws MessagingException;

    public List<Weather> getWeatherByTypeAndDate(int type, LocalDateTime dateDebut, LocalDateTime dateFin);
    public List<Weather> getWeather();
    public List<Weather> getWeatherByType(int type);

    public Weather getTodayWeather();

    public List<Object[]> getWeatherGroupedByType();

    public List<Object[]> getMaxTemperatureByMonth(LocalDateTime dateDebut, LocalDateTime dateFin);
}

package org.ehei.iot.Service;

import jakarta.mail.MessagingException;
import org.ehei.iot.Entities.Weather;
import org.ehei.iot.Repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WeatherService implements IWeatherService{

    public static int counter = 0;
    public static  final int DANGERTEMPHOT =30;
    public static  final int DANGERTEMPCOLD =-5;
    public static  final int MOYENTEMPHOT =25;
    public static  final int MOYENTEMPCOLD =3;
    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private INotificationService notificationService;

    @Override
    public void addWeather(Weather weather) throws MessagingException {

        Calendar calendar = Calendar.getInstance();
        weather.setDate(calendar.getTime());

        double temp = weather.getTemperature();
        if(temp>DANGERTEMPHOT || temp<DANGERTEMPCOLD){
            counter++;
            weather.setType(3);
            for (int i = 1; i <= counter; i++) {
                notificationService.sendMail(i,3,temp);
                notificationService.sendInTelegram(i,3,temp);
            }
        }else if(temp>MOYENTEMPHOT || temp<MOYENTEMPCOLD){
            weather.setType(2);
            for (int i = 1; i <= counter; i++) {
                notificationService.sendMail(i,2,temp);
                notificationService.sendInTelegram(i,2,temp);
            }
        }else{
            weather.setType(1);
            for (int i = 1; i <= counter; i++) {
                notificationService.sendMail(i,1,temp);
                notificationService.sendInTelegram(i,1,temp);
            }
            counter=0;
        }

        weatherRepository.save(weather);

    }
    @Override
    public List<Weather> getWeatherByTypeAndDate(int type, LocalDateTime dateDebut, LocalDateTime dateFin){
        if(type ==0 && dateDebut==null&& dateFin ==null)
            return weatherRepository.findAll();

       if(type==0){
           if(dateDebut!= null && dateFin!=null){
               Date debut = convertToDate(dateDebut);
               Date fin = convertToDate(dateFin);
               return weatherRepository.findAllByDateBetween(debut,fin);
           } else if (dateDebut != null) {
               Date debut = convertToDate(dateDebut);
                return weatherRepository.findAllByDateAfter(debut);
           } else{
               Date fin = convertToDate(dateFin);
               return weatherRepository.findAllByDateBefore(fin);
           }
       }else{
           if(dateDebut!= null && dateFin!=null){
               Date debut = convertToDate(dateDebut);
               Date fin = convertToDate(dateFin);
               return weatherRepository.findAllByTypeAndDateBetween(type,debut,fin);
           } else if (dateDebut != null) {
               Date debut = convertToDate(dateDebut);
               return weatherRepository.findAllByTypeAndDateAfter(type,debut);
           } else if (dateFin != null) {
               Date fin = convertToDate(dateFin);
               return weatherRepository.findAllByTypeAndDateBefore(type,fin);
           }else{
               return weatherRepository.findAllByType(type);
           }
       }
    }

    @Override
    public List<Weather> getWeather() {
        return weatherRepository.findAll();
    }

    @Override
    public List<Weather> getWeatherByType(int type) {
        return weatherRepository.findAllByType(type);
    }

    @Override
    public Weather getTodayWeather() {
        return weatherRepository.findTopByOrderByIdDesc();
    }

    @Override
    public List<Object[]> getWeatherGroupedByType(){
        return weatherRepository.countTemperatureByType();
    }

    @Override
    public List<Object[]> getMaxTemperatureByMonth(LocalDateTime dateDebut, LocalDateTime dateFin) {
        if(dateDebut != null && dateFin != null)
            return  weatherRepository.findMaxTemperatureByMonthWithFilter(convertToDate(dateDebut),convertToDate(dateFin));
        else if(dateDebut == null && dateFin == null){
            return weatherRepository.findMaxTemperatureByMonth();
        } else if (dateDebut != null) {
            return weatherRepository.findMaxTemperatureByMonthWithStartDate(convertToDate(dateDebut));
        }else {
            return weatherRepository.findMaxTemperatureByMonthWithEndDate(convertToDate(dateFin));
        }
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}

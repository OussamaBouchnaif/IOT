package org.ehei.iot.Repository;

import org.ehei.iot.Entities.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather,Integer> {
     Weather findById(int id);
     Weather findTopByOrderByIdDesc();
     List<Weather> findAllByType(int type);
     List<Weather> findAllByDateAfter(Date date);
     List<Weather> findAllByDateBefore(Date date);
     List<Weather> findAllByDateBetween(Date dateDebut,Date dateFin);
     List<Weather> findAllByTypeAndDateBetween(int type,Date dateDebut,Date dateFin);
     List<Weather> findAllByTypeAndDateAfter(int type, Date dateDebut);
     List<Weather> findAllByTypeAndDateBefore(int type, Date dateDebut);

     @Query("SELECT w.type, COUNT(w) FROM Weather w GROUP BY w.type")
     List<Object[]> countTemperatureByType();

     @Query("SELECT MONTH(w.date) AS month, MAX(w.temperature) AS maxTemperature " +
             "FROM Weather w " +
             "WHERE w.date BETWEEN :startDate AND :endDate " +
             "GROUP BY MONTH(w.date)")
     List<Object[]> findMaxTemperatureByMonthWithFilter(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

     @Query("SELECT MONTH(w.date) AS month, MAX(w.temperature) AS maxTemperature " +
             "FROM Weather w " +
             "GROUP BY MONTH(w.date)")
     List<Object[]> findMaxTemperatureByMonth();

     @Query("SELECT MONTH(w.date) AS month, MAX(w.temperature) AS maxTemperature " +
             "FROM Weather w " +
             "WHERE w.date >= :startDate " +
             "GROUP BY MONTH(w.date)")
     List<Object[]> findMaxTemperatureByMonthWithStartDate(@Param("startDate") Date startDate);


     @Query("SELECT MONTH(w.date) AS month, MAX(w.temperature) AS maxTemperature " +
             "FROM Weather w " +
             "WHERE w.date <= :endDate " +
             "GROUP BY MONTH(w.date)")
     List<Object[]> findMaxTemperatureByMonthWithEndDate(@Param("endDate") Date endDate);
}

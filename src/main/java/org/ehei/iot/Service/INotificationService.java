package org.ehei.iot.Service;

import jakarta.mail.MessagingException;
import org.ehei.iot.Entities.Weather;

public interface INotificationService {

    public void sendMail(int role,int type,double temp) throws MessagingException;

    public void sendInTelegram(int role,int type,double temp);

}

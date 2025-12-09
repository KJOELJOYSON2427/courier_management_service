package com.example.Scheduling.CronJob;

import com.example.Scheduling.parcelModels.Parcel;
import com.example.Scheduling.parcelModels.ParcelStatus;
import com.example.Scheduling.parcelRepo.ParcelRepository;
import com.example.Scheduling.service.GmailService;
import com.example.Scheduling.userModels.User;
import com.example.Scheduling.userRepo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class Scheduler {

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class.getName());
      @Autowired
      private UserRepository userRepository;

      @Autowired
      private ParcelRepository parcelRepository;
    @Autowired
    private GmailService gmailService;
//    // Runs every 10 seconds
//    @Scheduled(cron = "* * * * * *")
//    public void method() {
//        System.out.println("Cron Job running: " + System.currentTimeMillis());
//    }

    private List<Parcel> findParcelsNeedingNotification() {

        return parcelRepository.findByStatusIn(
                List.of(
                        ParcelStatus.CREATED,
                        ParcelStatus.PENDING,
                        ParcelStatus.IN_TRANSIT
                )
        );
    }

       private List<User> findStatusWithDefaultValue(){
           List<User> recentCreatedUser = userRepository.findByStatus();
           return recentCreatedUser;
       }

    @Scheduled(fixedRate = 30000)
    public void sendWelcomeEmails() {
      List<User>  newUsers = findStatusWithDefaultValue();

      for(User u: newUsers){
           gmailService.sendWelcomeEmail(u.getEmail(),
                   "Welcome to SendIt Courier",
                   "welcome",
                   Map.of("name", u.getFullName(),"email",u.getEmail()));
          u.setStatus(1);
          userRepository.save(u);
      }
    }


    @Scheduled(cron = "0 */1 * * * *")
    public void processParcelNotifications() {
        System.out.println("Parcel notification scheduler started...");

        // Get only parcels that need attention
        List<Parcel> parcels = findParcelsNeedingNotification();

        for(Parcel parcel: parcels){
            try{
                boolean notified = false;

                if(parcel.getStatus() == ParcelStatus.CREATED && !parcel.getCreatedEmailSent()) {
                    gmailService.sendParcelCreatedEmail(parcel);
                    parcel.setStatus(ParcelStatus.PENDING);
                    parcel.setCreatedEmailSent(true);
                    notified = true;
                }

                else if(parcel.getStatus() == ParcelStatus.PENDING
                        && parcel.getPickedUpByCourierAt() != null
                        && !parcel.getPickedUpEmailSent()) {
                    gmailService.sendParcelPickedUpEmail(parcel);
                    parcel.setStatus(ParcelStatus.IN_TRANSIT);
                    parcel.setPickedUpEmailSent(true);
                    notified = true;
                }

                else if(parcel.getStatus() == ParcelStatus.IN_TRANSIT
                        && parcel.getOutForDeliveryAt() != null
                        && !parcel.getOutForDeliveryEmailSent()) {
                    gmailService.sendOutForDeliveryEmail(parcel);
                    parcel.setOutForDeliveryEmailSent(true);
                    notified = true;
                }

                else if(parcel.getDeliveredAt() != null
                        && parcel.getStatus() != ParcelStatus.DELIVERED
                        && !parcel.getDeliveredEmailSent()) {
                    gmailService.sendDeliveredEmail(parcel);
                    parcel.setStatus(ParcelStatus.DELIVERED);
                    parcel.setDeliveredEmailSent(true);
                    notified = true;
                }


                if (notified) {
                    parcelRepository.save(parcel);
                    log.info("Notification sent → {}", parcel.getTrackingNumber());
                }


            } catch (Exception e) {
                log.error("Failed to notify parcel {}: {}",
                        parcel.getTrackingNumber(), e.getMessage());
            }
                }

        log.info("Parcel notification scheduler finished.");
    }
    }
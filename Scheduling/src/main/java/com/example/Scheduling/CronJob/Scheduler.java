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


    @Scheduled(cron = "0 */60 * * * *")
    public void processParcelNotifications() {
        System.out.println("Parcel notification scheduler started...");

        // Get only parcels that need attention
        List<Parcel> parcels = findParcelsNeedingNotification();

        for(Parcel parcel: parcels){
            try{
                boolean notified = false;

                // 1. CREATED → Sender confirmation only
                if(parcel.getStatus() == ParcelStatus.CREATED){
                    gmailService.sendParcelCreatedEmail(parcel);
                    parcel.setStatus(ParcelStatus.PENDING);
                    notified = true;
                }


                // 2. PENDING → Courier picked it up
                else if (parcel.getStatus() == ParcelStatus.PENDING
                        && parcel.getPickedUpByCourierAt() != null) {
                    gmailService.sendParcelPickedUpEmail(parcel);  // First time recipient knows!
                    parcel.setStatus(ParcelStatus.IN_TRANSIT);
                    notified = true;
                }

                // 3. IN_TRANSIT → Out for delivery today
                else if (parcel.getStatus() == ParcelStatus.IN_TRANSIT
                        && parcel.getOutForDeliveryAt() != null) {  // or your flag/method
                    gmailService.sendOutForDeliveryEmail(parcel);
                    // status stays IN_TRANSIT until actually delivered

                    notified = true;
                }// 4. Any state → Actually delivered
                else if (parcel.getDeliveredAt() != null
                        && parcel.getStatus() != ParcelStatus.DELIVERED) {
                    gmailService.sendDeliveredEmail(parcel);
                    parcel.setStatus(ParcelStatus.DELIVERED);
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
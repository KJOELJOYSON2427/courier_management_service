package com.example.Scheduling.CronJob;

import com.example.Scheduling.service.GmailService;
import com.example.Scheduling.userModels.User;
import com.example.Scheduling.userRepo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {
      @Autowired
      private UserRepository userRepository;

    @Autowired
    private GmailService gmailService;
//    // Runs every 10 seconds
//    @Scheduled(cron = "* * * * * *")
//    public void method() {
//        System.out.println("Cron Job running: " + System.currentTimeMillis());
//    }

       private List<User> findStatusWithDefaultValue(){
           List<User> recentCreatedUser = userRepository.findByStatus();
           return recentCreatedUser;
       }

    @Scheduled(fixedRate = 3000000)
    public void sendWelcomeEmails() {
      List<User>  newUsers = findStatusWithDefaultValue();

      for(User u: newUsers){
           gmailService.sendWelcomeEmail(u.getEmail(),u.getFullName());
          u.setStatus(1);
          userRepository.save(u);
      }
    }


    }
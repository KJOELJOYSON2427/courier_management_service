package com.example.Scheduling.gmailRepo;

import com.example.Scheduling.gmailToken.GmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GmailTokenRepository extends JpaRepository<GmailToken, Long> {

}


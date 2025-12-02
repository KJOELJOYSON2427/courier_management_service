package com.example.parcelorCourierService.Service;

import com.example.parcelorCourierService.Entity.Parcel;
import com.example.parcelorCourierService.Entity.ParcelHistory;
import com.example.parcelorCourierService.Entity.ParcelStatus;
import com.example.parcelorCourierService.Entity.dto.ParcelDTO;
import com.example.parcelorCourierService.Entity.dto.ParcelDtoMapper;
import com.example.parcelorCourierService.Entity.dto.ParcelHistoryDto;
import com.example.parcelorCourierService.repository.ParcelRepository;
import com.example.parcelorCourierService.request.ParcelRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParcelService {



    @Autowired
    private ParcelRepository parcelRepository;

    public ParcelDTO createParcelBooking(ParcelRequest request){

        Parcel parcel = new Parcel();

        parcel.setSenderId(request.getSenderId());
        parcel.setRecieverName(request.getReceiverName());
        parcel.setRecieverAddress(request.getReceiverAddress());
        parcel.setWeight(request.getWeight());
        parcel.setDimensions(request.getDimensions());
        parcel.setStatus(ParcelStatus.CREATED);
        parcel.setParcelGetId(request.getParcelGetId());
        parcel.setCreatedAt(LocalDateTime.now());
        parcel.setUpdatedAt(LocalDateTime.now());
parcel.setTrackingNumber(request.getTrackingNumber());

        ParcelHistory history = new ParcelHistory();

        history.setStatus(ParcelStatus.CREATED.name());
        history.setDescription("Parcel booking created");
        history.setTimestamp(LocalDateTime.now());
        history.setParcel(parcel);

        parcel.getHistory().add(history);

         Parcel savedParcel= parcelRepository.save(parcel);

         return ParcelDtoMapper.toDto(savedParcel);

    }


    public ParcelDTO getParcelByTrackingNumberForCustomer(String trackingNumber, Long customerId) {
        Parcel parcel = parcelRepository.findByTrackingNumberWithHistory(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        // Check if the parcel belongs to this customer
        if (!parcel.getSenderId().equals(customerId)) {
            throw new RuntimeException("You are not authorized to view this parcel");
        }

        ParcelDTO dto = new ParcelDTO();
        dto.setTrackingNumber(parcel.getTrackingNumber());
        dto.setSenderId(parcel.getSenderId());
        dto.setReceiverName(parcel.getRecieverName());
        dto.setReceiverAddress(parcel.getRecieverAddress());
        dto.setWeight(parcel.getWeight());
        dto.setDimensions(parcel.getDimensions());
        dto.setCreatedAt(parcel.getCreatedAt());
        dto.setUpdatedAt(parcel.getUpdatedAt());
        dto.setStatus(parcel.getStatus().name());

        // Customer should also see history
        if (parcel.getHistory() != null) {
            dto.setHistory(parcel.getHistory().stream().map(h -> {
                ParcelHistoryDto hdto = new ParcelHistoryDto();
                hdto.setStatus(h.getStatus());
                hdto.setTimestamp(h.getTimestamp());
                return hdto;
            }).collect(Collectors.toList()));
        }

        return dto;
    }



    public ParcelDTO getParcelByTrackingNumberForAdmin(String trackingNumber) {
        Parcel parcel = parcelRepository.findByTrackingNumberWithHistory(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        ParcelDTO dto = new ParcelDTO();
        dto.setTrackingNumber(parcel.getTrackingNumber());
        dto.setSenderId(parcel.getSenderId());
        dto.setReceiverName(parcel.getRecieverName());
        dto.setReceiverAddress(parcel.getRecieverAddress());
        dto.setWeight(parcel.getWeight());
        dto.setDimensions(parcel.getDimensions());
        dto.setCreatedAt(parcel.getCreatedAt());
        dto.setUpdatedAt(parcel.getUpdatedAt());
        dto.setStatus(parcel.getStatus().name());
        dto.setDriverId(parcel.getDriverId()); // Admin can see driver details

        if (parcel.getHistory() != null) {
            dto.setHistory(parcel.getHistory().stream().map(h -> {
                ParcelHistoryDto hdto = new ParcelHistoryDto();
                hdto.setStatus(h.getStatus());
                hdto.setTimestamp(h.getTimestamp());
                hdto.setDescription(h.getDescription());
                return hdto;
            }).collect(Collectors.toList()));
        }

        return dto;
    }


    public List<ParcelDTO> getParcelByCustomerId(Long senderId) {
           return
                   parcelRepository.findBySenderId(senderId)
                           .stream()
                           .map(ParcelDtoMapper::mapToResponse)
                           .collect(Collectors.toList());
    }


    @Transactional
    public void assignDriver(Long parcelId, Long driverId) {
        Parcel parcel = parcelRepository.findByParcelGetId(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));
        parcel.setStatus(ParcelStatus.ASSIGNED);
        parcel.setDriverId(driverId);
        parcelRepository.save(parcel);

    }

    public void markAsDelivered(Long parcelId) {
        Parcel parcel = parcelRepository.findByParcelGetId(parcelId).orElseThrow(
                () -> new RuntimeException("Parcel not found")
        );
    parcel.setStatus(ParcelStatus.DELIVERED);
    parcelRepository.save(parcel);
    }

    public List<ParcelDTO> getParcelsByDriver(Long driverId) {
        List<Parcel> parcels = parcelRepository.findByDriverId(driverId);
        return parcels.stream()
                .map(ParcelDtoMapper::mapToResponse)
                .toList();
    }
}

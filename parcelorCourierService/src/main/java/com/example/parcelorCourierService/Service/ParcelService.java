package com.example.parcelorCourierService.Service;

import com.example.parcelorCourierService.Entity.Parcel;
import com.example.parcelorCourierService.Entity.ParcelHistory;
import com.example.parcelorCourierService.Entity.ParcelStatus;
import com.example.parcelorCourierService.Entity.dto.ParcelDTO;
import com.example.parcelorCourierService.Entity.dto.ParcelDtoMapper;
import com.example.parcelorCourierService.Entity.dto.ParcelHistoryDto;
import com.example.parcelorCourierService.repository.ParcelRepository;
import com.example.parcelorCourierService.request.CreateParcelRequest;

import com.example.parcelorCourierService.request.UpdateParcel;
import com.example.parcelorCourierService.response.DeleteResponse;
import com.example.parcelorCourierService.response.ParcelResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParcelService {



    @Autowired
    private ParcelRepository parcelRepository;
//
////    public ParcelDTO createParcelBooking(ParcelRequest request){
////
////        Parcel parcel = new Parcel();
////
////        parcel.setSenderId(request.getSenderId());
////        parcel.setRecieverName(request.getReceiverName());
////        parcel.setRecieverAddress(request.getReceiverAddress());
////        parcel.setWeight(request.getWeight());
////        parcel.setDimensions(request.getDimensions());
////        parcel.setStatus(ParcelStatus.CREATED);
////        parcel.setParcelGetId(request.getParcelGetId());
////        parcel.setCreatedAt(LocalDateTime.now());
////        parcel.setUpdatedAt(LocalDateTime.now());
////parcel.setTrackingNumber(request.getTrackingNumber());
//
//        ParcelHistory history = new ParcelHistory();
//
//        history.setStatus(ParcelStatus.CREATED.name());
//        history.setDescription("Parcel booking created");
//        history.setTimestamp(LocalDateTime.now());
//        history.setParcel(parcel);
//
//        parcel.getHistory().add(history);
//
//         Parcel savedParcel= parcelRepository.save(parcel);
//
//         return ParcelDtoMapper.toDto(savedParcel);
//
//    }

//
//    public ParcelDTO getParcelByTrackingNumberForCustomer(String trackingNumber, Long customerId) {
//        Parcel parcel = parcelRepository.findByTrackingNumberWithHistory(trackingNumber)
//                .orElseThrow(() -> new RuntimeException("Parcel not found"));
//
//        // Check if the parcel belongs to this customer
//        if (!parcel.getSenderId().equals(customerId)) {
//            throw new RuntimeException("You are not authorized to view this parcel");
//        }
//
//        ParcelDTO dto = new ParcelDTO();
//        dto.setTrackingNumber(parcel.getTrackingNumber());
//        dto.setSenderId(parcel.getSenderId());
//        dto.setReceiverName(parcel.getRecieverName());
//        dto.setReceiverAddress(parcel.getRecieverAddress());
//        dto.setWeight(parcel.getWeight());
//        dto.setDimensions(parcel.getDimensions());
//        dto.setCreatedAt(parcel.getCreatedAt());
//        dto.setUpdatedAt(parcel.getUpdatedAt());
//        dto.setStatus(parcel.getStatus().name());
//
//        // Customer should also see history
//        if (parcel.getHistory() != null) {
//            dto.setHistory(parcel.getHistory().stream().map(h -> {
//                ParcelHistoryDto hdto = new ParcelHistoryDto();
//                hdto.setStatus(h.getStatus());
//                hdto.setTimestamp(h.getTimestamp());
//                return hdto;
//            }).collect(Collectors.toList()));
//        }
//
//        return dto;
//    }
//
//
//
//    public ParcelDTO getParcelByTrackingNumberForAdmin(String trackingNumber) {
//        Parcel parcel = parcelRepository.findByTrackingNumberWithHistory(trackingNumber)
//                .orElseThrow(() -> new RuntimeException("Parcel not found"));
//
//        ParcelDTO dto = new ParcelDTO();
//        dto.setTrackingNumber(parcel.getTrackingNumber());
//        dto.setSenderId(parcel.getSenderId());
//        dto.setReceiverName(parcel.getRecieverName());
//        dto.setReceiverAddress(parcel.getRecieverAddress());
//        dto.setWeight(parcel.getWeight());
//        dto.setDimensions(parcel.getDimensions());
//        dto.setCreatedAt(parcel.getCreatedAt());
//        dto.setUpdatedAt(parcel.getUpdatedAt());
//        dto.setStatus(parcel.getStatus().name());
//        dto.setDriverId(parcel.getDriverId()); // Admin can see driver details
//
//        if (parcel.getHistory() != null) {
//            dto.setHistory(parcel.getHistory().stream().map(h -> {
//                ParcelHistoryDto hdto = new ParcelHistoryDto();
//                hdto.setStatus(h.getStatus());
//                hdto.setTimestamp(h.getTimestamp());
//                hdto.setDescription(h.getDescription());
//                return hdto;
//            }).collect(Collectors.toList()));
//        }
//
//        return dto;
//    }
//
//
//    public List<ParcelDTO> getParcelByCustomerId(Long senderId) {
//           return
//                   parcelRepository.findBySenderId(senderId)
//                           .stream()
//                           .map(ParcelDtoMapper::mapToResponse)
//                           .collect(Collectors.toList());
//    }
//
//
//    @Transactional
//    public void assignDriver(Long parcelId, Long driverId) {
//        Parcel parcel = parcelRepository.findByParcelGetId(parcelId)
//                .orElseThrow(() -> new RuntimeException("Parcel not found"));
//        parcel.setStatus(ParcelStatus.ASSIGNED);
//        parcel.setDriverId(driverId);
//        parcelRepository.save(parcel);
//
//    }
//
//    public void markAsDelivered(Long parcelId) {
//        Parcel parcel = parcelRepository.findByParcelGetId(parcelId).orElseThrow(
//                () -> new RuntimeException("Parcel not found")
//        );
//    parcel.setStatus(ParcelStatus.DELIVERED);
//    parcelRepository.save(parcel);
//    }
//
//    public List<ParcelDTO> getParcelsByDriver(Long driverId) {
//        List<Parcel> parcels = parcelRepository.findByDriverId(driverId);
//        return parcels.stream()
//                .map(ParcelDtoMapper::mapToResponse)
//                .toList();
//    }







    //------>Frontend ---------


     public void createParcel(CreateParcelRequest parcelRequest){

        try{
            Parcel parcel = new Parcel();


            parcel.setTrackingNumber(parcelRequest.getTrackingNumber());

            parcel.setSenderName(parcelRequest.getSenderName());
            parcel.setSenderEmail(parcelRequest.getSenderEmail());
            parcel.setSenderAddress(parcelRequest.getSenderAddress());

            parcel.setReceiverEmail(parcelRequest.getReceiverEmail());
            parcel.setRecieverName(parcelRequest.getRecieverName());
            parcel.setRecieverAddress(parcelRequest.getRecieverAddress());

            parcel.setCost(parcelRequest.getCost());
            parcel.setSenderId(parcelRequest.getSenderId());
            parcel.setWeight(parcelRequest.getWeight());
            parcel.setDimensions(parcelRequest.getDimensions());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
     }


    // Get all Parcels sorted by createdAt (latest first)
    public List<Parcel> getAllParcel() {

        try {
            return parcelRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch parcels", e);
        }
    }

    public ParcelResponse updateParcel(UpdateParcel updateParcel, String trackingNumber) {
         try{

             Parcel parcel =  parcelRepository.findByTrackingNumber(trackingNumber);

             if (parcel == null) {
                 throw new RuntimeException("Parcel not found with tracking number: " + trackingNumber);
             }

             // Update fields
             // Update fields
             if (updateParcel.getReceiverEmail() != null) parcel.setReceiverEmail(updateParcel.getReceiverEmail());
             if (updateParcel.getRecieverName() != null) parcel.setRecieverName(updateParcel.getRecieverName());
             if (updateParcel.getRecieverAddress() != null) parcel.setRecieverAddress(updateParcel.getRecieverAddress());
             if (updateParcel.getCost() > 0) parcel.setCost(updateParcel.getCost());
             if (updateParcel.getWeight() > 0) parcel.setWeight(updateParcel.getWeight());
             if (updateParcel.getDimensions() != null) parcel.setDimensions(updateParcel.getDimensions());
             if (updateParcel.getDriverId() != null) parcel.setDriverId(updateParcel.getDriverId());
             if (updateParcel.getStatus() != null) parcel.setStatus(ParcelStatus.valueOf(updateParcel.getStatus()));

             parcelRepository.save(parcel);


             // Convert entity to response DTO
             ParcelResponse response = new ParcelResponse();
             response.setTrackingNumber(parcel.getTrackingNumber());
             response.setSenderName(parcel.getSenderName());
             response.setSenderEmail(parcel.getSenderEmail());
             response.setSenderAddress(parcel.getSenderAddress());
             response.setReceiverEmail(parcel.getReceiverEmail());
             response.setRecieverName(parcel.getRecieverName());
             response.setRecieverAddress(parcel.getRecieverAddress());
             response.setCost(parcel.getCost());
             response.setWeight(parcel.getWeight());
             response.setDimensions(parcel.getDimensions());
             response.setDriverId(parcel.getDriverId());
             response.setStatus(parcel.getStatus().name());
             response.setCreatedAt(parcel.getCreatedAt());
             response.setUpdatedAt(parcel.getUpdatedAt());
             return response;
         }catch (Exception e){
             throw new RuntimeException(e);
         }
    }

    public ParcelResponse getOneParcel(String trackingNumber) {
        try {
            // Find the parcel entity
            Parcel parcel = parcelRepository.findByTrackingNumber(trackingNumber);

            if (parcel == null) {
                throw new RuntimeException("Parcel not found with tracking number: " + trackingNumber);
            }

            // Convert entity to response DTO
            ParcelResponse response = new ParcelResponse();
            response.setTrackingNumber(parcel.getTrackingNumber());
            response.setSenderName(parcel.getSenderName());
            response.setSenderEmail(parcel.getSenderEmail());
            response.setSenderAddress(parcel.getSenderAddress());
            response.setReceiverEmail(parcel.getReceiverEmail());
            response.setRecieverName(parcel.getRecieverName());
            response.setRecieverAddress(parcel.getRecieverAddress());
            response.setCost(parcel.getCost());
            response.setWeight(parcel.getWeight());
            response.setDimensions(parcel.getDimensions());
            response.setDriverId(parcel.getDriverId());
            response.setStatus(parcel.getStatus().name());
            response.setCreatedAt(parcel.getCreatedAt());
            response.setUpdatedAt(parcel.getUpdatedAt());

            return response;

        } catch (RuntimeException e) {
            throw e; // propagate
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch parcel: " + e.getMessage(), e);
        }
    }

    public List<ParcelResponse> getUsersParcelBySenderEmail(String email) {
        List<Parcel> parcels = parcelRepository.findBySenderEmail(email, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<ParcelResponse> responseList = new ArrayList<>();

        for (Parcel parcel : parcels){
            ParcelResponse response = new ParcelResponse();
            response.setTrackingNumber(parcel.getTrackingNumber());
            response.setSenderName(parcel.getSenderName());
            response.setSenderEmail(parcel.getSenderEmail());
            response.setSenderAddress(parcel.getSenderAddress());
            response.setReceiverEmail(parcel.getReceiverEmail());
            response.setRecieverName(parcel.getRecieverName());
            response.setRecieverAddress(parcel.getRecieverAddress());
            response.setCost(parcel.getCost());
            response.setWeight(parcel.getWeight());
            response.setDimensions(parcel.getDimensions());
            response.setDriverId(parcel.getDriverId());
            response.setStatus(parcel.getStatus().name());
            response.setCreatedAt(parcel.getCreatedAt());
            response.setUpdatedAt(parcel.getUpdatedAt());

            responseList.add(response);
        }

        return  responseList;
    }

    @Transactional
    public DeleteResponse deleteParcel(String trackingNumber) {
        try{
            // 1. Find the parcel by tracking number
            Parcel parcel = parcelRepository.findByTrackingNumber(trackingNumber);

            // 2. If not found → return failure message
            if (parcel == null) {
                return new DeleteResponse("Parcel not found with tracking number: " + trackingNumber, false);
            }

            // 3. This actually deletes it from the database
            parcelRepository.delete(parcel);

            // 4. Success
            return new DeleteResponse("Parcel deleted successfully", true);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete parcel: " + e.getMessage(), e);
        }
    }
}

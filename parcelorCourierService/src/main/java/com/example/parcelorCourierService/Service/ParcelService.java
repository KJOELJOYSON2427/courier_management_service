package com.example.parcelorCourierService.Service;

import com.example.parcelorCourierService.Entity.Parcel;
import com.example.parcelorCourierService.Entity.ParcelHistory;
import com.example.parcelorCourierService.Entity.ParcelStatus;
import com.example.parcelorCourierService.Entity.dto.ParcelDTO;
import com.example.parcelorCourierService.Entity.dto.ParcelHistoryDto;
import com.example.parcelorCourierService.repository.ParcelRepository;
import com.example.parcelorCourierService.request.CreateParcelRequest;

import com.example.parcelorCourierService.request.ParcelStatusUpdateRequest;
import com.example.parcelorCourierService.request.UpdateParcel;
import com.example.parcelorCourierService.response.DeleteResponse;
import com.example.parcelorCourierService.response.ParcelResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;
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


public void createParcel(CreateParcelRequest parcelRequest) {
    try {
        Parcel parcel = new Parcel();



        parcel.setSenderName(parcelRequest.getSenderName());
        parcel.setSenderEmail(parcelRequest.getSenderEmail());
        parcel.setSenderAddress(parcelRequest.getFrom());

        parcel.setReceiverEmail(parcelRequest.getRecipientEmail());
        parcel.setRecieverName(parcelRequest.getRecipientName());
        parcel.setRecieverAddress(parcelRequest.getTo());

        parcel.setCost(parcelRequest.getCost());
        parcel.setSenderId(parcelRequest.getSenderId());
        parcel.setWeight(parcelRequest.getWeight());
        parcel.setDimensions(parcelRequest.getDimensions());
        // ⭐️ THIS LINE SAVES TO DB ⭐️
        parcelRepository.save(parcel);

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



    public Page<Parcel> getAllParcelsPageable(int page, int size, String sortDir){

         try{
             // Ensure page and size are valid
             if (page < 0) page = 0;
             if (size <= 0) size = 10; // default size
             Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
             Pageable pageable = PageRequest.of(
                     page,
                     size,
                     Sort.by(direction, "createdAt")
             );

             return parcelRepository.findAll(pageable);
         } catch (Exception e) {
             throw new RuntimeException("Failed to fetch parcels with pagination", e);         }
    }

    private Sort buildSort(
            String defaultSortDir,
            String trackingNumberSort,
            String statusSort
    ) {

        List<Sort.Order> orders = new ArrayList<>();

        if (trackingNumberSort != null) {
            orders.add(new Sort.Order(
                    Sort.Direction.fromString(trackingNumberSort),
                    "trackingNumber"
            ));
        }

        if (statusSort != null) {
            orders.add(new Sort.Order(
                    Sort.Direction.fromString(statusSort),
                    "status"
            ));
        }

        // Fallback
        if (orders.isEmpty()) {
            orders.add(new Sort.Order(
                    Sort.Direction.fromString(defaultSortDir),
                    "createdAt"
            ));
        }

        return Sort.by(orders);
    }


    public Page<Parcel> getParcelsWithFilters(
            int page,
            int size,
            String defaultSortDir,

            String from,
            String to,
            String trackingNumber,
            String status,

            String trackingNumberValues,
            String statusValues,

            String trackingNumberSort,
            String statusSort
    ) {

        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        Sort sort = buildSort(defaultSortDir, trackingNumberSort, statusSort);
        Pageable pageable = PageRequest.of(page, size, sort);

        // ✅ Replacement for Specification.where(null)
        Specification<Parcel> spec = (root, query, cb) -> cb.conjunction();

        if (from != null && !from.isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.like(cb.lower(root.get("from")), "%" + from.toLowerCase() + "%"));
        }

        if (to != null && !to.isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.like(cb.lower(root.get("to")), "%" + to.toLowerCase() + "%"));
        }

        if (trackingNumber != null && !trackingNumber.isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.like(root.get("trackingNumber"), "%" + trackingNumber + "%"));
        }

        if (trackingNumberValues != null) {
            List<String> values = Arrays.asList(trackingNumberValues.split(","));
            spec = spec.and((root, q, cb) ->
                    root.get("trackingNumber").in(values));
        }

        if (statusValues != null) {
            List<String> values = Arrays.asList(statusValues.split(","));
            spec = spec.and((root, q, cb) ->
                    root.get("status").in(values));
        }

        return parcelRepository.findAll(spec, pageable);
    }


    public ParcelResponse updateParcel(UpdateParcel updateParcel, String trackingNumber) {
        try {
            Parcel parcel = parcelRepository.findByTrackingNumber(trackingNumber);

            if (parcel == null) {
                throw new RuntimeException("Parcel not found with tracking number: " + trackingNumber);
            }

            // Update only provided fields
            if (updateParcel.getReceiverEmail() != null)
                parcel.setReceiverEmail(updateParcel.getReceiverEmail());

            if (updateParcel.getRecieverName() != null)
                parcel.setRecieverName(updateParcel.getRecieverName());

            if (updateParcel.getRecieverAddress() != null)
                parcel.setRecieverAddress(updateParcel.getRecieverAddress());

            if (updateParcel.getCost() != null && updateParcel.getCost() > 0)
                parcel.setCost(updateParcel.getCost());

            if (updateParcel.getWeight() != null && updateParcel.getWeight() > 0)
                parcel.setWeight(updateParcel.getWeight());

            if (updateParcel.getDimensions() != null)
                parcel.setDimensions(updateParcel.getDimensions());



            if (updateParcel.getStatus() != null)
                parcel.setStatus(ParcelStatus.valueOf(updateParcel.getStatus()));

            parcelRepository.save(parcel);

            // Convert to response DTO
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
            response.setStatus(parcel.getStatus().name());
            response.setCreatedAt(parcel.getCreatedAt());
            response.setUpdatedAt(parcel.getUpdatedAt());

            return response;

        } catch (Exception e) {
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

    @Transactional
    public List<ParcelResponse> getUsersParcelBySenderEmail(String email) {
        System.out.println("Sender List --->" + email
        );

        List<Parcel> parcels = parcelRepository.findBySenderEmail(email.trim(), Sort.by(Sort.Direction.DESC, "createdAt"));
        System.out.println("Parcel List --->" + parcels);
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



    public void updateParcelState(String trackingNumber, ParcelStatusUpdateRequest req) {

        Parcel parcel = parcelRepository.findByTrackingNumber(trackingNumber);

        if (parcel == null)
            throw new RuntimeException("Parcel not found: " + trackingNumber);

        LocalDateTime now = LocalDateTime.now();

        // Mark Picked Up (set only if not already set)
        if (Boolean.TRUE.equals(req.getPickedUp()) && parcel.getPickedUpByCourierAt() == null) {
            parcel.setPickedUpByCourierAt(now);
        }

        // Mark Out For Delivery
        if (Boolean.TRUE.equals(req.getOutForDelivery()) && parcel.getOutForDeliveryAt() == null) {
            parcel.setOutForDeliveryAt(now);
        }

        // Mark Delivered
        if (Boolean.TRUE.equals(req.getDelivered()) && parcel.getDeliveredAt() == null) {
            parcel.setDeliveredAt(now);
        }

        parcelRepository.save(parcel);
    }


    public Page<Parcel> getParcelsWithFilter(String searchText, List<String> searchColumns, Pageable pageable) {


     return parcelRepository.findAll((root, query, cb)->{
         // 1. If no search text, return all (conjunction is an empty WHERE clause)
         if (searchText == null || searchText.trim().isEmpty()) {
             return cb.conjunction();
         }

         String pattern="%" + searchText.toLowerCase() + "%";
         List<Predicate> predicates = new ArrayList<>();


         // 2. Define which columns we are allowed to search
         // If the frontend didn't specify columns, we search all of them by default
         List<String> columnsToSearch =(searchColumns != null && !searchColumns.isEmpty())
                 ?searchColumns:List.of("senderAddress", "recieverAddress", "status", "trackingNumber");

         for(String col : columnsToSearch){
// .as(String.class) allows searching across Enums (status) or Numbers (tracking)
             predicates.add(cb.like(cb.lower(root.get(col).as(String.class)), pattern));


         }
    return cb.or(predicates.toArray(new Predicate[0]));

     },pageable);
    }
}

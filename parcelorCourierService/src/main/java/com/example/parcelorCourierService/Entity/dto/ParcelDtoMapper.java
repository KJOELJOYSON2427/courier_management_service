//package com.example.parcelorCourierService.Entity.dto;
//
//import com.example.parcelorCourierService.Entity.Parcel;
//import com.example.parcelorCourierService.Entity.ParcelHistory;
//
//import java.util.stream.Collectors;
//
//public class ParcelDtoMapper {
//
//
//    public static ParcelDTO toDto(Parcel parcel){
//        ParcelDTO dto = new ParcelDTO();
//
//        dto.setId(parcel.getId());
//        dto.setSenderId(parcel.getSenderId());
//        dto.setReceiverName(parcel.getRecieverName());
//        dto.setReceiverAddress(parcel.getRecieverAddress());
//        dto.setWeight(parcel.getWeight());
//        dto.setDimensions(parcel.getDimensions());
//        dto.setStatus(parcel.getStatus().name());
//        dto.setCreatedAt(parcel.getCreatedAt());
//        dto.setUpdatedAt(parcel.getUpdatedAt());
//
//        dto.setParcelGetId(parcel.getParcelGetId());
//dto.setTrackingNumber(parcel.getTrackingNumber());
//
//        dto.setHistory(parcel.getHistory().stream()
//                .map( h->{
//                    ParcelHistoryDto hdto = new ParcelHistoryDto();
//                    hdto.setStatus(h.getStatus());
//                    hdto.setDescription(h.getDescription());
//                    hdto.setTimestamp(h.getTimestamp());
//                    return  hdto;
//                }).collect(Collectors.toList())
//        );
//
//        return  dto;
//    }
//
//
//
//    public static  ParcelDTO mapToResponse(Parcel parcel){
//
//        ParcelDTO response = new ParcelDTO();
//        response.setTrackingNumber(parcel.getTrackingNumber());
//        response.setReceiverName(parcel.getRecieverName());
//        response.setReceiverAddress(parcel.getRecieverAddress());
//        response.setWeight(parcel.getWeight());
//        response.setParcelGetId(parcel.getParcelGetId());
//        response.setDimensions(parcel.getDimensions());
//        response.setStatus(parcel.getStatus().name());
//        response.setDriverId(parcel.getDriverId());
//        response.setCreatedAt(parcel.getCreatedAt());
//        response.setSenderId(parcel.getSenderId());
//        response.setId(parcel.getId());
//        response.setHistory(parcel.getHistory().stream().map(
//                h->{
//                    ParcelHistoryDto hdto = new ParcelHistoryDto();
//                    hdto.setStatus(h.getStatus());
//                    hdto.setDescription(h.getDescription());
//                    hdto.setTimestamp(h.getTimestamp());
//                    return  hdto;
//                }
//        ).collect(Collectors.toList()));
//        return response;
//    }
//}

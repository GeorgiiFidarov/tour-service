package com.fidarov.tourservice.service;

import com.fidarov.tourservice.dto.TourRequest;
import com.fidarov.tourservice.dto.TourResponse;
import com.fidarov.tourservice.model.Tour;
import com.fidarov.tourservice.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TourService {

    private final TourRepository tourRepository;
    @Transactional
    public void createTour(TourRequest tourRequest){
        Tour tour = Tour.builder()
                .name(tourRequest.getName())
                .description(tourRequest.getDescription())
                .price(tourRequest.getPrice())
                .description(tourRequest.getDescription())
                .build();
        tourRepository.save(tour);
        log.info("Tour {} is saved",tour.getId());
    }

    public List<TourResponse> getAllTours() {
        List<Tour> tours = tourRepository.findAll();

        return tours.stream().map(this::mapToTourResponse).collect(Collectors.toList());
    }

    private TourResponse mapToTourResponse(Tour tour){
        return TourResponse.builder()
                .id(tour.getId())
                .name(tour.getName())
                .description(tour.getDescription())
                .price(tour.getPrice())
                .build();
    }
}

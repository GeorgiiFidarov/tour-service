package com.fidarov.tourservice.controller;

import com.fidarov.tourservice.dto.TourRequest;
import com.fidarov.tourservice.dto.TourResponse;
import com.fidarov.tourservice.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTour(@RequestBody TourRequest tourRequest){
        tourService.createTour(tourRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TourResponse> getAllTours(){
        return tourService.getAllTours();
    }
}

package com.example.newsclassification.Controllers;

import com.example.newsclassification.Entities.City;
import com.example.newsclassification.Services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/v1/city")
@CrossOrigin(origins = "http://localhost:5173")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getCity(){
        return cityService.getCity();
    }

    @GetMapping("/exists/{cityId}")
    public ResponseEntity<String> checkCityExists(@PathVariable Long cityId) {
        boolean exists = cityService.cityExists(cityId);

        if (exists) {
            return new ResponseEntity<>("City Found", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No City Exists", HttpStatus.NOT_FOUND);
        }
    }
}

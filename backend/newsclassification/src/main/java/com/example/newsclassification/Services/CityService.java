package com.example.newsclassification.Services;

import com.example.newsclassification.Entities.City;
import com.example.newsclassification.Repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCity(){
        return cityRepository.findAll();
    }

    public boolean cityExists(Long cityId) {
        Optional<City> city = cityRepository.findById(cityId);
        return city.isPresent();
    }

}

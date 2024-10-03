package com.example.newsclassification.Configs;

import com.example.newsclassification.Entities.City;
import com.example.newsclassification.Repositories.CityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
public class CityConfig {

    @Bean
    CommandLineRunner commandLineRunner(CityRepository repository) {
        return args -> {
            List<City> citiesList = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("us_cities.csv"))))) {

                br.readLine();

                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");

                    if (data.length >= 2) {
                        String name = data[0].replace("\"", "");
                        String state = data[1].replace("\"", "");

                        City city = new City(name, state);
                        citiesList.add(city);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!citiesList.isEmpty()) {
                repository.saveAll(citiesList);
            }
        };
    }

}

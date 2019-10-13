package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    MapsClient mapsClient;

    @Autowired
    PriceClient priceClient;

    @Autowired
    private final CarRepository repository;

    @Bean
    public Car getCar() {
        return new Car();
    }

    public CarService(CarRepository repository, MapsClient mapsClient,
                      PriceClient priceClient) {

        this.repository = repository;
        this.mapsClient = mapsClient;
        this.priceClient = priceClient;
    }


    public List<Car> list() {
        return repository.findAll();
    }


    public Car findById(Long id) {


        Optional <Car> optionalCar =  repository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setPrice(priceClient.getPrice(id));
            car.setLocation(mapsClient.getAddress(car.getLocation()));
            return car;
        }  else {
            throw new CarNotFoundException("Car Not Found");
        }





    }


    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setConditon(car.getCondition());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }


    public void delete(Long id) {

        Car car = repository.findById(id)
                .orElseThrow(CarNotFoundException :: new);
        repository.delete(car);
        /**
         * TODO: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         */

        /**
         * TODO: Delete the car from the repository.
         */


    }
}

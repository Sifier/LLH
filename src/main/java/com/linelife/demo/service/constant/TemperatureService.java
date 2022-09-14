package com.linelife.demo.service.constant;

import com.linelife.demo.model.constant.Temperature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TemperatureService {

    List<Temperature> getAll(Long id);
    HttpStatus save(Temperature temperature);
    Temperature findById(Long id);
    HttpStatus update(Temperature temperature, Long id);
}

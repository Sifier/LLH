package com.linelife.demo.service.constant;

import com.linelife.demo.model.constant.Pressure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PressureService {

    List<Pressure> getAll(Long id);
    HttpStatus save(Pressure pressure);
    Pressure findById(Long id);
    HttpStatus update(Pressure pressure, Long id);
}

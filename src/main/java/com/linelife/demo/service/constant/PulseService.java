package com.linelife.demo.service.constant;

import com.linelife.demo.model.constant.Pulse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PulseService {

    List<Pulse> getAll(Long id);
    HttpStatus save(Pulse pulse);
    Pulse findById(Long id);
    HttpStatus update(Pulse pulse, Long id);
}

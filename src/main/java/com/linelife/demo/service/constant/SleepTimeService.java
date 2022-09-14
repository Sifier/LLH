package com.linelife.demo.service.constant;

import com.linelife.demo.model.constant.SleepTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SleepTimeService {

    List<SleepTime> getAll(Long id);
    HttpStatus save(SleepTime sleepTime);
    SleepTime findById(Long id);
    HttpStatus update(SleepTime sleepTime, Long id);
}

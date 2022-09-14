package com.linelife.demo.service.impl.constant;

import com.linelife.demo.model.constant.SleepTime;
import com.linelife.demo.repository.constant.SleepTimeRepository;
import com.linelife.demo.service.constant.SleepTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SleepTimeServiceImpl implements SleepTimeService {

    private final SleepTimeRepository sleepTimeRepository;

    @Autowired
    public SleepTimeServiceImpl(SleepTimeRepository sleepTimeRepository) {
        this.sleepTimeRepository = sleepTimeRepository;
    }

    @Override
    public List<SleepTime> getAll(Long id) {
        List<SleepTime> sleepTimes = sleepTimeRepository.findAllByUserId(id);
        return sleepTimes;
    }

    @Override
    public HttpStatus save(SleepTime sleepTime) {
        sleepTimeRepository.save(sleepTime);
        return HttpStatus.OK;
    }

    @Override
    public SleepTime findById(Long id) {
        SleepTime sleepTime = sleepTimeRepository.findById(id).orElse(null);
        return sleepTime;
    }

    @Override
    public HttpStatus update(SleepTime sleepTime, Long id) {
        SleepTime up = sleepTimeRepository.findById(id).orElse(null);
        if (up == null) {
            return HttpStatus.NO_CONTENT;
        }
        up.setSleepTime(sleepTime.getSleepTime());
        sleepTimeRepository.save(sleepTime);
        return HttpStatus.OK;
    }
}

package com.linelife.demo.service.impl.constant;

import com.linelife.demo.model.constant.Pressure;
import com.linelife.demo.repository.constant.PressureRepository;
import com.linelife.demo.service.constant.PressureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PressureServiceImpl implements PressureService {

    private final PressureRepository pressureRepository;

    @Autowired
    public PressureServiceImpl(PressureRepository pressureRepository) {
        this.pressureRepository = pressureRepository;
    }

    @Override
    public List<Pressure> getAll(Long id) {
        List<Pressure> pressures = pressureRepository.findAllByUserId(id);
        return pressures;
    }

    @Override
    public HttpStatus save(Pressure pressure) {
        pressureRepository.save(pressure);
        return HttpStatus.OK;
    }

    @Override
    public Pressure findById(Long id) {
        Pressure pressure = pressureRepository.findById(id).orElse(null);
        return pressure;
    }

    @Override
    public HttpStatus update(Pressure pressure, Long id) {
        Pressure up = pressureRepository.findById(id).orElse(null);
        if (up == null) {
            return HttpStatus.NO_CONTENT;
        }
        up.setDiastolic(pressure.getDiastolic());
        up.setSystolic(pressure.getSystolic());
        pressureRepository.save(up);
        return HttpStatus.OK;
    }
}

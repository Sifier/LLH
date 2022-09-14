package com.linelife.demo.service.impl.constant;

import com.linelife.demo.model.constant.Temperature;
import com.linelife.demo.repository.constant.TemperatureRepository;
import com.linelife.demo.service.constant.TemperatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TemperatureServiceImpl implements TemperatureService {

    private final TemperatureRepository temperatureRepository;

    @Autowired
    public TemperatureServiceImpl(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @Override
    public List<Temperature> getAll(Long id) {
        List<Temperature> temperatures = temperatureRepository.findAllByUserId(id);
        return temperatures;
    }

    @Override
    public HttpStatus save(Temperature temperature) {
        temperatureRepository.save(temperature);
        return HttpStatus.OK;
    }

    @Override
    public Temperature findById(Long id) {
        Temperature temperature = temperatureRepository.findById(id).orElse(null);
        return temperature;
    }

    @Override
    public HttpStatus update(Temperature temperature, Long id) {
        Temperature up = temperatureRepository.findById(id).orElse(null);
        if (up == null) {
            return HttpStatus.NO_CONTENT;
        }
        up.setTemperature(temperature.getTemperature());
        temperatureRepository.save(temperature);
        return HttpStatus.OK;
    }
}

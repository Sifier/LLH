package com.linelife.demo.service.impl.constant;

import com.linelife.demo.model.constant.Pulse;
import com.linelife.demo.repository.constant.PulseRepository;
import com.linelife.demo.service.constant.PulseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PulseServiceImpl implements PulseService {

    private final PulseRepository pulseRepository;

    @Autowired
    public PulseServiceImpl(PulseRepository pulseRepository) {
        this.pulseRepository = pulseRepository;
    }

    @Override
    public List<Pulse> getAll(Long id) {
        List<Pulse> pulses = pulseRepository.findAllByUserId(id);
        return pulses;
    }

    @Override
    public HttpStatus save(Pulse pulse) {
        pulseRepository.save(pulse);
        return HttpStatus.OK;
    }

    @Override
    public Pulse findById(Long id) {
        Pulse pulse = pulseRepository.findById(id).orElse(null);
        return pulse;
    }

    @Override
    public HttpStatus update(Pulse pulse, Long id) {
        Pulse up = pulseRepository.findById(id).orElse(null);
        if (up == null) {
            return HttpStatus.NO_CONTENT;
        }
        up.setPulse(pulse.getPulse());
        pulseRepository.save(up);
        return HttpStatus.OK;
    }
}

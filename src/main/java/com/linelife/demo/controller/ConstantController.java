package com.linelife.demo.controller;

import com.linelife.demo.model.User;
import com.linelife.demo.model.constant.Pressure;
import com.linelife.demo.model.constant.Pulse;
import com.linelife.demo.model.constant.SleepTime;
import com.linelife.demo.model.constant.Temperature;

import com.linelife.demo.service.UserService;
import com.linelife.demo.service.constant.PressureService;
import com.linelife.demo.service.constant.PulseService;
import com.linelife.demo.service.constant.SleepTimeService;
import com.linelife.demo.service.constant.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "api/constant/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConstantController {

    private final PulseService pulseService;
    private final PressureService pressureService;
    private final SleepTimeService sleepTimeService;
    private final TemperatureService temperatureService;
    private final UserService userService;


    @Autowired
    public ConstantController(PulseService pulseService, PressureService pressureService, SleepTimeService sleepTimeService, TemperatureService temperatureService, UserService userService) {
        this.pulseService = pulseService;
        this.pressureService = pressureService;
        this.sleepTimeService = sleepTimeService;
        this.temperatureService = temperatureService;
        this.userService = userService;
    }

    public boolean validate(Long id){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = userService.findByUsername(username);
        if(user.getId()==id)
            return true;
        return false;
    }

    @GetMapping(value = "pressure/{id}")
    public ResponseEntity getAllPressure(@PathVariable Long id){

        if(validate(id)) {
            List<Pressure> pressures = pressureService.getAll(id);
            if (pressures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Link link = linkTo(ConstantController.class).slash("pressure").slash(id).withSelfRel();
            CollectionModel<Pressure> result = CollectionModel.of(pressures, link);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "pulse/{id}")
    public ResponseEntity getAllPulse(@PathVariable Long id){

        if(validate(id)) {
            List<Pulse> pulses = pulseService.getAll(id);
            if(pulses.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Link link = linkTo(ConstantController.class).slash("pulse").slash(id).withSelfRel();
            CollectionModel<Pulse> result = CollectionModel.of(pulses, link);
            return new ResponseEntity<>(pulses, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "sleep/{id}")
    public ResponseEntity getAllSleepTime(@PathVariable Long id){

        if(validate(id)) {
            List<SleepTime> sleeps = sleepTimeService.getAll(id);
            if (sleeps.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Link link = linkTo(ConstantController.class).slash("sleep").slash(id).withSelfRel();
            CollectionModel<SleepTime> result = CollectionModel.of(sleeps, link);
            return new ResponseEntity<>(sleeps, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "temperature/{id}")
    public ResponseEntity getAll(@PathVariable Long id){

        if(validate(id)) {
            List<Temperature> temperatures = temperatureService.getAll(id);
            if (temperatures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Link link = linkTo(ConstantController.class).slash("temperature").slash(id).withSelfRel();
            CollectionModel<Temperature> result = CollectionModel.of(temperatures, link);
            return new ResponseEntity<>(temperatures, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "pressure")
    public ResponseEntity setPressure(@RequestBody Pressure pressure){

        if(validate(pressure.getUserId())) {
            Link link = linkTo(ConstantController.class).slash("pressure").slash(pressure.getUserId()).withRel("all_pressure");
            return new ResponseEntity<>(link, pressureService.save(pressure));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping(value = "pulse")
    public ResponseEntity setPulse(@RequestBody Pulse pulse){

        if(validate(pulse.getUserId())) {
            Link link = linkTo(ConstantController.class).slash("pulse").slash(pulse.getUserId()).withRel("all_pulse");
            return new ResponseEntity<>(link, pulseService.save(pulse));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping(value = "sleep")
    public ResponseEntity setPressure(@RequestBody SleepTime sleepTime){

        if(validate(sleepTime.getUserId())) {
            Link link = linkTo(ConstantController.class).slash("sleep").slash(sleepTime.getUserId()).withRel("all_sleep");
            return new ResponseEntity<>(link, sleepTimeService.save(sleepTime));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping(value = "temperature")
    public ResponseEntity setPressure(@RequestBody Temperature temperature){

        if(validate(temperature.getUserId())) {
            Link link = linkTo(ConstantController.class).slash("temperature").slash(temperature.getUserId()).withRel("all_temperature");
            return new ResponseEntity<>(link, temperatureService.save(temperature));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "pressure/{id}")
    public ResponseEntity updatePressure(@RequestBody Pressure pressure, @PathVariable Long id){
        if(validate(id)) {
            Link link = linkTo(ConstantController.class).slash("pressure").slash(pressure.getUserId()).withRel("all_pressure");
            return new ResponseEntity<>(link, pressureService.update(pressure, id));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping(value = "pulse/{id}")
    public ResponseEntity updatePulse(@RequestBody Pulse pulse, @PathVariable Long id){
        if(validate(id)) {
            Link link = linkTo(ConstantController.class).slash("pulse").slash(pulse.getUserId()).withRel("all_pulse");
            return new ResponseEntity<>(link, pulseService.update(pulse, id));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping(value = "sleep/{id}")
    public ResponseEntity updateSleepTime(@RequestBody SleepTime sleepTime, @PathVariable Long id){
        if(validate(id)) {
            Link link = linkTo(ConstantController.class).slash("sleep").slash(sleepTime.getUserId()).withRel("all_sleep");
            return new ResponseEntity<>(link, sleepTimeService.update(sleepTime,id));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping(value = "temperature/{id}")
    public ResponseEntity updateTemperature(@RequestBody Temperature temperature, @PathVariable Long id){
        if(validate(id)) {
            Link link = linkTo(ConstantController.class).slash("temperature").slash(temperature.getUserId()).withRel("all_temperature");
            return new ResponseEntity<>(link, temperatureService.update(temperature,id));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

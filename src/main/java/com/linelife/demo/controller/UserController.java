package com.linelife.demo.controller;

import com.linelife.demo.model.ParametersUser;
import com.linelife.demo.model.PersonalUser;
import com.linelife.demo.model.User;
import com.linelife.demo.model.constant.Pressure;
import com.linelife.demo.service.ParametersUserService;
import com.linelife.demo.service.PersonalUserService;
import com.linelife.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "api/users/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final ParametersUserService parametersUserService;
    private final PersonalUserService personalUserService;
    private final UserService userService;


    @Autowired
    public UserController(ParametersUserService parametersUserService, PersonalUserService personalUserService, UserService userService) {
        this.parametersUserService = parametersUserService;
        this.personalUserService = personalUserService;
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

    @GetMapping(value = "parameters/{id}")
    public ResponseEntity<ParametersUser> getParameters(@PathVariable Long id){
        if(validate(id)) {
            ParametersUser parametersUser = parametersUserService.findById(id);
            if (parametersUser == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Link link = linkTo(ConstantController.class).slash("parameters").slash(id).withSelfRel();
            parametersUser.add(link);
            return new ResponseEntity<>(parametersUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "personal/{id}")
    public ResponseEntity<PersonalUser> getPersonal(@PathVariable Long id){

        if(validate(id)) {
            PersonalUser personalUser = personalUserService.findById(id);
            if (personalUser == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Link link = linkTo(ConstantController.class).slash("personal").slash(id).withSelfRel();
            personalUser.add(link);
            return new ResponseEntity<>(personalUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "parameters/{id}")
    public ResponseEntity<ParametersUser> updateParameters(@RequestBody ParametersUser parametersUser, @PathVariable Long id){

        if(validate(id)) {
            ParametersUser result = parametersUserService.findById(id);
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            result.setBirthDate(parametersUser.getBirthDate());
            result.setGender(parametersUser.getGender());
            result.setHeight(parametersUser.getHeight());
            result.setWeight(parametersUser.getWeight());
            Link link = linkTo(ConstantController.class).slash("parameters").slash(id).withSelfRel();
            ParametersUser pu = parametersUserService.update(result);
            pu.add(link);
            return new ResponseEntity<>(pu, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "personal/{id}")
    public ResponseEntity<PersonalUser> updatePersonal(@RequestBody PersonalUser personalUser, @PathVariable Long id){

        if(validate(id)) {
            PersonalUser result = personalUserService.findById(id);
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            result.setFullName(personalUser.getFullName());
            result.setAddress(personalUser.getAddress());
            result.setPolicy(personalUser.getPolicy());
            Link link = linkTo(ConstantController.class).slash("personal").slash(id).withSelfRel();
            PersonalUser pu = personalUserService.update(result);
            pu.add(link);
            return new ResponseEntity<>(pu, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}

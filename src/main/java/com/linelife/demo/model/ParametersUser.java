package com.linelife.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "parameters")
public class ParametersUser extends RepresentationModel<ParametersUser> {

    @Id
    @Column(name = "id")
    @JsonIgnore
    private Long id;
    @Column(name = "birth_date")
    @JsonProperty(value = "birth_date")
    private LocalDateTime birthDate;
    @Column(name = "gender")
    private String gender;
    @Column(name = "height")
    private Integer height;
    @Column(name = "weight")
    private double weight;
}

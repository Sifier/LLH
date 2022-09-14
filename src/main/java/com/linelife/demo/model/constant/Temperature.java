package com.linelife.demo.model.constant;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "temperature")
public class Temperature extends RepresentationModel<Temperature> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    @JsonProperty(value = "user_id")
    private Long userId;
    @Column(name = "date_time")
    @JsonProperty(value = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "temperature")
    private double temperature;
}

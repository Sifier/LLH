package com.linelife.demo.model.constant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sleep_time")
public class SleepTime extends RepresentationModel<SleepTime> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    @JsonProperty(value = "user_id")
    private Long userId;
    @JsonProperty(value = "date_time")
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "sleep_time")
    @JsonProperty(value = "sleep_time")
    private LocalDateTime sleepTime;
}

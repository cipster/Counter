package com.rainsoftware.application.controller;

import com.rainsoftware.application.domain.model.PersonCount;
import com.rainsoftware.application.model.PersonCountMetric;
import com.rainsoftware.application.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/count")
public class CountController {
    @Autowired
    private CountService personCountService;

    @PostMapping
    public PersonCount increasePersonCount(@RequestBody PersonCount personCount) {
        return personCountService.addPersonCount(personCount);
    }

    @GetMapping("/{firstDay}")
    public List<PersonCountMetric> all(@PathVariable String firstDay) {
        LocalDateTime localDateTime = LocalDateTime.parse(firstDay + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return personCountService.getMetrics(localDateTime);
    }
}

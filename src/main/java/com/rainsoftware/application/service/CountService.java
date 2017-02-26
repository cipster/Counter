package com.rainsoftware.application.service;

import com.rainsoftware.application.common.AuthUtils;
import com.rainsoftware.application.domain.model.PersonCount;
import com.rainsoftware.application.domain.repository.PersonCountRepository;
import com.rainsoftware.application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional
@Service
public class CountService {
    private PersonCountRepository personCountRepository;

    private LocalDateTime dayOne;
    private LocalDateTime dayTwo;
    private LocalDateTime dayThree;
    private LocalDateTime dayFour;
    private LocalDateTime dayFive;
    private LocalDateTime daySix;
    private LocalDateTime daySeven;
    private LocalDateTime dayEight;

    @Autowired
    public CountService(PersonCountRepository personCountRepository) {
        this.personCountRepository = personCountRepository;
        this.dayOne = LocalDateTime.parse("2017-02-25 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.dayTwo = LocalDateTime.parse("2017-02-26 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.dayThree = LocalDateTime.parse("2017-02-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.dayFour = LocalDateTime.parse("2017-02-28 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.dayFive = LocalDateTime.parse("2017-03-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.daySix = LocalDateTime.parse("2017-03-02 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.daySeven = LocalDateTime.parse("2017-03-03 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.dayEight = LocalDateTime.parse("2017-03-04 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public PersonCount addPersonCount(PersonCount personCount) {
        personCount.setUsername(AuthUtils.getLoggedInUsername());

        return personCountRepository.save(personCount);
    }

    public List<PersonCount> getAll() {
        return (List<PersonCount>) personCountRepository.findAll();
    }

    public List<PersonCountMetric> getMetrics() {
        return EnumSet.allOf(Poarta.class)
                .stream()
                .map(poarta -> {
                    PersonCountMetric metric = new PersonCountMetric();
                    metric.setPoarta(poarta);
                    Day dayOne = getDay(DayNumber.ONE, poarta);
                    Day dayTwo = getDay(DayNumber.TWO, poarta);
                    Day dayThree = getDay(DayNumber.THREE, poarta);
                    Day dayFour = getDay(DayNumber.FOUR, poarta);
                    Day dayFive = getDay(DayNumber.FIVE, poarta);
                    Day daySix = getDay(DayNumber.SIX, poarta);
                    Day daySeven = getDay(DayNumber.SEVEN, poarta);
                    Day dayEight = getDay(DayNumber.EIGHT, poarta);

                    metric.setDayOne(dayOne);
                    metric.setDayTwo(dayTwo);
                    metric.setDayThree(dayThree);
                    metric.setDayFour(dayFour);
                    metric.setDayFive(dayFive);
                    metric.setDaySix(daySix);
                    metric.setDaySeven(daySeven);
                    metric.setDayEight(dayEight);

                    return metric;
                }).collect(toList());
    }

    private Day getDay(DayNumber dayNumber, Poarta poarta) {
        Day day = new Day();
        LocalDateTime dateTime;

        switch (dayNumber) {
            case ONE:
                dateTime = this.dayOne;
                break;
            case TWO:
                dateTime = this.dayTwo;
                break;
            case THREE:
                dateTime = this.dayThree;
                break;
            case FOUR:
                dateTime = this.dayFour;
                break;
            case FIVE:
                dateTime = this.dayFive;
                break;
            case SIX:
                dateTime = this.daySix;
                break;
            case SEVEN:
                dateTime = this.daySeven;
                break;
            case EIGHT:
                dateTime = this.dayEight;
                break;
            default:
                throw new IllegalArgumentException("No such day");
        }
        Interval intervalOne = new Interval();
        intervalOne.setIn(personCountRepository.countByRecordDateBetweenAndPoartaAndDirection(getTimestampFromLocalDateTime(dateTime, IntervalType.FIRST_LOWER), getTimestampFromLocalDateTime(dateTime, IntervalType.FIRST_UPPER), poarta, Direction.IN));
        intervalOne.setOut(personCountRepository.countByRecordDateBetweenAndPoartaAndDirection(getTimestampFromLocalDateTime(dateTime, IntervalType.FIRST_LOWER), getTimestampFromLocalDateTime(dateTime, IntervalType.FIRST_UPPER), poarta, Direction.OUT));

        Interval intervalTwo = new Interval();
        intervalTwo.setIn(personCountRepository.countByRecordDateBetweenAndPoartaAndDirection(getTimestampFromLocalDateTime(dateTime, IntervalType.SECOND_LOWER), getTimestampFromLocalDateTime(dateTime, IntervalType.SECOND_UPPER), poarta, Direction.IN));
        intervalTwo.setOut(personCountRepository.countByRecordDateBetweenAndPoartaAndDirection(getTimestampFromLocalDateTime(dateTime, IntervalType.SECOND_LOWER), getTimestampFromLocalDateTime(dateTime, IntervalType.SECOND_UPPER), poarta, Direction.OUT));

        Interval intervalThree = new Interval();
        intervalThree.setIn(personCountRepository.countByRecordDateBetweenAndPoartaAndDirection(getTimestampFromLocalDateTime(dateTime, IntervalType.THIRD_LOWER), getTimestampFromLocalDateTime(dateTime, IntervalType.THIRD_UPPER), poarta, Direction.IN));
        intervalThree.setOut(personCountRepository.countByRecordDateBetweenAndPoartaAndDirection(getTimestampFromLocalDateTime(dateTime, IntervalType.THIRD_LOWER), getTimestampFromLocalDateTime(dateTime, IntervalType.THIRD_UPPER), poarta, Direction.OUT));

        day.setIntervalOne(intervalOne);
        day.setIntervalTwo(intervalTwo);
        day.setIntervalThree(intervalThree);
        return day;
    }

    private Timestamp getTimestampFromLocalDateTime(LocalDateTime day, IntervalType intervalType) {
        LocalDateTime localDateTime;
        switch (intervalType) {
            case FIRST_LOWER:
                localDateTime = day.plusHours(10);
                break;
            case FIRST_UPPER:
                localDateTime = day.plusHours(12);
                break;
            case SECOND_LOWER:
                localDateTime = day.plusHours(14);
                break;
            case SECOND_UPPER:
                localDateTime = day.plusHours(16);
                break;
            case THIRD_LOWER:
                localDateTime = day.plusHours(19);
                break;
            case THIRD_UPPER:
                localDateTime = day.plusHours(21);
                break;
            default:
                throw new IllegalArgumentException("No such interval");
        }
        return Timestamp.from(localDateTime.toInstant(ZoneOffset.ofHours(0)));
    }
}

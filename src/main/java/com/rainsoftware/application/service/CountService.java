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
    }

    public PersonCount addPersonCount(PersonCount personCount) {
        personCount.setUsername(AuthUtils.getLoggedInUsername());

        return personCountRepository.save(personCount);
    }

    public List<PersonCount> getAll() {
        return (List<PersonCount>) personCountRepository.findAll();
    }

    public List<PersonCountMetric> getMetrics(LocalDateTime firstDay) {
        this.dayOne = firstDay;
        this.dayTwo = this.dayOne.plusDays(1);
        this.dayThree = this.dayTwo.plusDays(1);
        this.dayFour = this.dayThree.plusDays(1);
        this.dayFive = this.dayFour.plusDays(1);
        this.daySix = this.dayFive.plusDays(1);
        this.daySeven = this.daySix.plusDays(1);
        this.dayEight = this.daySeven.plusDays(1);
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
        return Timestamp.from(localDateTime.toInstant(ZoneOffset.ofHours(2)));
    }
}

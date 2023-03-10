package it.tcgroup.vilear.course.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HolidayDate {

    private int day;
    private int month;
    private int year;

    public HolidayDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

}

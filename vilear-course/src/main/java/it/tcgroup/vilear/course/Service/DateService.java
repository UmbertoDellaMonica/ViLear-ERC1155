package it.tcgroup.vilear.course.Service;


import it.tcgroup.vilear.base.Payload.Enum.DayEnum;
import it.tcgroup.vilear.course.Model.HolidayDate;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;

public interface DateService {

    Calendar fromHolidayDateToCalendar(HolidayDate initHolidayDate);

    void addHolidayDate(HolidayDate holidayDate);

    // Imposto i Giorni di vacanza che io già conosco e sono sempre gli stessi ogni anno
    // Dovrà essere utilizzata solo una volta
    LinkedList<HolidayDate> createHolidayDate(int year);

    LinkedList<HolidayDate> getHolidayDateSet();

    HolidayDate getEasterDate(int year);

    DayEnum getDayOfWeek(HolidayDate holidayDate);

    HolidayDate fromDateToHolidayDate(Date date);


    Date fromHolidayDateToDate(HolidayDate holidayDate);

    /**
     * Mi restituisce il numero di giorni lavorativi
     * Calcolando dalla data di inizio e dalla data di fine
     *
     */
    Long getDayToEnd(HolidayDate holidayDate, HolidayDate endHolidayDate);

    HolidayDate plusDayToDate(HolidayDate initHolidayDate);

    boolean isHoliday(HolidayDate initHolidayDate);
}

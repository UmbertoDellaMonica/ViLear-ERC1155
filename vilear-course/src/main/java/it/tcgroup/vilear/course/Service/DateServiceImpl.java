package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Enum.DayEnum;
import it.tcgroup.vilear.course.Model.HolidayDate;
import it.tcgroup.vilear.course.Service.DateService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;


@Service
public class DateServiceImpl implements DateService {

    private LinkedList<HolidayDate> holidayDateSet ;

    /**
     * 1 Gennaio – Capodanno – sabato
     * 6 Gennaio – Epifania – giovedì
     * 17 Aprile – Pasqua – domenica
     * 18 Aprile – Lunedì dell’Angelo o di Pasquetta – lunedì
     * 25 aprile – Festa della liberazione – lunedì
     * 1 maggio – Festa dei lavoratori – domenica
     * 2 giugno – Festa della Repubblica – giovedì
     * 15 agosto – Ferragosto – lunedì
     * 1 novembre – Tutti i santi – martedì
     * 8 dicembre – Festa dell’Immacolata Concezione – giovedì
     * 25 dicembre – Natale – domenica
     * 26 dicembre – Santo Stefano – lunedì
     */

    @Override
    public HolidayDate fromDateToHolidayDate(Date timestamp) {
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        System.out.println("Calendar:  "+calendar.getTime());
        Date date=new Date(calendar.getTimeInMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1; // Aggiungo 1 poichè parto da zero
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new HolidayDate(day,month,year);
    }

    @Override
    public Date fromHolidayDateToDate(HolidayDate holidayDate) {
        Calendar c = fromHolidayDateToCalendar(holidayDate);
        Date timestamp = new Date(c.getTimeInMillis());
        return timestamp;
    }

    private HolidayDate fromCalendarToHolidayDate(Calendar toDateFromHoliday) {
        HolidayDate holidayDate=new HolidayDate();
        holidayDate.setYear(toDateFromHoliday.get(Calendar.YEAR));
        holidayDate.setMonth(toDateFromHoliday.get(Calendar.MONTH)+1);
        holidayDate.setDay(toDateFromHoliday.get(Calendar.DAY_OF_MONTH));
        return holidayDate;
    }

    @Override
    public Calendar fromHolidayDateToCalendar(HolidayDate initHolidayDate) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, initHolidayDate.getYear());
        c.set(Calendar.MONTH, initHolidayDate.getMonth() - 1);
        c.set(Calendar.DAY_OF_MONTH, initHolidayDate.getDay());
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    // Imposto i Giorni di vacanza che io già conosco e sono sempre gli stessi ogni anno
    // Dovrà essere utilizzata solo una volta
    @Override
    public LinkedList<HolidayDate> createHolidayDate(int year){
        // Imposta i giorni festivi
        if(this.holidayDateSet==null){
            this.holidayDateSet=new LinkedList<>();
        }
        this.holidayDateSet.add(new HolidayDate(1,1,year));// Capodanno
        this.holidayDateSet.add(new HolidayDate(6,1,year)); // Epifania
        this.holidayDateSet.add(new HolidayDate(25,4,year)); // Festa della Liberazione
        this.holidayDateSet.add(new HolidayDate(1,5,year)); // Festa dei Lavoratori
        this.holidayDateSet.add(new HolidayDate(2,6,year)); // Festa della Repubblica
        this.holidayDateSet.add(new HolidayDate(15,8,year)); // ferragosto
        this.holidayDateSet.add(new HolidayDate(1,11,year)); // Giorno dei Morti
        this.holidayDateSet.add(new HolidayDate(8,12,year)); // Immacolata
        this.holidayDateSet.add(new HolidayDate(25,12,year)); // Natale
        this.holidayDateSet.add(new HolidayDate(26,12,year)); // S.Stefano
        this.holidayDateSet.add(getEasterDate(year)); // Pasqua

        HolidayDate holidayDate=getEasterDate(year);
        holidayDate.setDay(holidayDate.getDay()+1);

        this.holidayDateSet.add(holidayDate); // Pasquetta
        return this.holidayDateSet;
    }

    @Override
    public LinkedList<HolidayDate> getHolidayDateSet() {
        if (this.holidayDateSet == null) {
            this.holidayDateSet=new LinkedList<>();
        }
            return this.holidayDateSet;
    }

    @Override
    public HolidayDate getEasterDate(int year){
        int a,b,c,d,e,f,g,h,i,k,l,m,n,p;
        int iDay,iMonth;

        a=year % 19;
        b=(int) (year/100);
        c=year % 100;
        d=(int) (b/4);
        e=b % 4;
        f=(int) ((b+8)/25);
        g=(int) ((b-f+1)/3);
        h=(19 * a+b-d-g+15) % 30;
        i=(int) (c/4);
        k=c % 4;
        l=(32+2*e+2*i-h-k) % 7;
        m=(int) ((a+11*h-221)/451);
        n=(int) (h+l-7*m+114) / 31;
        p=(h+l-7*m+114) % 31;

        iDay=p+1;
        iMonth=n;
        return new HolidayDate(iDay,iMonth,year);
    }

    @Override
    public DayEnum getDayOfWeek(HolidayDate holidayDate) {
        return getGiornoSettimana(creaData(holidayDate.getDay(), holidayDate.getMonth(), holidayDate.getYear()));
    }

    @Override
    public void addHolidayDate(HolidayDate holidayDate){
        if (this.holidayDateSet == null) {
            this.holidayDateSet=new LinkedList<>();
        }else {
            if (getHolidayDateSet().contains(holidayDate)) {
                return;
            } else {
                this.holidayDateSet.add(holidayDate);
            }
        }
    }

    //--------------------------------------//

    public  DayEnum getGiornoSettimana(Date data){
        DayEnum retVal = null;
        int anno=getAnnoDaData(data);
        int val1=anno+getPrimoElemento(anno);
        int giorniTrascorsi=getGiorniFromInizioAnno(data);
        int r=val1+giorniTrascorsi;
        int resto=r%7;
        switch(resto){
            case 0:
                retVal= DayEnum.SABATO;
                break;
            case 1:
                retVal=DayEnum.DOMENICA;
                break;
            case 2:
                retVal=DayEnum.LUNEDI;
                break;
            case 3:
                retVal=DayEnum.MARTEDI;
                break;
            case 4:
                retVal=DayEnum.MERCOLEDI;
                break;
            case 5:
                retVal=DayEnum.GIOVEDI;
                break;
            case 6:
                retVal=DayEnum.VENERDI;
                break;

        }
        return retVal;
    }

    private Date creaData(int giorno, int mese, int anno){
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR, anno);
        c.set(Calendar.MONTH, mese-1);
        c.set(Calendar.DAY_OF_MONTH, giorno);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date date=new Date(c.getTimeInMillis());
        return date;
    }

    private int getGiornoDaData(Date data){
        Calendar c=Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    private int getMeseDaData(Date data){
        Calendar c=Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.MONTH)+1;
    }

    private int getAnnoDaData(Date data){
        Calendar c=Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.YEAR);
    }

    private boolean isLeapYear(int anno){
        GregorianCalendar gc=new GregorianCalendar();
        return gc.isLeapYear(anno);
    }

    private int getGiorniFromInizioAnno(Date d){
        int anno=getAnnoDaData(d);
        boolean bisestile=isLeapYear(anno)?true:false;
        int retVal=0;
        int mese=getMeseDaData(d);
        int giorno=getGiornoDaData(d);
        for(int i=1;i<mese;i++){
            switch(i){
                case 2:
                    retVal+=bisestile?29:28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    retVal+=30;
                    break;
                default:
                    retVal+=31;
                    break;
            }
        }
        retVal+=giorno;
        return retVal;
    }

    private int getPrimoElemento(int anno){
        return getElemento(anno-1, 4)-getElemento(anno-1, 100)+getElemento(anno-1, 400);
    }

    private int getElemento(int anno,int div){
        int r=(int)Math.floor((double)anno/(double)div);
        return r;
    }

    @Override
    public Long getDayToEnd(HolidayDate holidayDate, HolidayDate endHolidayDate) {
        GregorianCalendar firstDate= new GregorianCalendar(holidayDate.getYear(),holidayDate.getMonth()-1,holidayDate.getDay());
        GregorianCalendar secondDate= new GregorianCalendar(endHolidayDate.getYear(),endHolidayDate.getMonth()-1,endHolidayDate.getDay());
        long millisElapsed =
                (secondDate.getTime()).getTime()
                        -
                        (firstDate.getTime()).getTime();
        long returnVal =  millisElapsed / (24*60*60 * 1000);
        return returnVal;
    }

    @Override
    public HolidayDate plusDayToDate(HolidayDate initHolidayDate) {
        Calendar toDateFromHoliday = fromHolidayDateToCalendar(initHolidayDate);
        toDateFromHoliday.add(Calendar.DATE,1);
        return fromCalendarToHolidayDate(toDateFromHoliday);
    }

    @Override
    public boolean isHoliday(HolidayDate initHolidayDate) {
        for(HolidayDate date: this.holidayDateSet) {
            if (
                    date.getDay() == initHolidayDate.getDay() &&
                    date.getMonth() == initHolidayDate.getMonth() &&
                    date.getYear() == initHolidayDate.getYear()
            ) {
                return true;
            }
        }
        return false;
    }

}

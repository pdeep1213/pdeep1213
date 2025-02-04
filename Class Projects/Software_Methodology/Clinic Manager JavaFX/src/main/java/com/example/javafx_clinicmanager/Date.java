package com.example.javafx_clinicmanager;
import java.util.Calendar;

/**
 *The Date Class models the Date system for the logic of the software
 * @author Deep Patel,Manan Patel
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;


    //Leap Year
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    //Days
    public static final int THIRTYDAYMONTH = 30;
    public static final int NONLEAPFEB = 28;
    public static final int LEAPFEB = 29;
    public static final int OTHERDAYMONTH = 31;


    /**
     * Default constructor that start the Date object.
     * @param year year of the date
     * @param month month of the date
     * @param day day of the date
     */
    public Date(int year, int month, int day){
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Use to Check if the Year is Leap year
     * @param year the year being checked
     * @return true if the year is leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    return true;  // Divisible by 400
                } else {
                    return false; // Divisible by 100 but not 400
                }
            } else {
                return true;  // Divisible by 4 but not 100
            }
        }
        return false;  // Not divisible by 4
    }


    /**
     * Checks if the date is Valid Calendar date
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid(){
        int daysInMonth;
        month--;
        // Validate month
        if (month < Calendar.JANUARY  || month > Calendar.DECEMBER)
        {
            month++;
            return false;
        }
        // Adjust February days for leap years
        if((month == Calendar.APRIL) || (month == Calendar.JUNE) || (month == Calendar.SEPTEMBER) || (month == Calendar.NOVEMBER)){
            daysInMonth = THIRTYDAYMONTH;
        } else {
            daysInMonth = OTHERDAYMONTH;
        }
        if (month == Calendar.FEBRUARY) {
            if (isLeapYear(year)) {
                daysInMonth = LEAPFEB;
            }
            else{
                daysInMonth = NONLEAPFEB;
            }
        }
        month++;
        // Validate day
        return day > 0 && day <= daysInMonth;
    }

    /**
     * Checks if the date is before today
     * @return true if before today, false otherwise
     */
    public boolean isBefore() {
        Calendar calendar = Calendar.getInstance();
        if(this.year < calendar.get(Calendar.YEAR)){
            return true;
        }
        if(this.year == calendar.get(Calendar.YEAR)){
            if(this.month < calendar.get(Calendar.MONTH)+1){
                return true;
            }

            if(this.month == calendar.get(Calendar.MONTH)+1){
                if(this.day < calendar.get(Calendar.DAY_OF_MONTH)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the Date is after today
     * @return true if today or after, false otherwise
     */
    public boolean isAfter() {
        Calendar calendar = Calendar.getInstance();
        if(this.year >= calendar.get(Calendar.YEAR)){
            if(this.month >= calendar.get(Calendar.MONTH)+1){
                if(this.day > calendar.get(Calendar.DAY_OF_MONTH)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the Date is a Weekend
     * @return true if its Sunday or Saturday, false otherwise
     */
    public boolean isWeekend() {
        Calendar calendar = Calendar.getInstance();
        // Set the calendar fields to the date's year, month, and day
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.MONTH, this.month - 1); // Month is 0-based in Calendar
        calendar.set(Calendar.DAY_OF_MONTH, this.day);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Check if it's Saturday or Sunday
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    /**
     * Checks if the Date is within Six Months from today
     * @return true if within six months, false otherwise
     */
    public boolean isWithinSixMonths() {
        // Get the current date (today)
        Calendar today = Calendar.getInstance();

        // Create a calendar instance for the date six months from today
        Calendar sixMonthsLater = Calendar.getInstance();
        sixMonthsLater.add(Calendar.MONTH, 6);

        // Create a calendar instance for the given date (this object)
        Calendar appointmentDate = Calendar.getInstance();
        appointmentDate.set(this.year, this.month - 1, this.day); // Adjusting month since Calendar.MONTH is zero-based

        // Check if the appointment date is between today and six months later
        return appointmentDate.after(today) && appointmentDate.before(sixMonthsLater);
    }

    /**
     * Check if this Date is today
     * @return true if today, false otherwise
     */
    public boolean isToday() {
        Calendar calendar = Calendar.getInstance();
        return this.day == calendar.get(Calendar.DAY_OF_MONTH) && this.month == (calendar.get(Calendar.MONTH)+1) && this.year == calendar.get(Calendar.YEAR);

    }

    /**
     * Compare this Date to other Date
     * @param other the object to be compared.
     * @return 1 if after the other, -1 if before the other, 0 if both dates are same
     */
    @Override
    public int compareTo(Date other) {
        // Compare year first
        if (this.year != other.year) {
            return this.year - other.year;
        }

        // If years are equal, compare month
        if (this.month != other.month) {
            return this.month - other.month;
        }

        // If both year and month are equal, compare day
        return this.day - other.day;
    }

    /**
     * Checks if this Date is equal to object
     * @param obj
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Date other){
            return this.day == other.day && this.year == other.year && this.month == other.month;
        }
        return false;
    }

    /**
     * Textual Representation of Date
     * @return String format of date
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }

}

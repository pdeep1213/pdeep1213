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


    /**
     * Default constructor
     * @param year
     * @param month
     * @param day
     */
    public Date(int year, int month, int day){
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Use to Chech if the Year is Leap year
     * @param year
     * @return
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
     * @return
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
            daysInMonth = 30;
        } else {
            daysInMonth = 31;
        }
        if (month == Calendar.FEBRUARY) {
            if (isLeapYear(year)) {
                daysInMonth = 29;
            }
            else{
                daysInMonth = 28;
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
        if(this.year <= calendar.get(Calendar.YEAR)){
            if(this.month <= calendar.get(Calendar.MONTH)+1){
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
        return this.day == calendar.get(Calendar.DAY_OF_MONTH) && this.month == calendar.get(Calendar.MONTH) && this.year == calendar.get(Calendar.YEAR);
    }

    /**
     * Compare this Date to other Date
     * @param other the object to be compared.
     * @return 1 if greater, -1 if smaller, 0 if equal
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
     * @return String
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }

    /**
     * Helper method for testing
     * @param date
     */
    private static void testDate(Date date) {
        if (date.isValid()) {
            System.out.println(date.toString() + " is a valid Date");
        } else {
            System.out.println(date.toString() + " is an Invalid Date");
        }
    }

    /**
     * Testbed main() to test the Date isValid() method
     * @param args
     */
    public static void main(String args[]){
        System.out.println("This is the Start of TestBed");

        //Testing Valid Dates
        System.out.println("\nChecking Valid Dates");
        testDate(new Date(2024, 1, 1));    // Valid
        testDate(new Date(2024, 2, 28));   // Valid
        testDate(new Date(2024, 2, 29));   // Valid
        testDate(new Date(2024, 3, 31));   // Valid
        testDate(new Date(2024, 4, 30));   // Valid
        testDate(new Date(2024, 5, 15));   // Valid
        testDate(new Date(2024, 6, 30));   // Valid
        testDate(new Date(2024, 7, 31));   // Valid
        testDate(new Date(2024, 8, 31));   // Valid
        testDate(new Date(2024, 9, 30));   // Valid
        testDate(new Date(2024, 10, 31));  // Valid
        testDate(new Date(2024, 11, 30));  // Valid
        testDate(new Date(2024, 12, 31));  // Valid

        // Invalid Dates
        System.out.println("\nTesting Invalid Dates");
        testDate(new Date(2023, 2, 29));   // Invalid
        testDate(new Date(2024, 4, 31));   // Invalid
        testDate(new Date(2024, 6, 31));   // Invalid
        testDate(new Date(2024, 9, 31));   // Invalid
        testDate(new Date(2024, 11, 31));  // Invalid
        testDate(new Date(2024, 1, 0));    // Invalid
        testDate(new Date(2024, 2, 30));   // Invalid
        testDate(new Date(2021, 2, 29));   // Invalid
        testDate(new Date(0, 1, 1));        // Invalid
        testDate(new Date(2024, 2, -1));    // Invalid

        // Boundary Cases
        System.out.println("\nTesting Boundary Cases");
        testDate(new Date(2024, 1, 1));    // Valid
        testDate(new Date(2024, 12, 31));  // Valid
        testDate(new Date(2024, 13, 1));   // Invalid (invalid month index)

        // Leap Year Tests
        System.out.println("\nTesting Leap Year Cases");
        testDate(new Date(2000, 2, 29));   // Valid
        testDate(new Date(1900, 2, 29));   // Invalid
        testDate(new Date(2016, 2, 29));   // Valid
        testDate(new Date(2028, 2, 29));


        testDate(new Date(2025,2,28));



    }
}

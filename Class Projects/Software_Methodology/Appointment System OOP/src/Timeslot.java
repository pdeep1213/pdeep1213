/**
 * The Timeslot enum represents different time slot for appointment.
 * @author Deep Patel, Manan Patel
 */
public enum Timeslot{
    SLOT1(9,0),
    SLOT2(10, 45),
    SLOT3(11, 15),
    SLOT4(13, 30),
    SLOT5(15, 0),
    SLOT6(16, 15);

    private final int hour;
    private final int minute;

    /**
     * Constructor for the timeslot enum.
     * @param hour time by hour for the timeslot
     * @param minute time by minute for the timeslot
     */
    Timeslot(int hour,int minute){
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns timeslot in a string format.
     * @return a string in format "SlotName, Hour, Min, Period"
     */
    @Override
    public String toString(){
        int hour12 = (hour == 0 || hour == 12) ? 12 : hour % 12;
        String period = (hour < 12) ? "AM" : "PM";
        return String.format("%d:%02d %s", hour12, minute, period);
    }
}

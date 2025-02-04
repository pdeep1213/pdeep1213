import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * The Scheduler Class is the user interface part of the Appointment software, where command are given.
 * @author Deep Patel, Manan Patel
 */
public class Scheduler {

    List list = new List();

    /**
     * The main starting method of the whole software, which takes commands and directs the program in the right direction.
     */
    public void run() {
        System.out.println("Scheduler is running.");
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine().trim(); // Read command line input

            if (input.isEmpty()) {
                continue; // Skip empty lines
            }

            String[] tokens = input.split(",");
            String command = tokens[0];
            switch (command) {
                case "S": // Schedule an appointment
                    handleScheduleCommand(tokens);
                    break;
                case "C":
                    handleCancelCommand(tokens);
                    break;
                case "R":
                    handleRescheduleCommand(tokens);
                    break;
                case "PA":
                    handlePA();
                    break;
                case "PL":
                    handlePL();
                    break;
                case "PP":
                    handlePP();
                    break;
                case "PS":
                    handlePriceList();
                    break;
                case "Q": // Quit the scheduler
                    System.out.println("Scheduler terminated.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

    /**
     * This helper method is used to convert the String Date into instance of Date class.
     * @param str_date
     * @return Date
     */
    private Date giveDate(String str_date){
        String[] token = str_date.split("/");
        int month = Integer.parseInt(token[0]);
        int day = Integer.parseInt(token[1]);
        int year = Integer.parseInt(token[2]);
        Date date = new Date(year,month,day);

        return date;
    }

    /**
     * This helper method is used to convert String Timeslot into instance of Timeslot class
     * @param str_Time
     * @return Timeslot
     */
    private Timeslot giveTimeSlot(String str_Time){
        switch (str_Time) {
            case "1" -> {
                return Timeslot.SLOT1; // 9:00 am
            }
            case "2" -> {
                return Timeslot.SLOT2; // 10:45 am
            }
            case "3" -> {
                return Timeslot.SLOT3; // 11:15 am
            }
            case "4" -> {
                return Timeslot.SLOT4; // 1:30 pm
            }
            case "5" -> {
                return Timeslot.SLOT5; // 3:00 pm
            }
            case "6" -> {
                return Timeslot.SLOT6; // 4:15 pm
            }
            default -> {
                System.out.println(str_Time + " is not a valid time slot");
                return null; // Return null if an invalid timeslot is provided
            }
        }
    }

    /**
     * This helper method is used to convert String Provider into instance of Provider class
     * @param str_Provider
     * @return Timeslot
     */
    private Provider giveProvider(String str_Provider) {

        switch (str_Provider.toUpperCase()) {
            case "PATEL" -> {
                return Provider.PATEL; // Bridgewater, Family
            }
            case "LIM" -> {
                return Provider.LIM; // Bridgewater, Pediatrician
            }
            case "ZIMNES" -> {
                return Provider.ZIMNES; // Clark, Family
            }
            case "HARPER" -> {
                return Provider.HARPER; // Clark, Family
            }
            case "KAUR" -> {
                return Provider.KAUR; // Princeton, Allergist
            }
            case "TAYLOR" -> {
                return Provider.TAYLOR; // Piscataway, Pediatrician
            }
            case "RAMESH" -> {
                return Provider.RAMESH; // Morristown, Allergist
            }
            case "CERAVOLO" -> {
                return Provider.CERAVOLO; // Edison, Pediatrician
            }
            default -> {
                System.out.println(str_Provider +  " - provider doesn't exist");
                return null; // Return null if no matching provider is found
            }
        }
    }

    /**
     * This helper method checks if the given Appointment date is valid or not. Also prints correct error message.
     * @param appDate
     * @return true if appDate is a valid Calendar Date, false otherwise
     */
    private boolean isValidAppoinment(Date appDate) {
        if (appDate.isValid()) {
            if (!appDate.isBefore() || appDate.isToday()) {
                if (!appDate.isWeekend()) {
                    if (appDate.isWithinSixMonths()) return true;
                    else System.out.println("Appointment Date : " + appDate.toString() + " is not within six months.");
            }   else System.out.println("Appointment Date : " + appDate.toString() + " is Saturday or Sunday.");
        }   else System.out.println("Appointment Date : " + appDate.toString() + " is today or date before today.");
    }   else System.out.println("Appointment Date : " + appDate.toString() + " is not a Valid Date");

        return false;
    }

    /**
     * This helper method checks of the Date of Birth of the patient is valid Calendar date, and print error messages accordingly
     * @param dob
     * @return true if dob is valid, false otherwise
     */
    private boolean validDob(Date dob) {
        if(dob.isValid()){
            if(!dob.isAfter()) {
                return true;
            }
            else System.out.println("Patient dob: " + dob.toString() + " is today or date after today.");
        }
        else System.out.println("Patient dob: " + dob.toString() + " is not a valid Calendar date.");

        return false;
    }

    /**
     * This helper method handles the Schedule Command(S)
     * @param tokens Contains all necessary info to add an appointment
     */
    private void handleScheduleCommand(String[] tokens){
        if (tokens.length != 7) {
            System.out.println("Invalid schedule command format.");
            return;
        }

        // Process the scheduling logic
        Profile patient = new Profile(tokens[3],tokens[4],giveDate(tokens[5]));

        Date appDate = giveDate(tokens[1]);
        Timeslot slot = giveTimeSlot(tokens[2]);
        Provider provider = giveProvider(tokens[6]);
        if(isValidAppoinment(appDate)) {
            if(slot != null) {
                if(validDob(giveDate(tokens[5]))){
                    if(provider != null){
                        if(list.providerAvail(provider, slot, appDate)){
                            Appointment appointment = new Appointment(appDate, slot, patient, provider);
                            int beforeAdd = list.getSize();
                            list.add(appointment);
                            if(list.getSize() > beforeAdd) System.out.println(appointment.toString() + " booked.");
                            else System.out.println(appointment.getPatient().toString() + " has an existing appointment at the same time slot");
                        } else System.out.println(provider.toString() + " is not available at slot " + slot.toString());
                    }
                }
            }
        }

    }

    /**
     * This helper method handles the Cancle Command(C)
     * @param tokens Contains all necessary info to remove an appointment
     */
    private void handleCancelCommand(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Invalid cancel command format.");
            return;
        }
        // Process the cancel logic
        Date appDate = giveDate(tokens[1]);
        Timeslot slot = giveTimeSlot(tokens[2]);
        Date dob = giveDate(tokens[5]);
        Profile patient = new Profile(tokens[3],tokens[4],dob);
        Provider provider = giveProvider(tokens[6]);

        Appointment appointment = new Appointment(appDate, slot, patient, provider);
        int beforerm = list.getSize();
        list.remove(appointment);
        if(list.getSize() < beforerm) System.out.println(appointment.getDate().toString() + " " + appointment.gettimeslot().toString() + " " + appointment.getPatient().toString() + " has been canceled");
        else System.out.println(appointment.getDate().toString() + " " + appointment.gettimeslot().toString() + " " + appointment.getPatient().toString() + " does not exist");


    }

    /**
     * This helper method handles the Reschedule Command
     * @param tokens Contains all necessary info to reschedule an appointment
     */
    private void handleRescheduleCommand(String[] tokens){
        if (tokens.length != 7) {
            System.out.println("Invalid Reschedule command format.");
            return;
        }

        Date appDate = giveDate(tokens[1]);
        Timeslot slot = giveTimeSlot(tokens[2]);
        Date dob = giveDate(tokens[5]);
        Profile patient = new Profile(tokens[3],tokens[4],dob);


        int index = list.patientApp(patient,slot,appDate);

        if(index != -1){
            Timeslot newSlot = giveTimeSlot(tokens[6]);
            if(slot != null && newSlot != null && isValidAppoinment(appDate) && validDob(dob)){
                Appointment appointment = new Appointment(appDate, newSlot, patient, list.getAppointmentatIndex(index).getProvider());
                list.remove(list.getAppointmentatIndex(index));
                list.add(appointment);
                System.out.println("Rescheduled to " + appointment.toString());
            }
        }
        else {
            System.out.println(appDate.toString() + " " + slot.toString() + " " + patient.toString() + " does not exist");
        }
    }

    /**
     * This helper method handles the Print List Command(PS)
     */
    private void handlePriceList(){
        MedicalRecord record = new MedicalRecord();
        list.sortByPatientProfile();
        for(int i = 0; i < list.getSize();i++){
           Visit visit = new Visit(list.getAppointmentatIndex(i));
            Profile profile = list.getAppointmentatIndex(i).getPatient();
           if(record.findPatient(profile) == -1) {
               Patient patient = new Patient(list.getAppointmentatIndex(i).getPatient(),visit);
               record.add(patient);

           }
           else
           {
               record.getPatient(record.findPatient(profile)).addVisits(visit);
           }
        }
        list.clear();
        System.out.println("** Billing statement ordered by patient **");
        DecimalFormat df = new DecimalFormat("0.00");
        for(int i = 0;i < record.getSize();i++){
            String charge = df.format(record.getPatient(i).charge());
            System.out.println("(" + i + ")"+record.getPatient(i).getProfile().toString() + " [amount due: $" +  charge + "]");
        }
        System.out.println("** end of list **\n");
    }

    /**
     * This helper method handles the Print List Command(PA)
     */
    private void handlePA() {
        if(list.getSize() == 0){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        list.printByAppointment();
        System.out.println("** Appointments ordered by date/time/provider **");
        printlist();
    }

    /**
     * This helper method handles the Print List Command(PP)
     */
    private void handlePP() {
        if(list.getSize() == 0){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        list.printByPatient();
        System.out.println("**  Appointments ordered by patient/date/time **");

        printlist();
    }

    /**
     * This helper method handles the Print List Command(PL)
     */
    private void handlePL() {
        if(list.getSize() == 0){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        list.sortByLocation();
        System.out.println("** Appointments ordered by county/date/time **");
        printlist();
    }

    /**
     * This helper method prints the current List
     */
    private void printlist(){
        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.getAppointmentatIndex(i).toString());
        }
        System.out.println("** end of list **\n");
    }

}

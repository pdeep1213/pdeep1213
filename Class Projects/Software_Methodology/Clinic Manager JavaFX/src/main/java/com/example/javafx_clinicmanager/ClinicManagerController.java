package com.example.javafx_clinicmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * The Controller class is a management class for the Clinic Manager JavaFX application.
 * It controls all the elements for the software
 *
 * @author Deep Patel, Manan Patel
 */
public class ClinicManagerController implements Initializable {


    public TextArea consele_R;
    public TextArea consele_P;
    public RadioButton radiology_Print,office_Print,pa_print,pp_Print,credit_print,bill_print;
    @FXML
    private DatePicker AppointmentDate;
    public TextField patientFirstName;
    public TextField patientLastName;
    public DatePicker dob;
    public ComboBox<Timeslot> slot;
    public Button loadProvider;
    public ComboBox<Provider> provider;
    public Button scheduleButton;
    public TextArea consele;
    public ToggleGroup appointmentType;
    public RadioButton doctor;
    public RadioButton imaging;
    public TextField rescheduleFirstName;
    public TextField rescheduleLastName;
    public DatePicker rescheduleDob;
    public ComboBox<Timeslot> oldSlot;
    public DatePicker oldAppointmentDate;
    public ComboBox<Timeslot> newSlot;
    public ComboBox<Radiology> radiologyRoom;
    public TableView<Location> location_tbl;
    @FXML
    private TableColumn<Location,String> col_city,col_county,col_zip;
    @FXML
    private ToggleGroup printType;


    private List<Appointment> appointmentList;
    private List<Provider> providersList;
    private List<Technician> technicianList;
    private List<Doctor> doctorList;
    private RotationList rotation = new RotationList();


    /**
     * This FXML method handles the button click when the load provider button is clicked in schedule tab.
     */
    @FXML
    protected void onLoadProviderClick(){
        File providersFile = new File("src/main/java/com/example/javafx_clinicmanager/providers.txt"); // Path to the providers file

        if (providersFile.exists()) {
            loadProviders(providersFile); // Call the method to load providers
            loadProvider.setDisable(true); // Disable the button after loading
        } else {
            consele.setText("Providers file not found!"); // Show an error message if the file does not exist
        }
        loadProvider.setDisable(true);
        consele.setText("Rotation list for the technicians.\n");
        makeRotationList();
    }

    /**
     * This FXML method handles the functionality behind the scheduling the appointment when the button is clicked
     */
    @FXML
    protected void onScheduleClick(){
        try{
                Date appDate = new Date(AppointmentDate.getValue().getYear(),AppointmentDate.getValue().getMonthValue(),AppointmentDate.getValue().getDayOfMonth());
                Date patientDob = new Date(dob.getValue().getYear(),dob.getValue().getMonthValue(),dob.getValue().getDayOfMonth());
                if(checkappDate(appDate) && checkDOB(patientDob)){
                    Patient patient = new Patient(new Profile(patientFirstName.getText(),patientLastName.getText(),patientDob));
                    if(!checkAppExist(patient.profile,appDate,slot.getValue())){
                        if(imaging.isSelected()) handleImgAppointment(appDate,slot.getValue(), patient);
                        if(doctor.isSelected()) handleOfficeAppointment(appDate,slot.getValue(),patient);
                        handleClear();
                    }
                    else {consele.appendText(patient.toString() + " has an existing appointment at the same time slot.\n");}
                }

        } catch (NullPointerException e) {
                consele.appendText("Missing Data\n");
        }

    }

    /**
     * This class handles the viewing and edibility of the appointment type
     */
    @FXML
    protected void onServiceSelect(){
        if(imaging.isSelected()){ provider.setDisable(true); radiologyRoom.setVisible(true);}
        if(doctor.isSelected()) {provider.setDisable(false); radiologyRoom.setVisible(false);}
    }

    /**
     * This class does the initial setup of the GUI, like loading the timeslots and location upon starting the program.
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appointmentList = new List<Appointment>();
        providersList = new List<Provider>();
        doctorList = new List<Doctor>();
        technicianList = new List<Technician>();

        radiologyRoom.setItems(FXCollections.observableArrayList(Radiology.values()));
        slot.setItems(FXCollections.observableArrayList(getArrOfSlot()));
        oldSlot.setItems(FXCollections.observableArrayList(getArrOfSlot()));
        newSlot.setItems(FXCollections.observableArrayList(getArrOfSlot()));


        ObservableList<Location> locations = FXCollections.observableArrayList(Location.values());
        location_tbl.setItems(locations);
        col_zip.setCellValueFactory(new PropertyValueFactory<>("zip"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        col_county.setCellValueFactory(new PropertyValueFactory<>("county"));

    }

    /**
     * helper method to get the timeslots in an array form
     * @return Array of Timeslots provided by the clinic
     */
    private Timeslot[] getArrOfSlot(){
        Timeslot[] slots = new Timeslot[12];
        for(int i = 1; i < 13;i++){
            int hour, minute;

            if (i <= 6) hour = 9 + (i - 1) / 2;
            else hour = 14 + (i - 7) / 2;

            minute = (i % 2 == 1) ? 0 : 30;
            slots[i-1] = new Timeslot(hour,minute);
        }
        return slots;
    }

    /**
     * This FXML method handles the functionality behind the clearing the appointment info when the button is clicked
     */
    @FXML
    protected void handleClear(){
        // Clear text fields
        patientFirstName.clear();
        patientLastName.clear();
        dob.getEditor().clear();

        // Reset DatePicker for appointment date
        AppointmentDate.setValue(null);
        // Reset RadioButtons in the ToggleGroup
        appointmentType.selectToggle(null);

    }

    /**
     * This FXML method handles the functionality behind the printing the appointment list when the button is clicked
     */
    @FXML
    protected void OnPrintButton(){
        if(printType.getSelectedToggle().equals(office_Print)) handlePrintOffice();
        if(printType.getSelectedToggle().equals(radiology_Print)) handlePrintImaging();
        if(printType.getSelectedToggle().equals(pa_print)) handlePrint("PTD");
        if(printType.getSelectedToggle().equals(pp_Print)) handlePrint("TDp");
        if(printType.getSelectedToggle().equals(bill_print)) handlePrintBilling();
        if(printType.getSelectedToggle().equals(credit_print)) handlePrintCredit();
    }

    /**
     * This handler method handles the command(D) to make an appointment with a Doctor
     *
     * @param appDate Appointment Date
     * @param slot Time of Appointment
     * @param patient Patient profile
     */
    private void handleOfficeAppointment(Date appDate, Timeslot slot, Patient patient){
        Provider doc = provider.getValue();
        if(docAvial((Doctor) doc,slot,appDate)) {
            Appointment appointment = new Appointment(appDate, slot, patient, doc);
            appointmentList.add(appointment);
            if(appointmentList.contains(appointment)) consele.appendText(appointment.toString() + " booked.\n");
        }
    }

    /**
     * This handler method handles the command(D) to make an appointment for Imaging
     *
     * @param appDate Appointment Date
     * @param slot Time of Appointment
     * @param patient Patient profile
     */
    private void handleImgAppointment(Date appDate, Timeslot slot, Patient patient){
            Technician technician = checkImgAvail(slot, (Radiology) radiologyRoom.getValue(),appDate);
            if(technician != null) {
                Appointment appointment = new Imaging(appDate,slot,patient,technician,(Radiology) radiologyRoom.getValue());
                appointmentList.add(appointment);
                if(appointmentList.contains(appointment)) consele.appendText(appointment.toString() + " booked.\n");
            }

    }

    /**
     * This helper method is used to convert the String Date into instance of Date class.
     * @param str_date String version of Date
     * @return Date
     */
    private Date giveDate(String str_date){
        String[] token = str_date.split("/");
        int month = Integer.parseInt(token[0]);
        int day = Integer.parseInt(token[1]);
        int year = Integer.parseInt(token[2]);
        return new Date(year,month,day);
    }

    /**
     * This helper method checks if the given appDate is a valid Date
     * @param appDate Appointment Date
     * @return true if valid Date, false if invalid
     */
    private boolean checkappDate(Date appDate){
        if(appDate.isToday() || appDate.isBefore()){consele.appendText("Appointment Date: " + appDate.toString() + " today or date before today.\n");return false;}
        if(appDate.isWeekend()){consele.appendText("Appointment Date: " + appDate.toString() + " is Saturday or Sunday.\n"); return false;}
        if(!appDate.isWithinSixMonths()){consele.appendText("Appointment Date: " + appDate.toString() + " is not within six months.\n"); return false;}

        return true;
    }

    /**
     * This helper method checks if the Patient DOB is valid
     * @param dob Date of Birth of Patient
     * @return true if valid, false otherwise
     */
    private boolean checkDOB(Date dob){
        if(dob.isToday() || dob.isAfter()){consele.appendText("Patient Dob : " + dob.toString() + " is today or a date after today.\n"); return false;}
        return true;
    }

    /**
     * This helper method checks if an Appointment exists and returns the index of appointment in appointment list
     * @param patient Patient info
     * @param appDate Appointment Date
     * @param timeslot Appointment Time
     * @return index of Appointment if it exists, -1 if not
     */
    private boolean checkAppExist(Profile patient,Date appDate,Timeslot timeslot){
        if(appointmentList.isEmpty()) return false;
        for(int i = 0; i < appointmentList.size();i++){
            Appointment appointment = appointmentList.get(i);
            if(patient.equals(appointment.patient.profile) && appDate.equals(appointment.date) && timeslot.equals(appointment.timeslot))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This helper method checks if the Doctor is Available at the given time and Date
     * @param doc Doctor info
     * @param slot Time of Appointment
     * @param appDate Date of Appointment
     * @return true if Doctor is available, false otherwise
     */
    private boolean docAvial(Doctor doc,Timeslot slot,Date appDate){
        for(int i = 0; i < appointmentList.size();i++){
            Appointment appointment = appointmentList.get(i);
            if(appointment.getProvider().equals(doc) && appointment.gettimeslot().equals(slot) && appointment.getDate().equals(appDate)){
                consele.appendText(doc.toString() + " is not not available at slot " + slot.toString() + "\n");
                return false;
            }
        }

        return true;
    }

    /**
     * This helper method checks for the next available Technician to provide Imaging service
     * @param slot Time of appointment
     * @param room Room of Img appointment
     * @param appDate Date of appointment
     * @return next available Technician if free, null if no technician is free
     */
    public Technician checkImgAvail(Timeslot slot, Radiology room,Date appDate){
        RotationList current = rotation;
        do {
            if (checkTechnician(current.getTechnician(),slot,appDate)) {
                if(checkRoom(slot,room,appDate,current.getTechnician().getLocation())) {
                    rotation = current.getNext();
                    return current.getTechnician(); // Technician is available and room is free
                }
            }
            current = current.getNext();
        } while(current != rotation);
        consele.appendText("Cannot find an available technician at all locations for " + room.toString() + " at slot " + slot.toString() + "\n");
        return null;
    }

    /**
     * This helper method checks for room availability
     * @param slot Time of appointment
     * @param room Room of Img appointment
     * @param appDate Date of appointment
     * @param location location of the appointment
     * @return true if room available, false otherwise
     */
    public boolean checkRoom(Timeslot slot, Radiology room, Date appDate, Location location){
        for(int i = 0;i < appointmentList.size();i++){
            if(appointmentList.get(i) instanceof Imaging imgApp){
                if(imgApp.timeslot.equals(slot) && imgApp.getRoom().equals(room) && imgApp.getDate().equals(appDate) && ((Provider) imgApp.getProvider()).getLocation().equals(location)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This helper method checks if the Technician is available
     * @param technician Technician to check
     * @param slot Time of appointment
     * @param appDate Date of appointment
     * @return true if technician free, false otherwise
     */
    public boolean checkTechnician(Technician technician, Timeslot slot, Date appDate) {
        for (int i = 0; i < appointmentList.size(); i++) {
            Appointment appointment = appointmentList.get(i);
            // Check if the appointment is at the same time and the same technician
            if (appointment.getProvider().equals(technician) && appointment.gettimeslot().equals(slot) && appointment.getDate().equals(appDate)) {
                return false; // Technician is not available
            }
        }

        return true;
    }

    /**
     * This helper method loads the Provider
     */
    private void loadProviders(File file){
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] tokens = fileScanner.nextLine().split("  ");
                Profile profile = new Profile(tokens[1],tokens[2],giveDate(tokens[3]));
                switch (tokens[0]){
                    case "D" : {
                        providersList.add(new Doctor(Specialty.valueOf(tokens[5]), tokens[6], Location.valueOf(tokens[4]),profile));
                        doctorList.add(new Doctor(Specialty.valueOf(tokens[5]), tokens[6], Location.valueOf(tokens[4]),profile));
                        break;
                    }
                    case "T" : {
                        providersList.add(new Technician(Location.valueOf(tokens[4]),profile,Integer.parseInt(tokens[5])));
                        technicianList.add(new Technician(Location.valueOf(tokens[4]),profile,Integer.parseInt(tokens[5])));
                        break;
                    }
                }
            }
            fileScanner.close();
            Sort.provider(providersList);
            provider.setItems(FXCollections.observableArrayList(addtoProviderChoose()));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    /**
     * This helper method makes the Rotational List for the Technicians
     */
    private void makeRotationList(){
        for(int i = technicianList.size()-1; i >= 0;i--) {
            rotation.addTechnician(technicianList.get(i));
            consele.appendText(technicianList.get(i).toString() +"\n");
        }

    }

    /**
     * Helper method to get an array of providers to be put in the dropdown
     * @return Array of Provider
     */
    private Provider[] addtoProviderChoose() {
        Provider[] list = new Provider[doctorList.size()];
        for(int i = 0; i < doctorList.size();i++){
            list[i] = doctorList.get(i);
        }
        return list;
    }

    /**
     * This helper method prints the List of appointment given the keys to be orders in
     * @param sort a String of Keys to be sorted by
     */
    private void handlePrint(String sort){
        if(appointmentList.isEmpty()){ consele_P.appendText("Schedule calendar is empty.\n"); return;}
        if(sort.equals("PTD"))consele_P.appendText("\n** List of appointments, ordered by date/time/provider.\n");
        if(sort.equals("TDC"))consele_P.appendText("\n** List of appointments, ordered by county/date/time.\n");
        if(sort.equals("TDp"))consele_P.appendText("\n** List of appointments, ordered by Profile/date/time.\n");
        sortType(sort);
        for(int i = 0; i < appointmentList.size();i++){
            consele_P.appendText(appointmentList.get(i).toString() + "\n");
        }
        consele_P.appendText("** end of list **\n");
    }


    /**
     * This handler method prints the Imaging Appointments
     */
    private void handlePrintImaging(){
        if(appointmentList.isEmpty()){ consele_P.appendText("Schedule calendar is empty.\n"); return;}
        consele_P.appendText("\n** List of radiology appointments ordered by county/date/time.\n");
        sortType("TDC");
        for(int i = 0; i < appointmentList.size();i++){
            if(appointmentList.get(i) instanceof Imaging img) consele_P.appendText(img.toString() + "\n");
        }
        consele_P.appendText("** end of list **");
    }

    /**
     * This handler method prints the Office Appointments
     */
    private void handlePrintOffice(){
        if(appointmentList.isEmpty()){ consele_P.appendText("Schedule calendar is empty.\n"); return;}
        consele_P.appendText("\n** List of office appointments ordered by county/date/time.\n");
        sortType("TDC");
        for(int i = 0; i < appointmentList.size();i++){
            if(!(appointmentList.get(i) instanceof Imaging)) consele_P.appendText(appointmentList.get(i).toString() + "\n");
        }
        consele_P.appendText("** end of list **\n");
    }

    /**
     * This handler method prints the credit of each provider
     */
    private void handlePrintCredit(){
        if(appointmentList.isEmpty()){ consele_P.appendText("Schedule calendar is empty.\n"); return;}
        consele_P.appendText("\n** Credit amount ordered by provider. **\n");
        for(int i = 0;i < providersList.size();i++){
            int credit = 0;
            for(int j = 0; j < appointmentList.size();j++){
                if(appointmentList.get(j).getProvider().profile.equals(providersList.get(i).getProfile()))
                {
                    credit += providersList.get(i).rate();
                }
            }
            DecimalFormat df = new DecimalFormat("0.00");
            String c = df.format(credit);
            consele_P.appendText("(" + (i+1) + ") " + providersList.get(i).profile.toString() + " [credit amount: $" + c + "]\n");
        }
        consele_P.appendText("** end of list **\n");
    }

    /**
     * This handler method prints the billings of each patient
     */
    private void handlePrintBilling(){
        if(appointmentList.isEmpty()){ consele_P.appendText("Schedule calendar is empty.\n"); return;}
        consele_P.appendText("\n** Billing statement ordered by patient. **\n");
        List<Patient> patientList = new List<Patient>();
        for(int i = 0; i < appointmentList.size();i++){
            Appointment appointment = appointmentList.get(i);
            if(appointment.getPatient() instanceof Patient patient) {
                Visit visit = new Visit(appointment);
                if (patientList.contains(patient)) {
                    patientList.get(patientList.indexOf(patient)).addVisits(visit);
                } else {
                    patient = new Patient(patient.profile, visit);
                    patientList.add(patient);
                }
            }
        }
        clearAppList();
        Sort.patient(patientList);
        for(int i = 0; i < patientList.size();i++){
            DecimalFormat df = new DecimalFormat("0.00");
            String c = df.format(patientList.get(i).charge());
            consele_P.appendText("(" + (i+1) + ") " + patientList.get(i).toString() + " [due: $" + c + "]\n");
        }
        consele_P.appendText("** end of list **\n");
    }

    /**
     * Helper method to clear Appointment List
     */
    private void clearAppList(){
        int appSize = appointmentList.size();
        for(int i = appSize-1;i >= 0;i--){
            appointmentList.remove(appointmentList.get(i));

        }
    }

    /**
     * Helper method that sorts Appointment list given a String of Keys
     * @param order String of Keys/Sorting order
     */
    private void sortType(String order){
        for(int i = 0; i < order.length();i++){
            Sort.appointment(appointmentList,order.charAt(i));
        }
    }


    /**
     * This FXML method handles the functionality of canceling an appointment, when cancel button is clicked
     */
    @FXML
    public void onCancelClick() {
        String firstName = patientFirstName.getText();
        String lastName = patientLastName.getText();
        LocalDate dobValue = dob.getValue();
        LocalDate appDateValue = AppointmentDate.getValue();
        Timeslot timeSlot = slot.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || dobValue == null || appDateValue == null || timeSlot == null) {
            consele.appendText("Please fill in all fields before cancelling an appointment.\n");
            return;
        }

        Date newdob = new Date(dobValue.getYear(), dobValue.getMonthValue(), dobValue.getDayOfMonth());
        Date appDate = new Date(appDateValue.getYear(), appDateValue.getMonthValue(), appDateValue.getDayOfMonth());
        int index = getAppointmentIndex(new Profile(firstName,lastName,newdob),appDate,timeSlot);
        if(index == -1){consele.appendText("Appointment Doesn't exist\n");}
        else {
            appointmentList.remove(appointmentList.get(index));
            consele.appendText("Appointment canceled successfully.\n");
        }
    }


    /**
     * Helper method to get an index of Appointment in the current list
     * @param patient Profile of patient
     * @param appDate Date of appointment
     * @param timeslot TIme of appointment
     * @return index of the appointment in current list
     */
    private int getAppointmentIndex(Profile patient, Date appDate, Timeslot timeslot){
        if(appointmentList.isEmpty()) return -1;
        for(int i = 0; i < appointmentList.size();i++){
            Appointment appointment = appointmentList.get(i);
            if(patient.equals(appointment.patient.profile) && appDate.equals(appointment.date) && timeslot.equals(appointment.timeslot))
            {
                return i;
            }
        }
        return -1;
    }



    /**
     * This FXML method handles the functionality of rescheduling an appointment, when reschedule button is clicked
     */
    @FXML
    public void onRescheduleClick() {
        String firstName = rescheduleFirstName.getText();
        String lastName = rescheduleLastName.getText();
        LocalDate dobValue = rescheduleDob.getValue();
        LocalDate oldAppDateValue = oldAppointmentDate.getValue();
        Timeslot oldTimeSlot = oldSlot.getValue();
        Timeslot newTimeSlot = newSlot.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || dobValue == null || oldAppDateValue == null || oldTimeSlot == null || newTimeSlot == null) {
            consele_R.appendText("Please fill in all fields before rescheduling an appointment.\n");
            return;
        }

        Date dob = new Date(dobValue.getYear(), dobValue.getMonthValue(), dobValue.getDayOfMonth());
        Date oldAppDate = new Date(oldAppDateValue.getYear(), oldAppDateValue.getMonthValue(), oldAppDateValue.getDayOfMonth());
        Patient patient = new Patient(new Profile(firstName, lastName, dob));
        int index = getAppointmentIndex(patient.profile,oldAppDate,oldTimeSlot);

        if (index == -1) {
            consele_R.appendText("No existing appointment found to reschedule.\n");
        }
        else
        {
            if(getAppointmentIndex(patient.profile,oldAppDate,newTimeSlot) == -1) {
               appointmentList.get(index).setTimeslot(newTimeSlot);
               if(getAppointmentIndex(patient.profile,oldAppDate,newTimeSlot) != -1) consele_R.appendText("Rescheduled the appointment from " + oldTimeSlot.toString() + " to " + newTimeSlot.toString()+"\n");
            }
            else {
                consele_R.appendText("New timeslot is not available. Please choose a different one.\n");
            }
        }
    }
}
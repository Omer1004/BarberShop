package view;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View implements Initializable {
	private static final String LOGIN_PAGE = "/view/loginPage.fxml";
	private static final String VIEW_ADD_APPOINTMENT_CUSTOMER = "/view/ViewAddAppointmentCustomer.fxml";
	private static final String VIEW_ADMIN = "/view/ViewAdmin.fxml";
	private static final String VIEW_CUSTOMER = "/view/ViewCustomer.fxml";
	private static final String VIEW_DELETE_APPOINTMENT = "/view/ViewDeleteAppointment.fxml";
	private static final String VIEW_SHOWAPPOINTMENT = "/view/ViewShowAppointment.fxml";
	private static final String VIEW_SIGN_UP = "/view/ViewSignUp.fxml";
	private static final String VIEW_BLOCK_DAY = "/view/ViewBlockDayQ.fxml";
	private static LocalDate tmpDate;
	
	@FXML
	ComboBox<String> CBChooseHourAdmin,CBHourAA;
	
	@FXML
	ComboBox<String> CBHaircut;
	
	@FXML
	private DatePicker DPdateAdmin,DPDateAA;
	
	@FXML
	private Text TPriceOfHaircut;
	
	@FXML
	private AnchorPane APLoginManu;
	@FXML
	private Label LAppointmentSA,LTimeBD,LshowMeeting;
	@FXML
	private Text TCostumerAppointment ; 
	
	@FXML
	TextArea TAAdminAppointment = new TextArea();
	
	@FXML
	Label LAAppointmentSA ;
	
	@FXML
	private Button bSignUpLoginPage,bLogin,bResetLogin,//LOGIN PAGE
					bLogOutCostumer,bShowAppointment,bCancelAppointment,bAddAppointment,//COSTUMER PAGE
					bSignUp,bResetSignUp,bCancelSignUp,//SIGN UP PAGE
					bLogOutAdmin, bBlockDay, bDeleteAppointmentAdmin,BshowMeeting,bUnblockDay,//ADMIN PAGE
					BDeleteAllMeetingsBD,BReturnToAdmin,//BLOCK DAY Q ADMIN
					bDeleteAppointment_CDA , bCancel_CDA,bDAshowAppointment,//DELETE APPOINTMENT COSTUMER
					bReturn_SA, //SHOW APPOINTMENT COSTUMER
					bCancelAA,bAddAppointmentAA//ADD APPOINTMENT
					;
	@FXML
	private TextField TFUsername,TFPassword,TFNameSignUp,TFPasswordSignUp,TFUsernameSignUp;	

	
	///////////////////////////////////////////////////////////////////////////////////////////////
	//General Shit
	
	private static Controller controller;
	
	public View() {
		
	//	CBHaircut.getItems().addAll(Controller.getAllHaircuts());
		CBHaircut = new ComboBox<String>();
		CBHourAA = new ComboBox<String>();
		CBChooseHourAdmin = new ComboBox<String>();
		
	
		
		
	}
	
	public void setController(Controller c) {
		controller = c;
	}
	
	public Controller getController() {
		return controller;
	}
	
	private void moveToScreen(Button bScreenToClose, String pathToNewScreen) {
		bScreenToClose.getScene().getWindow().hide();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(pathToNewScreen));
			Stage signUpStage = new Stage();
			Scene scene = new Scene(root);
			signUpStage.setScene(scene);
			signUpStage.show();
			
		} catch (Exception e) {
			System.out.println("Problem loading Page\n"+e.getMessage());
		}
	}
	
	
	public void showmsg(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//LOGIN Page
	public void loginClicked() throws ClassNotFoundException, SQLException {//MOVE TO DIFRERENT PAGE
		if(TFUsername.getText().isBlank()||TFPassword.getText().isBlank())
			showmsg("Please fill all fields");
		else {
			int res = controller.loginToSystem(TFUsername.getText(),TFPassword.getText());
			if(res==0)
			{
				return;
			}
			else if(res==1)
			{
				showmsg("succesful login");
				moveToScreen(bLogin,VIEW_ADMIN);
			}
			else if(res==2){
				showmsg("succesful login");
				moveToScreen(bLogin,VIEW_CUSTOMER);
			}
			else {	
				showmsg("Invalid username or password");
			}
		
		}
		
	}

	public void clearUsernameAndPasswordLogin() {
		TFUsername.clear();
		TFPassword.clear();
	}

	public void bSignUpLoginPageClicked() {
		moveToScreen(bSignUpLoginPage,VIEW_SIGN_UP);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SIGN UP PAGE
	
	
	public void signUpClicked() throws ClassNotFoundException, SQLException {
		if(TFUsernameSignUp.getText().isBlank()||TFPasswordSignUp.getText().isBlank()||TFNameSignUp.getText().isBlank())
			showmsg("Please fill all fields");
		else {
			boolean res = controller.signUpUser(TFNameSignUp.getText(),TFPasswordSignUp.getText(),TFUsernameSignUp.getText());
			if(res) {
				showmsg("Sign up succesful");
				moveToScreen(bSignUp, LOGIN_PAGE);
			}
			else {
				showmsg("Sign up unsuccesful");
			}		
		}
	}
	
	
	
	public void returnToLoginPage() {
		moveToScreen(bCancelSignUp, LOGIN_PAGE); 
	}
	 
	
	public void clearUsernameAndPasswordSignUp() {
		TFNameSignUp.clear();
		TFPasswordSignUp.clear();
		TFUsernameSignUp.clear();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Costumer Page
		
	public void bLogOutCostumerClicked() {
		controller.userSignOut();
		moveToScreen(bLogOutCostumer, LOGIN_PAGE); 
		
	}
	
	public void bShowAllAppointmentClicked() {
		//TO GO TO SHOW APPOINTMENT
		
		moveToScreen(bLogOutCostumer, VIEW_SHOWAPPOINTMENT); 
		
	}
	
	public void bCancelAppointmentClicked() {
		//TO GO TO DELETE APPOINTMENT
		
		moveToScreen(bCancelAppointment, VIEW_DELETE_APPOINTMENT); 
	}
	
	
	
	public void bAddAppointmentClicked() {
		//TO GO TO ADD APPOINTMENT
		moveToScreen(bAddAppointment, VIEW_ADD_APPOINTMENT_CUSTOMER); 
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ADD APPOINTMENT 
	
	
	public void bAddAppointmentAAClicked(){
		if(DPDateAA.getValue()==null||CBHourAA.getValue()==null||CBHaircut.getValue()==null) {
			showmsg("Fill all fields");
			return;
		}
		else {
			if(!controller.setAppointmentForLoggedInUser(DPDateAA.getValue(),CBHourAA.getValue(),
					CBHaircut.getValue()))
				showmsg("somthing went wrong... appointment was not added");
			else {
				showmsg("appointment was added");
				CBHourAA.getItems().clear();
				CBHaircut.getSelectionModel().select(null);
				moveToScreen(bAddAppointmentAA, VIEW_CUSTOMER);
			}
		}
		
	}
	
	public void bCancelAAClicked() {
		DPDateAA.setValue(null);
		CBHourAA.getItems().clear();
		moveToScreen(bCancelAA, VIEW_CUSTOMER);
	}
	
	public void DPDateAAClicked(){
		CBHourAA.getItems().clear();
		if(controller.getAvilableAppointmentsHours(DPDateAA.getValue())!=null)
			CBHourAA.getItems().addAll(controller.getAvilableAppointmentsHours(DPDateAA.getValue()));
	}
	
	public void CBHaircutClicked() {
		TPriceOfHaircut.setText("Price : "+ controller.getPriceOfHaircut(CBHaircut.getValue()));
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Delete appointment costumer
	
	public void bDeleteAppointment_CDA_Clicked() {
		if(controller.deleteAppointmentForLoggedInUser()) {
			showmsg("delete successful");
		}
		else 
			showmsg("delete appointment didnt succeed");
	}

	
	public void bCancel_CDA_Clicked() {
		TCostumerAppointment.setText(" ");
		moveToScreen(bCancel_CDA, VIEW_CUSTOMER);
	}
	
	public void bDAshowAppointmentClicked() {
		TCostumerAppointment.setText(controller.getLoggedInCostumerAppointmentString());
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Show appointment
	public void bSAShowAppointmentClicked() {
		LAAppointmentSA.setText(controller.getLoggedInCostumerAppointmentString()+" ");
	}
	
	public void bReturn_SA_Clicked() {
		LAAppointmentSA.setText(" ");
		moveToScreen(bReturn_SA, VIEW_CUSTOMER);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ADMIN VIEW
	
	
	public void DPdateAdminClicked() {
		if(DPdateAdmin.getValue()!=null) {
			if(controller.getTimesOfAppointments(DPdateAdmin.getValue())!=null) {
				CBChooseHourAdmin.getItems().clear();
				CBChooseHourAdmin.getItems().addAll(controller.getTimesOfAppointments(DPdateAdmin.getValue()));			
			}			
		}
		
	}
	
	public void bUnblockDayClicked() {
		if(DPdateAdmin.getValue()==null) {
			showmsg("Date is null");
		}
		else {
			controller.unblockDay(DPdateAdmin.getValue());
		}
	}
	
	public void CBChooseHourAdminClicked() {
		if(CBChooseHourAdmin.getValue()!=null)
			LshowMeeting.setText(controller.getAppointmentFromTime(DPdateAdmin.getValue(),CBChooseHourAdmin.getValue()));
	}
	
	
	public void bLogOutAdminClicked() {
		controller.userSignOut();
		CBChooseHourAdmin.getItems().clear();
		TAAdminAppointment.setText("");
		moveToScreen(bLogOutAdmin, LOGIN_PAGE); 
	}
	
	public void bBlockDayClicked() {
		if(DPdateAdmin.getValue()==null) {
			showmsg("no date selected");
			return;
		}
		if(DPdateAdmin.getValue().isBefore(LocalDate.now())) {
			showmsg("Date invalid");
			return;
		}
		moveToScreen(bBlockDay, VIEW_BLOCK_DAY);
		tmpDate = DPdateAdmin.getValue();
		
	}
	

	
	public void bDeleteAppointmentAdminClicked() {//TO FINISH
		if(DPdateAdmin.getValue()==null||CBChooseHourAdmin.getValue()==null){
			showmsg("No appointment was selected");
			return;
		}
		//TO DO OBSERVER NOTIFY USER WHEN LOGIN
		controller.deleteAppointmentInTime(DPdateAdmin.getValue(),CBChooseHourAdmin.getValue());
		CBChooseHourAdmin.getItems().clear();
		DPdateAdmin.setValue(null);
		LshowMeeting.setText("Appointment was deleted");

	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	//BLOCK DAY
	public void BReturnToAdminClicked() {
		moveToScreen(BReturnToAdmin,VIEW_ADMIN);
	}
	
	public void BDeleteAllMeetingsBDClicked() {
		if(!controller.deleteAllAppointmentsInDate(tmpDate)) {
			showmsg("Something went wrong appointments were not deleted");
		}
		else {
			showmsg("all appointments in the date "+tmpDate.toString()+" were deleted");
		}
		moveToScreen(BDeleteAllMeetingsBD,VIEW_ADMIN);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		CBChooseHourAdmin.getItems().removeAll(CBChooseHourAdmin.getItems());
		CBHaircut.getItems().removeAll(CBHaircut.getItems());
		CBHaircut.getItems().addAll(Controller.getAllHaircuts());
		CBHaircut.getSelectionModel().select(null);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
}

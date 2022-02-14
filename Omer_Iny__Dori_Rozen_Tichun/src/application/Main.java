package application;
	
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modelPackage.Model;
import view.View;
import javafx.scene.Parent;
import javafx.scene.Scene;

////                     view  <->  controller  <->  model
////             JDBController    admin controller    costumer controler

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			View view = new View();
			Model model = Controller.bootUp();
			Controller controller = new Controller(view, model);
			view.setController(controller);
			model.setController(controller);
			Parent root = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

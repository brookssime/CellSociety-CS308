import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


	@Override
	public void start(Stage s) throws Exception {	

		
		CellSocietyView display = new CellSocietyView(300, 300, "square");
		s.setScene(display.getScene());
		s.show();
	}

	public static void main(String[] args) {
		
		launch(args);
	}

}

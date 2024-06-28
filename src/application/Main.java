package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			String fxml = "vista/Baseplate.fxml";
			// Cargar la ventana
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
			// Cargar la Scene
			Scene scene = new Scene(root);
			// Asignar propiedades al Stage
			
			primaryStage.setTitle("Game of Cards");
			primaryStage.getIcons().add(new Image(getClass().getResource("../vista/icono.png").toExternalForm()));
			primaryStage.setResizable(false);
			// Asignar la scene y mostrar
			primaryStage.setScene(scene);
			primaryStage.show();
			scene.getStylesheets().add(getClass().getResource("../vista/application.css").toExternalForm());

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {// javafx + fxml 获取ui
        //加载fxml文件
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/des.fxml"));
        FXMLLoader violentLoader = new FXMLLoader(getClass().getResource("/violent.fxml"));

        //加载父结点
        Parent root = mainLoader.load();
        Parent parent = violentLoader.load();

        //获取Controller
        desController controller = mainLoader.getController();
        violentController violentController = violentLoader.getController();

        //显示Stage设置
        primaryStage.setTitle("S-DES");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Stage vioStage = new Stage();
        vioStage.setTitle("Violent Decode");
        vioStage.setScene(new Scene(parent));
        vioStage.show();

    }
}

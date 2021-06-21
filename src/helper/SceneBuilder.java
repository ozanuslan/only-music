package helper;

import controller.ItemPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Item;

import java.io.IOException;

/**
 * Helper class for opening and closing pages throughout the project
 */
public class SceneBuilder {

    private static SceneBuilder sb;

    public static SceneBuilder getSceneBuilder(){
        return sb == null ? sb = new SceneBuilder() : sb;
    }
    public void createScene(String scenePath)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/"+scenePath+".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Only-Music");
        stage.show();
    }
    public void closeScene(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

}

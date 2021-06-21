package helper;

import controller.BlockController;
import controller.DynamicGridController;
import controller.MainPageController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Item;
import model.Order;

import java.io.IOException;
import java.util.ArrayList;

public class GUIHelper {
    /**
     * singleton variable
     */
    private static GUIHelper guiHelper;

    public static GUIHelper getGuiHelper(){
        return guiHelper == null ? guiHelper = new GUIHelper() : guiHelper;
    }

    /**
     * sets the given list into the given grid pane and show it according to block name fxml.
     * sets the insets of grid pane.
     * @param itemList
     * @param gridPane
     * @param dynamicGridController
     * @param blockName
     * @param topBottom
     * @param rightLeft
     * @param <T>
     */
    public <T> void showDynamicGrid(ArrayList<T> itemList, GridPane gridPane, DynamicGridController dynamicGridController, String blockName,int topBottom, int rightLeft){
        int column = 0;
        int row = 1;
        boolean isMainPageController = false;
        try{
            dynamicGridController = (MainPageController)dynamicGridController;
            isMainPageController = true;
        }catch (Exception e){
            isMainPageController = false;
        }
        try {
            int size = itemList.size();
            for(int i = 0;i<size;i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/"+blockName+".fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                BlockController blockController = fxmlLoader.getController();
                blockController.setData(itemList.get(i));
                blockController.setController(dynamicGridController);

                gridPane.add(anchorPane, column, row);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);
                if(isMainPageController){
                    column++;
                    if(column == 4){
                        column = 0;
                        row++;
                    }
                }else{
                    row++;
                }
                GridPane.setMargin(anchorPane, new Insets(topBottom,rightLeft,topBottom,rightLeft));
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * shows only one block in grid pane
     * @param item
     * @param blockName
     * @param dynamicGridController
     * @param gridPane
     * @throws IOException
     */
    public void showBlock(Item item,String blockName,DynamicGridController dynamicGridController, GridPane gridPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/"+blockName+".fxml"));

        AnchorPane anchorPane = fxmlLoader.load();

        BlockController blockController = fxmlLoader.getController();
        blockController.setData(item);
        blockController.setController(dynamicGridController);
        gridPane.add(anchorPane, 0, 0);
        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxWidth(Region.USE_PREF_SIZE);

        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxHeight(Region.USE_PREF_SIZE);
    }
    /**
     * sets the given order list into two grid pane (pending, completed).
     * sets the insets of the grid pane.
     * @param list
     * @param gridPane
     * @param gridPane2
     * @param dynamicGridController
     * @param blockName
     * @param topBottom
     * @param rightLeft
     */
    public void showDoubleDynamicGrid(ArrayList<Order> list, GridPane gridPane, GridPane gridPane2, DynamicGridController dynamicGridController, String blockName, int topBottom, int rightLeft){
        int rowPending = 0;
        int rowCompleted = 0;
        try {
            int size = list.size();
            for(int i = 0;i<size;i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/"+blockName+".fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                BlockController blockController = fxmlLoader.getController();
                blockController.setData(list.get(i));
                blockController.setController(dynamicGridController);

                if(list.get(i).getStatus() == 0){
                    gridPane.add(anchorPane, 0, rowPending++);
                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_PREF_SIZE);
                }
                else{
                    gridPane2.add(anchorPane, 0, rowCompleted++);
                    gridPane2.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane2.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane2.setMaxWidth(Region.USE_PREF_SIZE);

                    gridPane2.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane2.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane2.setMaxHeight(Region.USE_PREF_SIZE);
                }

                GridPane.setMargin(anchorPane, new Insets(topBottom,rightLeft,topBottom,rightLeft));
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

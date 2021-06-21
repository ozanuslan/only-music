package controller;

public interface BlockController {

    /**
     * sets the Dynamic grid controller class into blockController.
     * @param dynamicGridController
     */
    public void setController(DynamicGridController dynamicGridController);

    /**
     * sets the generic data into the blockController (item, order, cartItem)
     * @param data
     * @param <T>
     */
    public <T> void setData(T data);

}

package cs3318.raytracing;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.logging.Logger;


public class Controller {
    public ImageView renderedImage;
    private Stage stage;
    private Driver sceneToRender;
    private Optional<Boolean> finished = Optional.empty();

    Logger logger = Logger.getLogger(Controller.class.getName());

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void run() {
        long time = System.currentTimeMillis();
        try {
            for (int j = 0; j < sceneToRender.height; j += 1) {
                for (int i = 0; i < sceneToRender.width; i += 1) {
                    sceneToRender.renderPixel(i, j);
                }
            }
            renderedImage.setImage(sceneToRender.getRenderedImage());
            finished = Optional.of(true);
            logger.info("Image has been rendered within " + (System.currentTimeMillis() - time) + " ms");
        } catch (Exception e) {
            logger.severe("Error occured during rendering: " + e.getMessage());
        }
    }
 
    public void startRayTrace(ActionEvent actionEvent, int width, int height) {
        sceneToRender = new RayTraceAPI(width, height);
        this.run();
    }
}

package it.polimi.ingsw.view.gui;

import javafx.animation.ScaleTransition;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to handle the enlargement of some {@link ImageView} in the GUI.
 *
 * To set the animation it is just necessary to pass the image to the <i>addToAnimation</i> method.
 */
public class EnlargementHandler {
    /**
     * This is the default duration of the animation in milliseconds.
     */
    private static final int DEFAULT_DURATION = 30;
    /**
     * This is the default zoom factor applied to the images.
     */
    private static final double DEFAULT_ZOOM = 0.25;
    /**
     * This map is used to link every images to its handler.
     * This is implemented to allow this class extension for an eventual permanent stop of the animation.
     */
    private static Map<ImageView, SingleEnlargement> transitions = new HashMap<>();

    private EnlargementHandler(){

    }

    /**
     * This is the only exposed method and it is used to set the enlargement <i>routine</i> to an image.
     * @param image the image to animate
     */
    public static synchronized void addToAnimation(ImageView image){
        transitions.put(image, new SingleEnlargement(image, DEFAULT_DURATION, DEFAULT_ZOOM));

        image.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> beginEnlargement(image));
        image.addEventHandler(MouseEvent.MOUSE_EXITED, e -> reverseEnlargement(image));
    }

    /**
     * This method is used to start the enlargement animation.
     * @param image the subject of the event
     */
    private static synchronized void beginEnlargement(ImageView image){
        if(transitions.containsKey(image))
            transitions.get(image).start();
    }

    /**
     * This method is used to reverse the enlargement animation.
     * @param image the subject of the event
     */
    private static synchronized void reverseEnlargement(ImageView image){
        if(transitions.containsKey(image))
            transitions.get(image).reverse();
    }

    /**
     * This object is used to handle one single animation for a specific {@link ImageView}.
     */
    private static class SingleEnlargement{
        /**
         * The target of the animation
         */
        private ImageView image;
        /**
         * The object that performs the animation
         */
        private ScaleTransition transition;
        /**
         * This is the original size of the animation
         */
        private Size originalSize;
        /**
         * This size has to be reached at the end of the animation
         */
        private Size targetSize;

        SingleEnlargement(ImageView image, int duration, double zoom){
            this.image = image;
            this.transition = new ScaleTransition(Duration.millis(duration), image);

            Bounds bounds = image.getBoundsInParent();

            this.originalSize = new Size(bounds.getWidth(), bounds.getHeight());
            this.targetSize = new Size( (1+zoom)*this.originalSize.width,
                                        (1+zoom)*this.originalSize.height);
        }

        /**
         * This method is used to eventually stops the actual animation and starts to reach
         * the target size from the actual one. Multiple invocations don't cause problems.
         */
        void start() {
            this.transition.stop();
            Bounds bounds = image.getBoundsInParent();
            this.transition.setByX(this.targetSize.width/bounds.getWidth() - 1);
            this.transition.setByY(this.targetSize.height/bounds.getHeight() - 1);
            this.transition.play();
        }

        /**
         * This method is used to eventually stops the actual animation and starts to reach
         * the original size from the actual one. Multiple invocations don't cause problems.
         */
        void reverse(){
            this.transition.stop();
            Bounds bounds = image.getBoundsInParent();
            this.transition.setByX(-(bounds.getWidth()/this.originalSize.width - 1));
            this.transition.setByY(-(bounds.getHeight()/this.originalSize.height - 1));
            this.transition.play();
        }

        /**
         * This class represents a generic couple: width and height.
         */
        class Size{
            double width;
            double height;

            Size(double width, double height){
                this.width = width;
                this.height = height;
            }
        }
    }
}

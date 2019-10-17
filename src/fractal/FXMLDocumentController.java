/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import javafx.scene.control.Slider;

/**
 *
 * @author maksim
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Pane pane;
    private ArrayList<Circle> points;
    private Circle startPoint;
    private Circle lastPoint;
    private Timeline timer;
    private boolean isRunning = false;
    @FXML
    private Slider slider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        points = new ArrayList<>();

        timer = new Timeline(new KeyFrame(Duration.ZERO, e -> process()), new KeyFrame(Duration.millis(1), e ->{}));
        timer.setAutoReverse(true);
        timer.setCycleCount(Timeline.INDEFINITE);
        
        slider.valueProperty().addListener(e ->{
            pane.getChildren().clear();
            lastPoint = startPoint;
        });
        pane.setOnMouseClicked(e -> {
            if (!isRunning) {
                if (e.getButton() == MouseButton.PRIMARY) {
                    Circle c = new Circle(e.getX(), e.getY(), 10, Color.RED);
                    pane.getChildren().add(c);
                    points.add(c);
                }
                if (e.getButton() == MouseButton.SECONDARY) {
                    startPoint = new Circle(e.getX(), e.getY(), 10, Color.GREEN);
                    pane.getChildren().add(startPoint);
                }
            }

        });
        // TODO
    }

    @FXML
    private void bStart(ActionEvent event) {
        if (isRunning) {
            timer.stop();
            pane.getChildren().clear();
            points.clear();
            isRunning = false;
        } else {
            isRunning = true;
            timer.play();
        }
    }

    private void process() {
        if (startPoint != null) {
            int numberOfPoint = (int) ((Math.random() * points.size()));
            int count = points.size();
            System.out.println(numberOfPoint);
            if (lastPoint == null) {
                lastPoint = startPoint;
            }
            double g = slider.getValue();
            //g = 1;
            System.out.println(g);
            double newX=0;
            double newY=0;
            double toX = points.get(numberOfPoint).getCenterX();
            double toY = points.get(numberOfPoint).getCenterY();
            double fromX = lastPoint.getCenterX();
            double fromY = lastPoint.getCenterY();
            if (fromX >= toX){
                newX = fromX + (toX-fromX)/g;
            }
            else {
                newX = fromX + (toX-fromX)/g;
            }
            if (fromY >= toY){
                newY = fromY + (toY-fromY)/g;
            }
            else {
                newY = fromY + (toY-fromY)/g;
            }
            lastPoint = new Circle(newX, newY, 1, Color.YELLOW);
            pane.getChildren().add(lastPoint);
        }
    }

}

package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class MainPageController {

    private ArrayList<Circle> _previousTail = new ArrayList<Circle>();

    @FXML
    private Button _button;

    @FXML
    private AnchorPane _pane;

    private Board _board;

    @FXML
    public void initialize() {
        _board = new Board(this);

        _button.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.UP) {
                    _board.setDirection(Direction.UP);
                }
                else if (event.getCode() == KeyCode.DOWN) {
                    _board.setDirection(Direction.DOWN);
                }
                else if(event.getCode() == KeyCode.RIGHT) {
                    _board.setDirection(Direction.RIGHT);
                }
                else if (event.getCode() == KeyCode.LEFT) {
                    _board.setDirection(Direction.LEFT);
                }
            }
        });



    }

    @FXML
    public void handleKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.UP) {
            _board.setDirection(Direction.UP);
            System.out.println("UP");
        }
        else if (event.getCode() == KeyCode.DOWN) {
            _board.setDirection(Direction.DOWN);
        }
        else if(event.getCode() == KeyCode.RIGHT) {
           _board.setDirection(Direction.RIGHT);
        }
        else if (event.getCode() == KeyCode.LEFT) {
            _board.setDirection(Direction.LEFT);
        }
        event.consume();
    }

    @FXML
    public void paintHead(int x, int y) {
        Circle circle = new Circle(x, y,3);
        circle.setFill(Color.PINK);
        _pane.getChildren().add(circle);
        _previousTail.add(circle);
    }

    @FXML
    public void paintBody(int x, int y) {
        Circle circle = new Circle(x, y,3);
        circle.setFill(Color.GREEN);
        _pane.getChildren().add(circle);
        _previousTail.add(circle);
    }

    @FXML
    public void paintTail(int x, int y) {
        Circle circle = new Circle(x, y,3);
        circle.setFill(Color.PURPLE);
        _pane.getChildren().add(circle);
        _previousTail.add(circle);
    }

    @FXML
    public void paintFruit(int x, int y) {
        Circle circle = new Circle(x, y,3);
        circle.setFill(Color.RED);
        _pane.getChildren().add(circle);
    }

    @FXML
    public void clear() {
        for(Circle c: _previousTail){
            _pane.getChildren().remove(c);
        }
    }

}

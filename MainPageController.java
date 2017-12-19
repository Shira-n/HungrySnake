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
    private ArrayList<Circle> _fruits = new ArrayList<Circle>();

    @FXML
    private Button _button;

    @FXML
    private AnchorPane _pane;

    private Board _board;

    private Direction _currentDirection;

    @FXML
    public void initialize() {
        _board = new Board(this);

        _button.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.UP) {
                    _currentDirection = Direction.UP;
                }
                else if (event.getCode() == KeyCode.DOWN) {
                    _currentDirection = Direction.DOWN;
                }
                else if(event.getCode() == KeyCode.RIGHT) {
                    _currentDirection = Direction.RIGHT;
                }
                else if (event.getCode() == KeyCode.LEFT) {
                    _currentDirection = Direction.LEFT;
                }
            }
        });



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
    public void paintFruit(ArrayList<int[]> fruits) {
        for (Circle c: _fruits) {
            _pane.getChildren().remove(c);
        }
        _fruits = new ArrayList<Circle>();
        for(int i = 0; i < fruits.size(); i++) {
            Circle circle = new Circle(fruits.get(i)[0],fruits.get(i)[1],3);
            circle.setFill(Color.RED);
            _pane.getChildren().add(circle);
            _fruits.add(circle);
        }
    }

    @FXML
    public void clear() {
        for(Circle c: _previousTail){
            _pane.getChildren().remove(c);
        }
    }

    public Direction getDirection() {
        return _currentDirection;
    }

}

package sample;


import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class Board {

    private final int BOARD_WIDTH = 500;
    private final int BOARD_HEIGHT = 500;
    private final int START_LENGTH = 3;
    private final int[] DEFAULT_START_POINT = {200,200}; //default snake facing left;
    private final Direction DEFAULT_START_DIRECTION = Direction.LEFT;
    private final int DEFAULT_FRUIT_NUM = 5;
    private final int START_SPEED = 500;


    private MainPageController _controller;

    private int _snakeLength;
    private int _fruitNum;

    private int[] x_snake = new int[BOARD_HEIGHT * BOARD_WIDTH];
    private int[] y_snake = new int[BOARD_HEIGHT * BOARD_WIDTH];

    private int[] x_fruit = new int[DEFAULT_FRUIT_NUM];
    private int[] y_fruit = new int[DEFAULT_FRUIT_NUM];

    private Timer _timer;
    private int _speed;  //snake movement in millisecond;

    private Direction _direction;

    public Board(MainPageController controller) {
        _controller = controller;
        _snakeLength = START_LENGTH;
        _fruitNum = 0;
        _speed = START_SPEED;
        _direction = DEFAULT_START_DIRECTION;

        initialiseSnake();
        initialiseFruit();

        setUpTimer();
    }


    private void initialiseSnake() {
        for(int i = _snakeLength; i>0; i--) {
            System.out.println(""+_direction.getX());
            x_snake[i-1] = DEFAULT_START_POINT[0]-_direction.getX()*i;
            y_snake[i-1] = DEFAULT_START_POINT[1] -_direction.getY()*i;
        }

        paintSnake();
        System.out.println("initialise snake");
    }

    private void initialiseFruit() {
        while (_fruitNum < DEFAULT_FRUIT_NUM){
            plantFruit();
            _fruitNum++;
        }
    }

    private void paintSnake() {
        for (int i = 0; i < _snakeLength; i++) {
            if(i == 0) {
                _controller.paintHead(x_snake[0],y_snake[0]);
                System.out.println("Head "+ x_snake[0]+ y_snake[0]);
            }
            else if (i == _snakeLength -1) {
                _controller.paintTail(x_snake[i],y_snake[i]);
                System.out.println("Tail "+ x_snake[i]+y_snake[i]);
            }
            else {
                _controller.paintBody(x_snake[i],y_snake[i]);
                System.out.println("Body "+ x_snake[i]+y_snake[i]);
            }
        }
    }

    private void plantFruit() {
        int x_pos = (int)(Math.random() * BOARD_WIDTH);
        int y_pos = (int)(Math.random() * BOARD_HEIGHT);

        x_fruit[_fruitNum] = x_pos;
        y_fruit[_fruitNum] = y_pos;

        _controller.paintFruit(x_pos, y_pos);
        System.out.println("plant fruit");
    }

    private void moveSnake() {


        for(int i = _snakeLength; i > 0; i--) {
            x_snake[i] = x_snake[i-1];
            y_snake[i] = y_snake[i-1];
        }
        x_snake[0] = x_snake[0]+_direction.getX();
        y_snake[0] = y_snake[0]+_direction.getY();

        _controller.clear();
        paintSnake();
    }

    private void setUpTimer() {
        _timer = new Timer();

        TimerTask updateSnake = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    moveSnake();
                    System.out.println("timer");
                });
            }
        };

        TimerTask increaseSpeed = new TimerTask() {
            public void run() {
                _speed = _speed - 500;
            }
        };

        _timer.scheduleAtFixedRate(updateSnake, 0,_speed);
        _timer.scheduleAtFixedRate(increaseSpeed,0,7001);
    }
    public void setDirection(Direction direction) {
        _direction = direction;
    }
}

enum Direction {
    LEFT(-10,0),RIGHT(10,0),UP(0,-10),DOWN(0,10);

    private int _x;
    private int _y;

    Direction(int x, int y) {
       _x = x;
       _y = y;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }
}

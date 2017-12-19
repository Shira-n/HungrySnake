package sample;


import javafx.application.Platform;

import java.util.ArrayList;
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

    private ArrayList<int[]> _fruits= new ArrayList<>();
    private int[] x_fruit = new int[DEFAULT_FRUIT_NUM];
    private int[] y_fruit = new int[DEFAULT_FRUIT_NUM];

    private Timer _timer;
    private int _speed;  //snake movement in millisecond;

    private int _score;

    private boolean _gameOver = false;

    private Direction _direction;

    public Board(MainPageController controller) {
        _controller = controller;
        _snakeLength = START_LENGTH;
        _fruitNum = 0;
        _speed = START_SPEED;
        _direction = DEFAULT_START_DIRECTION;

        initialiseSnake();
        plantFruit();

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

    private void paintSnake() {
        for (int i = 0; i < _snakeLength; i++) {
            if(i == 0) {
                _controller.paintHead(x_snake[0],y_snake[0]);
                //System.out.println("Head "+ x_snake[0]+ y_snake[0]);
            }
            else if (i == _snakeLength -1) {
                _controller.paintTail(x_snake[i],y_snake[i]);
                //System.out.println("Tail "+ x_snake[i]+y_snake[i]);
            }
            else {
                _controller.paintBody(x_snake[i],y_snake[i]);
                //System.out.println("Body "+ x_snake[i]+y_snake[i]);
            }
        }
    }

    private void plantFruit() {
        while (_fruits.size() < DEFAULT_FRUIT_NUM){
            int[] pos = {(int)(Math.round((Math.random() * BOARD_WIDTH)/10.0)*10.0),(int)(Math.round((Math.random() * BOARD_HEIGHT)/10.0)*10.0)};
            _fruits.add(pos);
            //x_fruit[_fruitNum] = x_pos;
            //y_fruit[_fruitNum] = y_pos;
            //_fruitNum++;
        }
        _controller.paintFruit(_fruits);
    }

    private void moveSnake() {
        setDirection();

        for(int i = _snakeLength; i > 0; i--) {
            x_snake[i] = x_snake[i-1];
            y_snake[i] = y_snake[i-1];
        }
        x_snake[0] = x_snake[0]+_direction.getX();
        y_snake[0] = y_snake[0]+_direction.getY();

        checkHitWall();
        _controller.clear();
        paintSnake();
        checkFruit();
        checkGameOver();
    }

    private void checkHitWall() {
        for (int i = 0; i< _snakeLength; i++) {
            if (x_snake[i]>BOARD_WIDTH) {
                x_snake[i]=0;
            }
            else if(x_snake[i]<0) {
                x_snake[i]=BOARD_WIDTH;
            }
            else if(y_snake[i]>BOARD_HEIGHT) {
                y_snake[i]=0;
            }
            else if(y_snake[i]<0){
                y_snake[i]=BOARD_HEIGHT;
            }
        }
    }

    private void setUpTimer() {
        _timer = new Timer();

        TimerTask updateSnake = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    if(!_gameOver){
                        moveSnake();
                        //System.out.println("timer " + _speed);
                        setUpTimer();
                    }
                });
            }
        };

        _timer.schedule(updateSnake, _speed);
    }

    private void checkFruit() {
        for (int i = 0; i < _fruits.size(); i++) {
            if (_fruits.get(i)[0] == x_snake[0] && _fruits.get(i)[1] == y_snake[0]) {
                _fruits.remove(i);
                plantFruit();
                _score ++;
                if (_speed > 80) {
                    _speed = _speed -50;
                }
                increaseSnakeLength();
            }
        }
    }

    private void increaseSnakeLength() {
        x_snake[_snakeLength] = x_snake[_snakeLength-1]+_direction.getX();
        y_snake[_snakeLength] = y_snake[_snakeLength-1]+_direction.getY();
        _snakeLength++;
    }
    private void checkGameOver() {
        for(int i =1; i<_snakeLength;i++){
            if (x_snake[i]==x_snake[0] && y_snake[i]==y_snake[0]) {
                _timer.cancel();
                _gameOver = true;
                System.out.println("Game Over");
            }
        }


    }

    public void setDirection() {
        Direction direction = _controller.getDirection();
        if ((direction == Direction.LEFT && _direction != Direction.RIGHT)
            ||(direction == Direction.RIGHT && _direction != Direction.LEFT)
                ||(direction == Direction.UP && _direction != Direction.DOWN)
                ||(direction == Direction.DOWN && _direction != Direction.UP)){
            _direction = direction;
        }

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

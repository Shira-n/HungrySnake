package sample;

public class Board {

    private final int BOARD_WIDTH = 50;
    private final int BOARD_HEIGHT = 50;
    private final int START_LENGTH = 3;
    private final int[] DEFAULT_START_POINT = {20,20}; //default snake facing left;
    private final int DEFAULT_FRUIT_NUM = 5;


    private MainPageController _controller;

    private int _snakeLength;
    private int _fruitNum;

    private int[] x_snake = new int[BOARD_HEIGHT * BOARD_WIDTH];
    private int[] y_snake = new int[BOARD_HEIGHT * BOARD_WIDTH];

    private int[] x_fruit = new int[DEFAULT_FRUIT_NUM];
    private int[] y_fruit = new int[DEFAULT_FRUIT_NUM];

    public Board(MainPageController controller) {
        _controller = controller;
        _snakeLength = START_LENGTH;
        _fruitNum = 0;

        initialiseSnake();
        initialiseFruit();
    }


    private void initialiseSnake() {
        for(int i = 0; i < _snakeLength; i++) {
            x_snake[i] = DEFAULT_START_POINT[0]+1;
            y_snake[i] = DEFAULT_START_POINT[1];
        }

        paintSnake();
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
            }
            else if (i == _snakeLength -1) {
                _controller.paintTail(x_snake[i],y_snake[i]);
            }
            else {
                _controller.paintBody(x_snake[i],y_snake[i]);
            }
        }
    }

    private void plantFruit() {
        int x_pos = (int)Math.random()*BOARD_WIDTH;
        int y_pos = (int)Math.random()*BOARD_HEIGHT;

        x_fruit[_fruitNum-1] = x_pos;
        y_fruit[_fruitNum-1] = y_pos;

        _controller.paintFruit(x_pos, y_pos);
    }
}

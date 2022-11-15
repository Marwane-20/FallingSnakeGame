import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JFrame;


public  class Test{
    public static void main(String[] args) {
	        new GameFrame();
	    }	
}

class GameFrame extends JFrame{
    public GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}


 class GamePanel extends JPanel implements ActionListener{
    public enum Direction {
        Right,
        Left,
        Up,
        Down
    }

    static final int screenWidth = 600;
    static final int screenHeight = 600;
    static final int unitSize = 25;
    static final int gameUnits = (screenWidth * screenHeight) / unitSize;
    static final int delay = 75;

    private boolean running = true;

    private Snake snake;
    private Apple apple;

    private Timer timer;
    private Random random;


    public GamePanel() {
        timer = new Timer(delay, this);
        timer.start();
        random = new Random();

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new Adapter());

        startGame();
    }

    public void startGame() {
        running = true;
        snake = new Snake();
        apple = new Apple();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	System.out.println("hereeee\n");
        if (running) {
            move();
            checkCollisions();
        }
        repaint();
    }

    public void move() {
        for (int i = snake.length; i > 0; i--) {
            snake.XPositions[i] = snake.XPositions[i - 1];
            snake.YPositions[i] = snake.YPositions[i - 1];
        }

        switch(snake.direction) {
            case Right:
                snake.XPositions[0] += unitSize;
                break;
            case Left:
                snake.XPositions[0] -= unitSize;
                break;
            case Up:
                snake.YPositions[0] -= unitSize;
                break;
            case Down:
                snake.YPositions[0] += unitSize;
                break;
        }
    }

    public void checkCollisions() {
        checkBodyCollision();
        checkWallCollision();
        checkAppleCollision();
    }

    private void checkBodyCollision() {
        for (int i = 0; i < snake.length; i++) {
            for (int j = 0; i < snake.length; i++) {
                if (snake.XPositions[i] == snake.XPositions[j] && snake.YPositions[i] == snake.YPositions[j]) {
                    if (i != j) {
                        running = false;
                    }
                }
            }
        }
    }
    private void checkWallCollision() {
        if (snake.XPositions[0] > screenWidth) {
            running = false;
        } else if (snake.XPositions[0] < 0) {
            running = false;
        } else if (snake.YPositions[0] > screenHeight) {
            running = false;
        } else if (snake.YPositions[0] < 0) {
            running = false;
        }
    }
    public void checkAppleCollision() {
        for (int i = 0; i < snake.length; i++) {
            if (snake.XPositions[i] == apple.XPosition && snake.YPositions[i] == apple.YPosition) {
                apple = new Apple();
                snake.length++;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g) {
        drawGrid(g);
        
        drawSnake(g);
        drawApple(g);
    }
    private void drawGrid(Graphics g) {
        for (int i = 0; i < screenHeight / unitSize; i++) {
            g.drawLine(i * unitSize, 0, i * unitSize, screenHeight);
            g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
        }
    }
   
    private void drawSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (int i = 0; i < snake.length; i++) {
            g.fillRect(snake.XPositions[i], snake.YPositions[i], unitSize, unitSize);
        }
    }
    private void drawApple(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(apple.XPosition, apple.YPosition, unitSize, unitSize);
    }

    public class Apple {
        public int XPosition;
        public int YPosition;

        public Apple() {
            XPosition = random.nextInt((int)(screenWidth / unitSize)) * unitSize;
            YPosition = random.nextInt((int)(screenHeight / unitSize)) * unitSize;
        }
    }

    public class Snake {
        private final int startXPosition = (int) (gameUnits / 2);
        private final int startYPosition = (int) (gameUnits / 2);

        private final int[] XPositions = new int[gameUnits];
        private final int[] YPositions = new int[gameUnits];

        private int length = 10;
        private Direction direction;

        public Snake() {
            createRandomDirection();
            createBodyPartPositions();
        }

        private void createRandomDirection() {
            int i = 1;
            switch (i) {
                case 1:
                    direction = Direction.Right;
                    break;
                case 2:
                    direction = Direction.Left;
                    break;
                case 3:
                    direction = Direction.Up;
                    break;
                case 4:
                    direction = Direction.Down;
                    break;
            }
        }

        private void createBodyPartPositions() {
            if (direction == Direction.Right) {
                for (int i = this.length; i > 0; i--) {
                    XPositions[i] = ((startXPosition + i) * unitSize);
                }
            } else if (direction == Direction.Left) {
                for (int i = 0; i < this.length; i++) {
                    XPositions[i] = ((startXPosition - i) * unitSize);
                }
            } else if (direction == Direction.Up) {
                for (int i = this.length; i > 0; i--) {
                    YPositions[i] = ((startYPosition + i) * unitSize);
                }
            } else if (direction == Direction.Down) {
                for (int i = 0; i < this.length; i++) {
                    YPositions[i] = ((startYPosition - i) * unitSize);
                }
            }
        }
    }
    public class Adapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    if (snake.direction != GamePanel.Direction.Left) {
                        snake.direction = GamePanel.Direction.Right;
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if (snake.direction != GamePanel.Direction.Right) {
                        snake.direction = GamePanel.Direction.Left;
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (snake.direction != GamePanel.Direction.Down) {
                        snake.direction = GamePanel.Direction.Up;
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (snake.direction != GamePanel.Direction.Up) {
                        snake.direction = GamePanel.Direction.Down;
                    }
                    break;
            }
        }
    }
}
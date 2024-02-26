package com.java.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {

    private static final int BOARD_WIDTH = 40;
    private static final int BOARD_HEIGHT = 40;
    private static final int CELL_SIZE = 20;
    private static final int GAME_SPEED = 150; // in milliseconds
    private static final int INITIAL_SNAKE_LENGTH = 3;

    private List<Point> snake;
    private Point food;
    private int score;
    private Direction direction;
    private boolean running;
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        setLayout(null);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (direction != Direction.DOWN) direction = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != Direction.UP) direction = Direction.DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (direction != Direction.RIGHT) direction = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != Direction.LEFT) direction = Direction.RIGHT;
                        break;
                }
            }
        });
        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        snake.add(new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2));
        for (int i = 1; i < INITIAL_SNAKE_LENGTH; i++) {
            snake.add(new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2 + i));
        }
        food = createFood();
        score = 0;
        direction = Direction.UP;
        running = true;
        timer = new Timer(GAME_SPEED, this);
        timer.start();
    }

    private Point createFood() {
        Random random = new Random();
        int x = random.nextInt(BOARD_WIDTH);
        int y = random.nextInt(BOARD_HEIGHT);
        while (snake.contains(new Point(x, y))) {
            x = random.nextInt(BOARD_WIDTH);
            y = random.nextInt(BOARD_HEIGHT);
        }
        return new Point(x, y);
    }

    private void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head);
        switch (direction) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }
        if (newHead.equals(food)) {
            snake.add(0, newHead);
            food = createFood();
            score++;
        } else {
            Point tail = snake.remove(snake.size() - 1);
            if (snake.contains(newHead) || !isValidPosition(newHead)) {
                running = false;
                timer.stop();
            } else {
                snake.add(0, newHead);
            }
        }
    }

    private boolean isValidPosition(Point point) {
        return point.x >= 0 && point.x < BOARD_WIDTH && point.y >= 0 && point.y < BOARD_HEIGHT;
    }

    private void checkCollision() {
        if (!running) return;
        Point head = snake.get(0);
        if (!isValidPosition(head) || snake.subList(1, snake.size()).contains(head)) {
            running = false;
            timer.stop();
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + score, "Game Over", JOptionPane.PLAIN_MESSAGE);
        initGame();
    }

    private void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fill3DRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE, true);
        }
        g.setColor(Color.RED);
        g.fillOval(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
        } else {
            gameOver();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

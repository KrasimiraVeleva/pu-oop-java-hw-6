import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Screen extends JPanel implements Runnable, KeyListener, ActionListener {

    public static final long serialVersionUID = 1L;
    public static final int WIDTH = 400, HEIGHT = 400;

    private Thread thread;
    private boolean running = false;

    private Snake b;
    private ArrayList<Snake> snake;

    private Apple apple;
    private ArrayList<Apple> apples;

    private Random r;

    private int xColor = 10,
                yColor = 10;
    private int size   = 5;

    private boolean right = true,
                    left  = false,
                    up    = false,
                    down  = false;

    private int ticks = 0;

    JButton button;
    JFrame frame;
    JTextArea textArea;

    /**
     * Метод, който визуализира играта на екрана
     */
    public Screen() {

        button = new JButton();
        frame = new JFrame();
        textArea = new JTextArea();

        button.addActionListener(this);
        textArea.setLineWrap(true);
        frame.setLayout(new BorderLayout());
        frame.add(textArea, BorderLayout.NORTH);
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        r = new Random();

        snake  = new ArrayList<Snake>();
        apples = new ArrayList<Apple>();

        start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textArea.setText(textArea.getText().concat(""));
    }

    public void tick() {

        if (snake.size() == 0) {
            b = new Snake(xColor, yColor, 10);
            snake.add(b);
        }

        if(apples.size() == 0) {
            int xColor = r.nextInt(39);
            int yColor = r.nextInt(39);

            apple = new Apple(xColor, yColor, 10);
            apples.add(apple);
        }

        for(int i = 0; i < apples.size(); i++) {
            if(xColor == apples.get(i).getxColor() && yColor == apples.get(i).getyColor()) {
                size++;
                apples.remove(i);
                i++;
            }
        }

        for(int i =0; i < snake.size(); i++) {
            if(xColor == snake.get(i).getxColor() && yColor == snake.get(i).getyColor()) {
                if(i != snake.size() - 1) {
                    stop();
                }
            }
        }
        if(xColor < 0 || xColor > 39 || yColor < 0 || yColor > 39) {
            stop();
        }
        ticks++;

        if(ticks > 250000) {
            if(right) xColor++;
            if(left) xColor--;
            if(up) yColor--;
            if(down) yColor++;

            ticks = 0;

            b = new Snake(xColor, yColor, 10);
            snake.add(b);

            if(snake.size() > size) {
                snake.remove(0);
            }
        }
    }

    /**
     * Метод, който изобразява змията и ябълката
     */
    public void paint(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        for (int i = 0; i < WIDTH / 10; i++) {
            g.drawLine(i * 10, 0, i * 10, HEIGHT);
        }
        for (int i = 0; i < HEIGHT / 10; i++) {
            g.drawLine(0, i * 10, WIDTH, i * 10);
        }

        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }
        for(int i = 0; i < apples.size(); i++) {
            apples.get(i).draw(g);
        }
    }

    public void start() {
        running = true;
        thread  = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
            tick();
            repaint();
        }
    }

    /**
     * Метод, който изпълнява макро функциите
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT && !left) {
            up    = false;
            down  = false;
            right = true;
        }
        if(key == KeyEvent.VK_LEFT && !right) {
            up   = false;
            down = false;
            left = true;
        }
        if(key == KeyEvent.VK_UP && !down) {
            left  = false;
            right = false;
            up    = true;
        }
        if(key == KeyEvent.VK_DOWN && !up) {
            left  = false;
            right = false;
            down  = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}

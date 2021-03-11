import javax.swing.*;

public class Frame {

    private static Object ActionListener;

    /**
     * @author Красимира Велева
     */
    public  Frame() {
        JFrame frame = new JFrame();
        Screen screen = new Screen();

        frame.add(screen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Snake");
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Frame();
        ActionListener = new Screen();
    }
}

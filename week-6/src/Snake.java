import java.awt.Color;
import java.awt.Graphics;

public class Snake {

    private int xColor, yColor, width, height;

    /**
     * Метод, визуализиращ змията
     */
    public Snake(int xColor, int yColor, int tileSize) {

        this.xColor = xColor;
        this.yColor = yColor;
        width       = tileSize;
        height      = tileSize;
    }

    public void tick() {
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(xColor * width, yColor * height, width, height);
    }

    public int getxColor() {
        return xColor;
    }

    public void setxColor(int xColor) {
        this.xColor = xColor;
    }

    public int getyColor() {
        return yColor;
    }

    public void setyColor(int yColor) {
        this.yColor = yColor;
    }
}

package library;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/19/12
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Vec {
    public double mx;
    public double my;

    public double x;
    public double y;

    public Vec(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec(double mx, double my, double centerX, double centerY) {
        this.x = convertX(mx, centerX);
        this.y = convertY(my, centerY);
        this.mx = mx;
        this.my = my;
    }

    public int quadrant() {
        int q = 0;
        if(x > 0 && y > 0) {
            return 1;
        } else if (x < 0 && y > 0) {
            return 2;
        } else if (x < 0 && y < 0) {
            return 3;
        } else if (x > 0 && y < 0) {
            return 4;
        } else {
            return 0;
        }
    }

    public String toString() {
        return "(x:" + x + ", y:" + y + ") quadrant: " + quadrant()+")";
    }

    public static double convertX(double mouseX, double originX) {
        return mouseX - originX;
    }

    public static double convertY(double mouseY, double originY) {
        return originY - mouseY;
    }
}

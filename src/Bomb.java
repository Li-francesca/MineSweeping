import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 封装Bomb炸弹类
 *
 * @author wangx
 */

public class Bomb {

    //地雷横坐标
    private int x;
    //纵坐标
    private int y;
    //情况数 13种
    private int what;
    private int hide = 0;
    //窗口宽度
    private int w = 19;
    //窗口高度
    private int h = 19;
    private MineClient mc;
    private Toolkit tk = Toolkit.getDefaultToolkit();
    private Image bomb = tk.getImage("Image/bomb.jpg");     //扫到炸弹效果图
    private Image bomb0 = tk.getImage("Image/bomb0.jpg");   //炸弹
    private Image zeroBomb = tk.getImage("Image/0.jpg");    //数字为0 ，自动延伸8个方向，直到遇见数字（1-8）为止
    private Image oneBomb = tk.getImage("Image/1.jpg");     //数字为1
    private Image twoBomb = tk.getImage("Image/2.jpg");     //数字为2
    private Image threeBomb = tk.getImage("Image/3.jpg");   //数字为3
    private Image fourBomb = tk.getImage("Image/4.jpg");    //数字为4
    private Image fiveBomb = tk.getImage("Image/5.jpg");    //数字为5
    private Image sixBomb = tk.getImage("Image/6.jpg");     //数字为6
    private Image severnBomb = tk.getImage("Image/7.jpg");  //数字为7
    private Image eightBomb = tk.getImage("Image/8.jpg");   //数字为8
    private Image flag = tk.getImage("Image/flag.jpg");     //右键标记地雷
    private Image flag2 = tk.getImage("Image/flag2.jpg");   //右键问号
    private Image bg = tk.getImage("Image/s.jpg");          //初空白

    public Bomb() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Bomb(int x, int y, int what, MineClient mc) {
        super();
        this.x = x;
        this.y = y;
        this.what = what;
        this.mc = mc;
    }

        //set and get 方法
    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public int getHide() {
        return hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }


    public void draw(Graphics g) {
        switch (what) {
            case 0:                                   //对应图0
                g.drawImage(zeroBomb, x, y, w, h, mc);
                break;
            case 1:                                   //对应图1
                g.drawImage(oneBomb, x, y, w, h, mc);
                break;
            case 2:                                   //对应图2
                g.drawImage(twoBomb, x, y, w, h, mc);
                break;
            case 3:                                   //对应图3
                g.drawImage(threeBomb, x, y, w, h, mc);
                break;
            case 4:                                   //对应图4
                g.drawImage(fourBomb, x, y, w, h, mc);
                break;
            case 5:                                   //对应图5
                g.drawImage(fiveBomb, x, y, w, h, mc);
                break;
            case 6:                                   //对应图6
                g.drawImage(sixBomb, x, y, w, h, mc);
                break;
            case 7:                                   //对应图7
                g.drawImage(severnBomb, x, y, w, h, mc);
                break;
            case 8:                                   //对应图8
                g.drawImage(eightBomb, x, y, w, h, mc);
                break;
            case 9:                                   //对应图bomb炸弹
                g.drawImage(bomb, x, y, w, h, mc);
                break;
            case 10:                                   //对应图bomb0
                g.drawImage(bomb0, x, y, w, h, mc);
                break;
            case 11:                                   //对应图flag
                g.drawImage(flag, x, y, w, h, mc);
                break;
            case 12:                                   //对应图flag2
                g.drawImage(flag2, x, y, w, h, mc);
                break;
            case 13:                                   //对应图bg
                g.drawImage(bg, x, y, w, h, mc);
                break;
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, w, h);
    }
}

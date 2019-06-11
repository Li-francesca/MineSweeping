import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * MineClient扫雷客户端类
 *初始化地雷，刷新线程 画图等
 *
 * @author litj
 * @time 2019/5/15
 */

public class MineClient extends JFrame {

    private static final long serialVersionUID = 1L;

    //屏幕宽度
    private int screenWidth;
    //屏幕高度
    private int screenHeight;
    //图片宽度
    private int imgWidth = 20;
    //图片高度
    private int imgHeight = 20;
    //地图的行数
    private int rowNum = 0;
    //地图的列数
    private int colNum = 0;
    //地雷的总数
    private int mineNum = 99;
    //计时器
    private int timer = 0;
    //游戏时间
    private int time = 0;
    //未扫雷的个数
    private int restMine;
    //不是雷的个数
    private int notMine;

    private MyPanel myPanel;
    //当前游戏状态
    private String gameState = "start";
    //第一次点击
    private boolean firstClick = true;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem lowLevel;
    private JMenuItem midLevel;
    private JMenuItem heightLevel;
    private JMenuItem restart;
    private Toolkit tk = Toolkit.getDefaultToolkit();
    private Image icon = tk.getImage("Image/icon.jpg");

    //地图集合
    private ArrayList<Bomb> bombList = new ArrayList<Bomb>();


    /**
     * mineClient构造器
     * @param screenWidth
     * @param screenHeight
     * @param mineNum
     */
    public MineClient(int screenWidth, int screenHeight, int mineNum) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.mineNum = mineNum;

        //初始化菜单栏
        initMenu();
        setTitle("扫 雷");
        setIconImage(icon);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //JFrame消失了，并不代表程序结束,如果没有这句话，JVM就还没有终止，此时关闭的窗口只是个假象。setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)是使用 System exit 方法退出应用程序。
        setVisible(true);  //表示可以执行paint方法开始画图并显示到屏幕上了，即可以运行开始画图了

        initList();
        myPanel = new MyPanel();
        myPanel.setBackground(Color.darkGray);
        add(myPanel);

        //鼠标事件
        myPanel.addMouseListener(new MyMouseListener(this));
        new updateThread().start();
    }

    /*
     * 初始化菜单栏
     */
    private void initMenu() {

        menu = new JMenu("设置");  //参数设置
        menuBar = new JMenuBar();   //连接Jmenu
        lowLevel = new JMenuItem("初级(10)");  //JmenuItem 菜单项
        midLevel = new JMenuItem("中级(44)");
        heightLevel = new JMenuItem("高级(99)");
        restart = new JMenuItem("重启");

        lowLevel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
                new MineClient(225, 305, 10);
            }
        });

        midLevel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
                new MineClient(380, 460, 44);
            }
        });

        heightLevel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
                new MineClient(660, 460, 99);
            }
        });

        restart.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
                new MineClient(screenWidth, screenHeight, mineNum);
            }
        });

        menu.add(restart);
        menu.add(new JSeparator());
        menu.add(lowLevel);
        menu.add(midLevel);
        menu.add(heightLevel);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public boolean isFirstClick() {
        return firstClick;
    }

    public void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Bomb> getBombList() {
        return bombList;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public int getMineNum() {
        return mineNum;
    }

    //创建地图
    private void initList() {
        //根据窗口大小进行计算方格数目
        for (int i = imgWidth; i < this.getWidth() - 2 * imgWidth; i += imgWidth) {
            for (int j = imgWidth; j < this.getHeight() - 6 * imgWidth; j += imgHeight) {
                rowNum = rowNum > i / imgWidth ? rowNum : i / imgWidth;
                colNum = colNum > j / imgWidth ? colNum : j / imgWidth;
                Bomb bomb = new Bomb(i, j, 13, this);
                bombList.add(bomb);
            }
        }
    }

    public static void main(String[] args) {
        new MineClient(225, 305, 10);

    }


    //自定义panel   Jpanel是java的GUI工具包swing下的面板容器类，它的功能是对窗体具有相同逻辑功能的组建进行组合，且自身可以嵌套组合。 可以加入到Jframe的窗体中。（Jframe不能自身嵌套）
    public class MyPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        public void paint(Graphics g) {
            super.paintComponent(g);
            restMine = mineNum;
            notMine = 0;
            //画地雷 数字
            for (Bomb bomb : bombList) {
                bomb.draw(g);
                if (bomb.getWhat() == 11)//旗子 表示判断为地雷
                    restMine--;//剩余地雷数则减少
                if (bomb.getWhat() >= 0 && bomb.getWhat() <= 8)//如果是0-8 就不是地雷
                    notMine++;
            }
            //游戏失败
            if (gameState.equals("lose")) {
                for (Bomb bomb : bombList) {
                    if (bomb.getHide() == 9) {//当获取到的是炸弹时 游戏失败
                        bomb.setWhat(bomb.getHide());
                    }
                }
                Font font = new Font("微软雅黑", Font.BOLD, 20);
                g.setFont(font);
                g.setColor(new Color(248, 29, 56));
                g.drawString("GAME OVER!!", this.getWidth() / 2 - 80,
                        this.getHeight() / 2);


            }

            //画当前游戏进行时间和未扫的地雷数目
            drawTimeAndMineNum(g);

            //取得游戏胜利 雷+非雷==总的格子数
            if (!gameState.equals("lose") && notMine + mineNum == colNum * rowNum) {
                gameState = "win";
                Toolkit tk = Toolkit.getDefaultToolkit();
                //Image img = tk.getImage("Image/win.jpg");
                Font font1 = new Font("微软雅黑", Font.BOLD, 30);
                g.setFont(font1);
                g.setColor(new Color(248, 29, 56));
                g.drawString("YOU WIN!!", this.getWidth() / 2 - 100, 30);
            }
            /* //画当前游戏进行时间和未扫的地雷数目
            drawTimeAndMineNum(g);*/
        }

        private void drawTimeAndMineNum(Graphics g) {
            Font font = new Font("微软雅黑", Font.PLAIN, 12);
            g.setFont(font);
            g.setColor(Color.white);
            //g.drawString("time" + time + " s", 0, this.getHeight() - 20);
            g.drawString( "剩余地雷：" + restMine + " ",0, this.getHeight() - 20);
            //g.drawString("Residual Mine：" + restMine + " ", this.getWidth() - 100, this.getHeight() - 20);
            g.drawString(" 时 间: " + time + " s", this.getWidth() - 100, this.getHeight() - 20);

        }
    }

    //屏幕每隔100ms刷新一次
    public class updateThread extends Thread {
        public void run() {

            while (true) {
                repaint();
                /*
                * 第一次点击后赋值为了false 并且 状态为开始 才计时
                * 非第一次点击 firstClick初值为true 或者 是win 或者lose 就不计时
                * */
                if (!firstClick&&gameState.equals("start")) {

                    timer += 100;
                    if (timer == 1000) {
                        timer = 0;
                        time++;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

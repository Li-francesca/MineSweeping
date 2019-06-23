import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * MyMouseListener鼠标监听类
 *
 * @author FengShuYi
 * @time 2019/5/15
 */

public class MyMouseListener extends MouseAdapter {
    private MineClient mc;
    private int colNum;//列
    private int rowNum;//行
    private boolean isFirstClick;
    private ArrayList<Bomb> bombList = new ArrayList<Bomb>();
    boolean[] vis ;  //是否翻过？
    public MyMouseListener() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MyMouseListener(MineClient mc) {
        super();
        this.mc = mc;
        colNum = mc.getColNum();
        rowNum = mc.getRowNum();
        vis = new boolean[colNum * rowNum];
        bombList = mc.getBombList();
        this.isFirstClick=mc.isFirstClick();
    }
    /*
     * 鼠标左键  那么显示当前位置的地雷
     * 鼠标右键  那么标记当前位置
     * 左键、滚轮、右键  BUTTON 1，2，3
     */
    public void mouseReleased(MouseEvent e) {//鼠标释放进行判断
        if (mc.getGameState().equals("lose")) {//
            return;
        }
        int x = e.getX();
        int y = e.getY();
        Rectangle rec = new Rectangle(x, y, 1, 1);
        if (e.getButton() == MouseEvent.BUTTON1) {//左键 10种情况 0-8 空白符+八个数字 和一个炸弹
            for (Bomb bomb : bombList) {
                if (rec.intersects(bomb.getRec())) {//判断点击部分和面板中的格子是否有相交的部分
                    if (bomb.getHide() == 9) {//炸弹
                        mc.setGameState("lose");
                    } else {
                        if (bomb.getHide() == 0) {//若是空白符就自动扩大区域 直到周围出现数字边界
                            increasePoint(bombList.indexOf(bomb));
                        }
                        bomb.setWhat(bomb.getHide());
                    }

                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {//右键 三种情况 旗子 问号 灰格（还原）
            for (Bomb bomb : bombList) {
                if (rec.intersects(bomb.getRec())) {//判断点击部分和面板中的格子是否有相交的部分
                    if(bomb.getWhat()!=bomb.getHide()){
                        if(bomb.getWhat()==13){//当前显示为灰格 即右键第一次是旗子
                            bomb.setWhat(11);
                        }
                        else if(bomb.getWhat()==11){//当前显示为旗子 即即右键第二次是问号
                            bomb.setWhat(12);
                        }
                        else if(bomb.getWhat()==12){//当前显示为问号 即右键第三次是灰格（还原）
                            bomb.setWhat(13);
                        }
                    }
                }
            }
        }

    }

    //自动扩大区域直到遇见数字
    private void increasePoint(int index) {
        if (vis[index])
            return;
        vis[index] = true;
        //edgeU edgeD边界状态值
        boolean edgeU = false, edgeD = false;
        if ((index + 1) % (colNum) != 0)//是否在最右一列
            edgeU = true;
        if (index % (colNum) != 0)//是否在最左一列
            edgeD = true;
        if (judgeLimit(index - 1) && edgeD) {//左边
            Bomb bomb = bombList.get(index - 1);
            setVis(bomb, index - 1);
        }

        if (judgeLimit(index + 1) && edgeU) {//右边
            Bomb bomb = bombList.get(index + 1);
            setVis(bomb, index + 1);
        }

        if (judgeLimit(index - colNum)) {//上一行
            Bomb bomb = bombList.get(index - colNum);
            setVis(bomb, index - colNum);
        }

        if (judgeLimit(index + colNum)) {//下一行
            Bomb bomb = bombList.get(index + colNum);
            setVis(bomb, index + colNum);
        }

        if (judgeLimit(index - colNum + 1) && edgeU) {//右上方
            Bomb bomb = bombList.get(index - colNum + 1);
            setVis(bomb, index - colNum + 1);
        }

        if (judgeLimit(index - colNum - 1) && edgeD) {//左上方
            Bomb bomb = bombList.get(index - colNum - 1);
            setVis(bomb, index - colNum - 1);
        }

        if (judgeLimit(index + colNum + 1) && edgeU) {//右下方
            Bomb bomb = bombList.get(index + colNum + 1);
            setVis(bomb, index + colNum + 1);
        }

        if (judgeLimit(index + colNum - 1) && edgeD) {//左下方
            Bomb bomb = bombList.get(index + colNum - 1);
            setVis(bomb, index + colNum - 1);
        }

    }
    //判断边界
    private boolean judgeLimit(int i) {
        if (i >= 0 && i < bombList.size())
            return true;
        return false;
    }
    //显示某位置
    public void setVis(Bomb bomb, int index) {
        if (bomb.getWhat() == bomb.getHide() && bomb.getWhat() != 0)
            return;
        if (bomb.getHide() >= 0 && bomb.getHide() <= 8 && bomb.getHide() != 9) {
            bomb.setWhat(bomb.getHide());
            if (bomb.getWhat() == 0)
                increasePoint(index);
        } else {
            increasePoint(index);//?
        }
    }
    /*
     * 按下鼠标左键的同时开始初始化地图
     *
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (mc.getGameState().equals("lose")) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (isFirstClick) {
                isFirstClick = false;
                mc.setFirstClick(false);
                initBomb(e);
                checkBomb();
            }
        }
    }

    private void checkBomb() {

        for (Bomb bomb : bombList) {
            int x = bombList.indexOf(bomb);
            //edgeU edgeD边界状态值
            boolean edgeU = false, edgeD = false;
            if ((x + 1) % (colNum) != 0)//当前位置是否为最右列
                edgeU = true;
            if (x % (colNum) != 0)//当前位置是否为最左列
                edgeD = true;
            if (bomb.getHide() != 9) {//当前位置不是炸弹时 判断其周围八个位置
                if (judge(x - 1) && edgeD)
                    bomb.setHide(bomb.getHide() + 1);
                if (judge(x + 1) && edgeU)
                    bomb.setHide(bomb.getHide() + 1);
                if (judge(x - colNum))
                    bomb.setHide(bomb.getHide() + 1);
                if (judge(x + colNum))
                    bomb.setHide(bomb.getHide() + 1);
                if (judge(x - colNum + 1) && edgeU)
                    bomb.setHide(bomb.getHide() + 1);
                if (judge(x - colNum - 1) && edgeD)
                    bomb.setHide(bomb.getHide() + 1);
                if (judge(x + colNum + 1) && edgeU)
                    bomb.setHide(bomb.getHide() + 1);
                if (judge(x + colNum - 1) && edgeD)
                    bomb.setHide(bomb.getHide() + 1);
            }
        }
    }
    //判断某位置是否是地雷
    private boolean judge(int x) {
        if (x >= 0 && x < bombList.size()) {
            if (bombList.get(x).getHide() == 9)
                return true;
        }
        return false;
    }
    /*
     * 初始化地雷 （开始玩游戏的时候要保证第一下点击的不是地雷）
     */
    private void initBomb(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Rectangle rec = new Rectangle(x, y, 1, 1);
        Bomb bombTemp=new Bomb();
        int what=0;
        //为了避免第一下点击的不是地雷 首先让它设置为地雷，初始化地雷完成后 再恢复原样
        for (Bomb bomb : bombList) {
            if(rec.intersects(bomb.getRec())){
                what=bomb.getHide();
                bombTemp=bomb;
                bomb.setHide(9);
                break;
            }
        }
        //使用随机数  初始化地图
        Random r = new Random();
        for (int i = 0; i < mc.getMineNum(); i++) {
            while (true) {
                int index = r.nextInt(bombList.size());
                if (bombList.get(index).getHide() != 9) {
                    bombList.get(index).setHide(9);
                    break;
                }
            }
        }
        bombTemp.setHide(what);
    }
}

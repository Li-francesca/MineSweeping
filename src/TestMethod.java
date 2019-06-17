import org.junit.Test;

/**
 * Created by WangXue on 2019/6/10.
 */
public class TestMethod {
    @Test
    public  void testMineClient () throws Exception{
        MineClient mineClient =new MineClient(225,305,10);
        System.out.println(mineClient);
        System.out.println(mineClient.getGameState());
        System.out.println(mineClient.getMineNum());
        System.out.println(mineClient.isFirstClick());
        System.out.println(mineClient.getGraphics());


    }
    @Test
    public  void testBombList () throws Exception{
        MineClient mineClient =new MineClient(225, 305, 10);
        System.out.println(mineClient.getRowNum());
        System.out.println(mineClient.getColNum());
        System.out.println(mineClient.getMineNum());
        System.out.println(mineClient.getBombList());

    }

    @Test
    public  void testHide () throws Exception{
        Bomb bomb= new Bomb();
        bomb.setHide(8);
        System.out.println(bomb.getHide());

    }

    @Test
    public  void testWhat () throws Exception{
        Bomb bomb= new Bomb();
        bomb.setWhat(11);
        System.out.println(bomb.getWhat());
}


    @Test
    public  void testGameState () throws Exception{
        MineClient mineClient =new MineClient(356,255,10);
        mineClient.setGameState("游戏开始");
        System.out.println(mineClient.getGameState());

    }

    @Test
    public  void testRowNum () throws Exception{
        MineClient mineClient =new MineClient(254,100,10);
        System.out.println(mineClient.getRowNum());
    }
    @Test
    public  void testColNum () throws Exception{
        MineClient mineClient =new MineClient(254,100,10);
        System.out.println(mineClient.getColNum());
    }

    @Test
    public void testMouseListener(){
        MineClient mineClient =new MineClient(254,100,10);
        MyMouseListener myMouseListener =new MyMouseListener();
        System.out.println(myMouseListener);
    }



}

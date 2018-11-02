import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

/*
 * Author：Lynne
 * Date：9/19/2018
 * 功能：扫雷游戏主窗口和main方法
 */
public class MineGame extends JFrame implements ActionListener{
    JMenuBar bar;
    JMenu fileMenu;
    JMenu fileMenu1;
    JMenuItem 初级,中级,高级,专家级,扫雷英雄榜,自定义雷区;
    JMenuItem 外挂,关于扫雷,教程;

    MineArea mineArea=null;
    File 英雄榜=new File("英雄榜.txt");
    Hashtable hashtable=null;
    ShowRecord showHeroRecord=null;
    Custom c = null;
    MineGame() throws Exception {
        mineArea=new MineArea(16,16,40,1);
        add(mineArea,BorderLayout.CENTER);
        bar=new JMenuBar();
        fileMenu=new JMenu("游戏");
        fileMenu1 = new JMenu("帮助");
        初级=new JMenuItem("初级");
        中级=new JMenuItem("中级");
        高级=new JMenuItem("高级");
        专家级 = new JMenuItem("专家级");
        自定义雷区 = new JMenuItem("自定义");
        扫雷英雄榜=new JMenuItem("扫雷英雄榜");
        教程 = new JMenuItem("教程");
        关于扫雷 = new JMenuItem("关于“扫雷”");
        外挂 = new JMenuItem("外挂");
        fileMenu.add(初级);
        fileMenu.add(中级);
        fileMenu.add(高级);
        fileMenu.add(专家级);
        fileMenu.add(自定义雷区);
        fileMenu.addSeparator();//分割线
        fileMenu.add(扫雷英雄榜);
        fileMenu1.add(教程);
        fileMenu1.add(外挂);
        fileMenu1.add(关于扫雷);

        bar.add(fileMenu);
        bar.add(fileMenu1);
        setJMenuBar(bar);

        初级.addActionListener(this);
        中级.addActionListener(this);
        高级.addActionListener(this);
        专家级.addActionListener(this);
        扫雷英雄榜.addActionListener(this);
        自定义雷区.addActionListener(this);
        教程.addActionListener(this);
        外挂.addActionListener(this);
        关于扫雷.addActionListener(this);

        hashtable=new Hashtable();

        hashtable.put("初级","初级#"+999+"#匿名");
        hashtable.put("中级","中级#"+999+"#匿名");
        hashtable.put("高级","高级#"+999+"#匿名");
        hashtable.put("专家级","专家级#"+999+"#匿名");  //#号识别数字的大小进行比较

        if(!英雄榜.exists()) {
            try{ FileOutputStream out=new FileOutputStream(英雄榜);
                ObjectOutputStream objectOut=new ObjectOutputStream(out);
                objectOut.writeObject(hashtable);
                objectOut.close();
                out.close();
            }
            catch(IOException e){}
        }
        showHeroRecord=new ShowRecord(this,hashtable);
        setTitle("扫雷");
        setIconImage(Toolkit.getDefaultToolkit().getImage(String.valueOf(ImageIconFactory.getIcon())));
        setBounds(100,100,347,457);  //设置游戏窗体的位置
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //设置默认关闭模式
        validate();
    }
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == 初级) {

                mineArea.initMineArea(8, 8, 10, 1);

                setBounds(100, 100, 207, 317);
//            mineArea.setBounds(100,100,195,248);
            }
            if (e.getSource() == 中级) {
                mineArea.initMineArea(16, 16, 40, 2);
                setBounds(100, 100, 347, 457);
                //mineArea.pNorth.setBorder();
            }
            if (e.getSource() == 高级) {
                mineArea.initMineArea(22, 22, 99, 3);
                setBounds(100, 100, 460, 580);
                //mineArea.pNorth.setBorder();
            }
        if(e.getSource()==专家级){
            mineArea.initMineArea(28,28,99,4);
            setBounds(100,100,580,680);
        }
            if (e.getSource() == 自定义雷区) {
                c = new Custom();
                c.mineArea = mineArea;
                c.setVisible(true);
            }
            if (e.getSource() == 扫雷英雄榜) {
                if (showHeroRecord != null)
                    showHeroRecord.setVisible(true);
            }
            if (e.getSource() == 教程) {
                help h = new help();
                h.setVisible(true);
            }
            if (e.getSource() == 关于扫雷) {
                message mes = new message();
                mes.setVisible(true);
            }
            if (e.getSource() == 外挂){
                mineArea.cheat();
            }
            validate();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public static void main(String args[])throws Exception{
        new MineGame();
    }
}

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class testUI extends JFrame implements ActionListener{
    public static int result,showNumb;//result表示标记是雷的个数，而showNumb表示点开的个数
    private boolean isFirst = true;//设置必须点击新游戏才能开始游戏
    private JLabel jlbtime,jlbremain;
    public static JLabel time;//时间
    public static JLabel remainMine;//剩余雷的个数
    private JButton start;
    private JPanel jpane;
    public static int row,col,mineNumber;//雷的排列，行和列，以及雷的总个数
    private Dimension dim;
    private JRadioButtonMenuItem[] difficult;//菜单项

    public testUI(){
        super("扫雷");
        dim=this.getToolkit().getScreenSize();//获取屏幕分辨率
        this.setLocation(dim.width*2/5, dim.height/3);//设置位置，根据难易程度做出人性化变动
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMenu();
        jlbremain=new JLabel("剩余地雷：");
        time=new JLabel("10");
        remainMine=new JLabel("0");
        jlbtime=new JLabel("耗时：");
        start=new JButton("新游戏");
        start.setActionCommand("new");
        start.addActionListener(this);
        JPanel p=new JPanel();
        p.add(jlbremain);p.add(remainMine);p.add(start);p.add(jlbtime);p.add(time);
        this.add(p,BorderLayout.NORTH);
        this.row=9;this.col=9;this.mineNumber=10;
        restart();
    }

    private void restart() {
        if(jpane!=null){
            this.remove(jpane);
        }
        jpane=new MinePanel(row,col,mineNumber,isFirst);
        this.add(jpane,BorderLayout.CENTER);
        this.remainMine.setText(mineNumber+"");
        this.time.setText("0");
        this.setSize(col*30, row*30+10);
        this.setResizable(false);

        this.setVisible(true);
    }

    public void addMenu(){
        JMenuBar jbar=new JMenuBar();
        this.setJMenuBar(jbar);
        JMenu game = new JMenu("游戏");
        JMenu help = new JMenu("帮助");
        JMenuItem jitemexit=new JMenuItem("退出");
        jitemexit.setActionCommand("exit");
        ButtonGroup bgp=new ButtonGroup();

        String[] difficults={"复杂","中等","简单"};
        difficult=new JRadioButtonMenuItem[difficults.length];
        for(int i=difficults.length-1;i>=0;i--){
            difficult[i]=new JRadioButtonMenuItem(difficults[i],true);
            difficult[i].addActionListener(this);
            bgp.add(difficult[i]);
            game.add(difficult[i]);
        }
        game.addSeparator();jitemexit.addActionListener(this);game.add(jitemexit);
        jbar.add(game);
        JMenuItem jitemview=new JMenuItem("关于扫雷");
        jitemview.setActionCommand("view");
        jitemview.addActionListener(this);
        help.add(jitemview);
        jbar.add(help);
    }

    public static void main(String[] args){
        new testUI();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("new")){
            this.result=0;
            this.showNumb=0;
            this.isFirst=false;
            restart();
            return ;
        }
        if(e.getActionCommand().equals("简单")){
            this.row=9;this.col=9;this.mineNumber=10;
            this.setLocation(dim.width*2/5, dim.height/3);
            restart();
            return ;
        }
        if(e.getActionCommand().equals("中等")){
            this.row=16;this.col=16;this.mineNumber=40;
            this.setLocation(dim.width*2/5, dim.height/5);
            restart();
            return ;
        }
        if(e.getActionCommand().equals("复杂")){
            this.row=16;this.col=30;this.mineNumber=99;
            this.setLocation(dim.width/7, dim.height/7);
            restart();
            return ;
        }
        if(e.getActionCommand().equalsIgnoreCase("exit")){
            System.exit(0);
        }
        if(e.getActionCommand().equalsIgnoreCase("view")){
            JOptionPane.showMessageDialog(this,"扫雷的规则:\n\t尽快找到雷区中的所有不是地雷的格子，而不许踩到地雷。" +
                    "点开的数字是几，\n则说明该数字旁边的8个位置中有几个雷，如果挖开的是地雷，则会输掉游戏。");
        }
    }

}

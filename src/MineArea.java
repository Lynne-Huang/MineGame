import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
//minearea是一个容器

public  class MineArea extends JPanel implements ActionListener,MouseListener, AncestorListener {
    JStatusPanel jstatusPanel = new JStatusPanel();
    JBlockPanel jBlockPanel = new JBlockPanel();
//    JButton reStart = new JButton();
    Block [][] block;
    BlockView [][] blockView;
    LayMines lay;  //随机分配地雷
    int row,colum,mineCount,markMount;//雷区的行数、列数以及地雷个数和用户给出的标记数
    ImageIcon mark;  //标记的图标
    int grade;   //级别
    //JPanel pCenter,pNorth;  //两个面板
//    JTextField showTime,showMarkedMineCount; //两个文本框，显示用时以及标记数
    Timer time;  //计时器
    int spendTime=0;
    Record record;

    //private JLabel expressionLabel = new JLabel(ImageIconFactory.getFaceSmile());


    public MineArea(int row,int colum,int mineCount,int grade) throws Exception {
//        jstatusPanel.expressionLabel.setPreferredSize(new Dimension(26,26));
//        jstatusPanel.expressionLabel.setIcon(ImageIconFactory.getFaceSmile());
        mark=new ImageIcon(String.valueOf(ImageIconFactory.getFlag()));  //探雷标记
        time=new Timer(1000,this);  //计数器初值1000mm
//        showTime=new JTextField(5);  //时间最多5个字符
//        showMarkedMineCount=new JTextField(5);  //地雷数
//        showTime.setHorizontalAlignment(JTextField.CENTER);  //水平对齐方式
//        showMarkedMineCount.setHorizontalAlignment(JTextField.CENTER);  //垂直对齐方式
//        showMarkedMineCount.setFont(new Font("Arial",Font.BOLD,16));  //标记地雷数字体
//        showTime.setFont(new Font("Arial",Font.BOLD,16));
//        pCenter=new JPanel();
//        pNorth=new JPanel();
        lay=new LayMines();     //分配内存空间
        initMineArea(row,colum,mineCount,grade); //初始化雷区,见下面的LayMines()
        jstatusPanel.expressionLabel.addAncestorListener(this);  //动作监听器

//        pNorth.add(showMarkedMineCount);  //添加剩余雷
//        pNorth.add(reStart);    //笑脸按钮
//        pNorth.add(showTime);  //添加时间
        setLayout(new BorderLayout());
//        Border bevelBorder = BorderFactory
//                .createBevelBorder(BevelBorder.RAISED);
//        this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 0,
//                0, 0), BorderFactory.createRaisedBevelBorder(BevelBorder.RAISED)));
        add(jstatusPanel,BorderLayout.NORTH);
        add(jBlockPanel,BorderLayout.CENTER);
        jstatusPanel.expressionLabel.addMouseListener(this);
    }

    @Override
    public void ancestorAdded(AncestorEvent event) {

    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {

    }

    @Override
    public void ancestorMoved(AncestorEvent event) {

    }


    private class ExpressionListener extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            if (jstatusPanel.expressionLabel == e.getSource()) {
                jstatusPanel.expressionLabel.setIcon(ImageIconFactory.getFaceSmile());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == jstatusPanel.expressionLabel) {
                jstatusPanel.expressionLabel.setIcon(ImageIconFactory.getFaceSmile());
            }
        }
    }




    public void initMineArea(int row,int colum,int mineCount,int grade) throws Exception {   //初始化雷区

        jBlockPanel.removeAll();  //清空
        jstatusPanel.setLEDMineCountLeft(mineCount);//设置雷数
        jstatusPanel.setTimerValue(0);//计时器归零
        this.row=row;
        this.colum=colum;
        this.mineCount=mineCount;
        this.grade=grade;
        jstatusPanel.expressionLabel.setIcon(ImageIconFactory.getFaceSmile());
        block=new Block[row][colum];  //行列
        //this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        for(int i=0;i<row;i++){
            for(int j=0;j<colum;j++)
                block[i][j]=new Block();  //参数方块分配内存空间
        }
        lay.layMinesForBlock(block,mineCount);
        blockView=new BlockView[row][colum];
        jBlockPanel.setLayout(new GridLayout(row,colum));  //设置为网格式布局管理器
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {   //外观显示
                blockView[i][j]=new BlockView();   //分配内存空间
                blockView[i][j].giveView(block[i][j]); //给block[i][j]提供视图
                jBlockPanel.add(blockView[i][j]);
                blockView[i][j].getBlockCover().addActionListener(this);  //获取BlockCover，获取动作监听器
                blockView[i][j].getBlockCover().addMouseListener(this);
                blockView[i][j].seeBlockCover();   //显示最上层
                blockView[i][j].setSize(14,14);
                blockView[i][j].getBlockCover().setEnabled(true);
                blockView[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                blockView[i][j].getBlockCover().setIcon(null);
            }
        }
        jstatusPanel.setLEDMineCountLeft(mineCount);    //设置雷数
        markMount=mineCount;
        validate();
    }
    public void setRow(int row){
        this.row=row;
    }
    public int getRow(){return this.row;}
    public void setColum(int colum){
        this.colum=colum;
    }
    public int getColum(){return this.colum;}
    public void setMineCount(int mineCount){
        this.mineCount=mineCount;
    }
    public int getMineCount(){return this.mineCount;}
    public void setGrade(int grade) {
        this.grade=grade;
    }
    public int getGrade(){return this.grade;}
    public void actionPerformed(ActionEvent e) {  //动作监听器
        if(e.getSource()!=jstatusPanel.expressionLabel&&e.getSource()!=time) {  //事件源不是重新开始不是时间
            jstatusPanel.startsTimer();
            int m=-1,n=-1; //点下时的坐标
            for(int i=0;i<row;i++) {
                for(int j=0;j<colum;j++) {
                    if(e.getSource()==blockView[i][j].getBlockCover()){
                        m=i;
                        n=j;
                        break;
                    }
                }
            }
            if(block[m][n].isMine()) {  //踩中地雷
                block[m][n].setMineIcon(ImageIconFactory.getRedMine());
                for(int i=0;i<row;i++) {
                    for(int j=0;j<colum;j++) {
                        blockView[i][j].getBlockCover().setEnabled(false);  //显示地雷
                        if(block[i][j].isMine()) {
                            blockView[i][j].seeBlockNameOrIcon();   //显示标签控
                        }
                    }
                }
                jstatusPanel.stopTimer();
                jstatusPanel.expressionLabel.setIcon(ImageIconFactory.getFaceCry());
                spendTime=jstatusPanel.getTimerValue();
                markMount=mineCount;
            }
            else {
                show(m,n);          //见本类后面的show方法
            }
        }
        if(e.getSource()==jstatusPanel.expressionLabel) {   //重新开始
            try {
                initMineArea(row,colum,mineCount,grade);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if(e.getSource()==time){  //累加时间
            spendTime++;
            try {
                jstatusPanel.setTimerValue(spendTime);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        inquireWin();
    }

    public void show(int m,int n) {  //没有踩雷
        if(block[m][n].getAroundMineNumber()>0&&block[m][n].getIsOpen()==false){  //周围有雷
            blockView[m][n].seeBlockNameOrIcon();
            block[m][n].setIsOpen(true);
            return;
        }
        else if(block[m][n].getAroundMineNumber()==0&&block[m][n].getIsOpen()==false){  //周围无雷
            blockView[m][n].seeBlockNameOrIcon();
            block[m][n].setIsOpen(true);
            for(int k=Math.max(m-1,0);k<=Math.min(m+1,row-1);k++) {  //空白处自动挖开功能
                for(int t=Math.max(n-1,0);t<=Math.min(n+1,colum-1);t++)
                    show(k,t);
            }
        }
    }
    public void inquireWin(){   //查询是否成功
        int number=0;
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
                if(block[i][j].getIsOpen()==false)
                    number++;
            }
        }
        if(number==mineCount){  //成功
            jstatusPanel.stopTimer();
            record=new Record();
            switch(grade){
                case 1: record.setGrade("初级");
                    break;
                case 2: record.setGrade("中级");
                    break;
                case 3: record.setGrade("高级");
                    break;
//                case 4: record.setGrade("专家级");
//                    break;
            }
            record.setTime(jstatusPanel.getTimerValue());
            record.setVisible(true);
        }
    }

    public void cheat(){
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
                // blockView[i][j].seeBlockNameOrIcon();
                if(block[i][j].isMine){
                    blockView[i][j].seeBlockNameOrIcon();
                }
            }
        }
    }
    //监听所有的鼠标动作，点笑脸，点空格，点其他地方，左击，右击，双击，右击1下，右击2下，右击3下
    public void mousePressed(MouseEvent e){  //监听鼠标右键
        JButton source=(JButton)e.getSource();
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
                if(e.getModifiers()==InputEvent.BUTTON3_MASK&&
                        source==blockView[i][j].getBlockCover()){  //判断来源
                    if(block[i][j].getIsMark()) {  //如果已经标记
                        source.setIcon(null);
                        block[i][j].setIsMark(false);
                        markMount=markMount+1;
                        try {
                            jstatusPanel.setLEDMineCountLeft(markMount);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    else{  //如果没有标记
                        source.setIcon(mark);
                        block[i][j].setIsMark(true);
                        markMount=markMount-1;
                        try {
                            jstatusPanel.setLEDMineCountLeft(markMount);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
}

import com.sun.javafx.image.BytePixelSetter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;


public class MineArea extends JPanel implements ActionListener,MouseListener{
//    JButton reStart;
    Block [][] block;
    BlockView [][] blockView;
    LayMines lay;
    int row,colum,mineCount,markMount;//雷区的行数、列数以及地雷个数和用户给出的标记数
//    ImageIcon mark;
    int grade;
//    JPanel pCenter/*,pNorth*/;
//    JTextField /*showTime,*/showMarkedMineCount; //显示用时以及标记数
    Timer time;  //计时器
    JStatusPanel jStatusPanel = new JStatusPanel();
    JBlockPanel jBlockPanel = new JBlockPanel();
//    int spendTime=0;
    Record record;


    public MineArea(int row,int colum,int mineCount,int grade) throws Exception {
//        reStart=new JButton("重新开始");
//        mark=new ImageIcon("mark.gif");  //探雷标记
        time=jStatusPanel.timer;//new Timer(1000,this);
//        showTime=new JTextField(5);
//        showMarkedMineCount=new JTextField(5);
//        showTime.setHorizontalAlignment(JTextField.CENTER);
//        showMarkedMineCount.setHorizontalAlignment(JTextField.CENTER);
//        showMarkedMineCount.setFont(new Font("Arial",Font.BOLD,16));
//        showTime.setFont(new Font("Arial",Font.BOLD,16));
//        pCenter=new JPanel();
//        pNorth=new JPanel();
        lay=new LayMines();
        initMineArea(row,colum,mineCount,grade); //初始化雷区,见下面的LayMines()
        jStatusPanel.expressionLabel.addMouseListener(this);
//        reStart.addActionListener(this);
//        pNorth.add(showMarkedMineCount);
//        pNorth.add(reStart);
//        pNorth.add(showTime);
        setLayout(new BorderLayout());
        add(jStatusPanel,BorderLayout.NORTH);
        add(jBlockPanel,BorderLayout.CENTER);

    }



    public void initMineArea(int row,int colum,int mineCount,int grade) throws Exception {
//        pCenter.removeAll();
        jBlockPanel.removeAll();
//        spendTime=0;
        markMount=mineCount;
        this.row=row;
        this.colum=colum;
        this.mineCount=mineCount;
        this.grade=grade;
        block=new Block[row][colum];
        for(int i=0;i<row;i++){
            for(int j=0;j<colum;j++)
                block[i][j]=new Block();
        }
        lay.layMinesForBlock(block,mineCount);
        blockView=new BlockView[row][colum];
        jBlockPanel.setLayout(new GridLayout(row,colum));
//        pCenter.setLayout(new GridLayout(row,colum));
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
                blockView[i][j]=new BlockView();
                blockView[i][j].giveView(block[i][j]); //给block[i][j]提供视图
                jBlockPanel.add(blockView[i][j]);
//                pCenter.add(blockView[i][j]);
                blockView[i][j].getBlockCover().addActionListener(this);
                blockView[i][j].getBlockCover().addMouseListener(this);
                blockView[i][j].seeBlockCover();
                blockView[i][j].getBlockCover().setEnabled(true);
                blockView[i][j].getBlockCover().setIcon(null);
            }
        }
        jStatusPanel.setLEDMineCountLeft(markMount);
//        showMarkedMineCount.setText(""+markMount);
        validate();
    }
    public void setRow(int row){
        this.row=row;
    }
    public void setColum(int colum){
        this.colum=colum;
    }
    public void setMineCount(int mineCount){
        this.mineCount=mineCount;
    }
    public void setGrade(int grade) {
        this.grade=grade;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()!=jStatusPanel.expressionLabel&&e.getSource()!=time) {
//            time.start();
            jStatusPanel.setTimerStart();
            int m=-1,n=-1;
            for(int i=0;i<row;i++) {
                for(int j=0;j<colum;j++) {
                    if(e.getSource()==blockView[i][j].getBlockCover()){
                        m=i;
                        n=j;
                        break;
                    }
                }
            }
            if(block[m][n].isMine()) {
                for(int i=0;i<row;i++) {
                    for(int j=0;j<colum;j++) {
                        blockView[i][j].getBlockCover().setEnabled(false);
                        if(block[i][j].isMine())
                            blockView[i][j].seeBlockNameOrIcon();
                    }
                }
                jStatusPanel.setTimerStop();
                jStatusPanel.setFaceCry();
//                spendTime=0;
                markMount=mineCount;
            }
            else {
                jStatusPanel.setFaceSurprised();
                show(m,n);          //见本类后面的show方法
            }
        }
        if(e.getSource()==jStatusPanel.expressionLabel) {
            try {
                initMineArea(row,colum,mineCount,grade);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
//        if(e.getSource()==time){
//            spendTime++;
//            showTime.setText(""+spendTime);
//        }
        try {
            inquireWin();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void show(int m,int n) {
        if(block[m][n].getAroundMineNumber()>0&&block[m][n].getIsOpen()==false){
            blockView[m][n].seeBlockNameOrIcon();
            block[m][n].setIsOpen(true);
            return;
        }
        else if(block[m][n].getAroundMineNumber()==0&&block[m][n].getIsOpen()==false){
            blockView[m][n].seeBlockNameOrIcon();
            block[m][n].setIsOpen(true);
            for(int k=Math.max(m-1,0);k<=Math.min(m+1,row-1);k++) {
                for(int t=Math.max(n-1,0);t<=Math.min(n+1,colum-1);t++)
                    show(k,t);
            }
        }
    }
    public boolean inquireWin() throws Exception {
        int number=0;
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
                if(block[i][j].getIsOpen()==false)
                    number++;
            }
        }
        if(number==mineCount){
//            time.stop();
            jStatusPanel.setTimerStop();
            jStatusPanel.setFaceHappy();
            record=new Record();
            switch(grade){
                case 1: record.setGrade("初级");
                    break;
                case 2: record.setGrade("中级");
                    break;
                case 3: record.setGrade("高级");
                    break;
            }
            jStatusPanel.setLEDMineCountLeft(markMount);
            record.setTime(jStatusPanel.getTimerValue());
//            record.setTime(spendTime);
            record.setVisible(true);
            return true;
        }
//            jStatusPanel.setFaceCry();

            jStatusPanel.setLEDMineCountLeft(markMount);
        return false;


    }
    public void cheat(){
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
                // blockView[i][j].seeBlockNameOrIcon();
                if(block[i][j].isMine){
                    blockView[i][j].seeBlockNameOrIcon();
                }
//                if(!block[i][j].isMine){
//                    blockView[i][j].seeBlockNameOrIcon();
//                }
            }
        }
    }
    public void mousePressed(MouseEvent e){
        JButton source=(JButton)e.getSource();
        if(e.getSource() == jStatusPanel.expressionLabel)//按笑脸
            try{
            initMineArea(row,colum,mineCount,grade);
            jStatusPanel.resetTimer();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {//BUTTON1_MASK左击
                if(e.getSource()!=jStatusPanel.expressionLabel&&
                        e.getModifiers() ==InputEvent.BUTTON1_MASK
                        && source == blockView[i][j].getBlockCover()) {
//                    blockView[i][j].blockCover.setIcon(ImageIconFactory.getBlankPressed());
                    blockView[i][j].blockCover.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    jStatusPanel.setFaceSurprised();
                    jStatusPanel.setTimerStart();
                    int m = -1, n = -1;
                    if (e.getSource() == blockView[i][j].getBlockCover()) {
                        m = i;
                        n = j;
                        break;
                    }
                    if (block[m][n].isMine()) {
                        for(int r=0;r<row;r++) {
                            for (int c = 0; c < colum; c++) {
                                blockView[r][c].getBlockCover().setEnabled(false);
                                if (block[r][c].isMine()) {
                                    blockView[r][c].seeBlockNameOrIcon();
                                    jStatusPanel.setFaceCry();
                                    jStatusPanel.setTimerStop();

                                }
                            }
                        }

//                spendTime=0;
                        markMount=mineCount;
                    }
                    else {

                        show(m,n);          //见本类后面的show方法
                    }
                }
                if(e.getModifiers()==InputEvent.BUTTON3_MASK&&
                        source==blockView[i][j].getBlockCover()){//BUTTON3_MASK左击
                    jStatusPanel.setFaceSurprised();
                    if(block[i][j].getIsMark()) {
                        jStatusPanel.setFaceSurprised();
                        source.setIcon(null);
                        block[i][j].setIsMark(false);
                        markMount=markMount+1;
                        try {
                            jStatusPanel.setLEDMineCountLeft(markMount);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
//                        showMarkedMineCount.setText(""+markMount);
                    }
                    else{
                        source.setIcon(ImageIconFactory.getFlag());
                        jStatusPanel.setFaceSurprised();
                        block[i][j].setIsMark(true);
                        markMount=markMount-1;
                        try {
                            jStatusPanel.setLEDMineCountLeft(markMount);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
//                        showMarkedMineCount.setText(""+markMount);
                    }
                }
            }
            try {
                inquireWin();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    public void mouseReleased(MouseEvent e){
        try {
            if(inquireWin())jStatusPanel.setFaceHappy();
            else jStatusPanel.setFaceCry();
            jStatusPanel.setFaceSmile();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}


}

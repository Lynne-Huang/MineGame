
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MinePanel extends JPanel implements ActionListener{
    private boolean isFirst;
    private int row,col,mineNumber;
    private JButton[][] mine;
    private int[][] mark;
    Thread t;

    public MinePanel(int row,int col,int mineNumber,boolean isFirst){
        this.isFirst=isFirst;
        this.row=row;
        this.col=col;
        this.mineNumber=mineNumber;
        mine=new JButton[this.row][this.col];
        mark=new int[this.row][this.col];
        this.setLayout(new GridLayout(row,col));
        createMine();
        createButtons();
        if(isFirst){
            firstinist();
        }else{
            init();//初始化
        }
    }
    private void firstinist(){
        for(int i=0;i<mine.length;i++){
            for(int j=0;j<mine[i].length;j++){
                mine[i][j]=new JButton();
                this.add(mine[i][j]);
            }
        }
    }
    private void init() {
        this.setLayout(new GridLayout(row,col));
        for(int i=0;i<mine.length;i++){
            for(int j=0;j<mine[i].length;j++){
                this.add(mine[i][j]);
            }
        }

    }

    private void createButtons() {
        for(int i=0;i<mine.length;i++){
            for(int j=0;j<mine[i].length;j++){
                mine[i][j]=new MineButton(i,j);
                mine[i][j].setSize(6, 6);
                mine[i][j].addActionListener(this);
                mine[i][j].addMouseListener(
                        new MouseAdapter(){
                            public void mouseClicked(MouseEvent e){
                                if(e.getButton()==MouseEvent.BUTTON3){
                                    int remain=Integer.parseInt(testUI.remainMine.getText());
                                    JButton b=(JButton)e.getSource();
                                    if(b.getText().equals("")&&remain>0){
                                        testUI.result++;//System.out.println(CleanMine.showNumb+" "+CleanMine.result);
                                        if(testUI.result==testUI.row*testUI.col-testUI.showNumb){
                                            JOptionPane.showMessageDialog(b, "congratulations ! xjj万岁 !");
                                        }
                                        remain--;
                                        testUI.remainMine.setText(remain+"");
                                        b.setText("$");
                                    }else if(b.getText().equals("$")){
                                        testUI.result--;System.out.println(testUI.showNumb);
                                        remain++;
                                        testUI.remainMine.setText(remain+"");
                                        b.setText("");
                                    }
                                }
                            }
                        }
                );
            }
        }

    }

    private void createMine() {
        int n=0;
        while(n<mineNumber){//随机生成规定个数的雷的位置
            int i=(int)(Math.random()*row);
            int j=(int)(Math.random()*col);
            if(mark[i][j]!=-1){
                mark[i][j]=-1;
                n++;
            }
        }
        for(int i=0;i<mark.length;i++){//对地图做标记
            for(int j=0;j<mark[i].length;j++){
                if(mark[i][j]!=-1){
                    mark[i][j]=getSurroundMineNumber(mark,i,j);
                }
            }
        }
    }

    private int getSurroundMineNumber(int[][] mark2, int n, int m) {
        int numb=0;
        int[] direct={0,1,-1};
        int x,y;
        for(int i=0;i<direct.length;i++){
            for(int j=0;j<direct.length;j++){
                if(i==0&&j==0){
                    continue;
                }
                x=n+direct[i];
                y=m+direct[j];
                if(x>=0 && x<mark2.length && y>=0 &&  y<mark2[0].length){//对周围八个方向进行遍历
                    numb+=(mark2[x][y]==-1? 1:0);
                }
            }
        }
        return numb;
    }

    public void actionPerformed(ActionEvent e) {
        MineButton b=(MineButton)e.getSource();
        int r=b.getRow();
        int c=b.getCol();
        if(mark[r][c]==-1){//踩到雷，将地图所有都显示出来
            //String str=CleanMine.time.getText();
            //isFirst=true;
            testUI.time.setText("0");
            for(int i=0;i<mark.length;i++){
                for(int j=0;j<mark[i].length;j++){
                    if(mark[i][j]==-1){
                        mine[i][j].setText("@");
                    }else if(mark[i][j]==0){
                        mine[i][j].setText("");
                        mine[i][j].setBackground(Color.green);
                    }else{
                        mine[i][j].setText(mark[i][j]+"");
                        mine[i][j].setBackground(Color.green);
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "叫你扫雷，不是踩雷，游戏结束 ！");
        }else{
            showEmpty(mark,r,c);
        }
    }

    private void showEmpty(int[][] mark2, int r, int c) {
        MineButton b=(MineButton)mine[r][c];
        if(b.isCleared()){
            return ;
        }testUI.showNumb++;//System.out.println(CleanMine.showNumb);
        if(testUI.result==testUI.row*testUI.col-testUI.showNumb){
            JOptionPane.showMessageDialog(this,"congratulations ! xjj万岁 !");
        }
        int x,y;
        int[] direct={0,1,-1};
        if(mark[r][c]==0){//若是空的  将其显示出来，并继续遍历
            b.setBackground(Color.green);
            b.setCleared(true);
            for(int i=0;i<direct.length;i++){
                for(int j=0;j<direct.length;j++){
                    if(i==0&&j==0){
                        continue;
                    }
                    if(b.getText().equals("$")){
                        continue;
                    }
                    x=r+direct[i];
                    y=c+direct[j];
                    if(x>=0 && x<mark2.length && y>=0 &&  y<mark2[0].length){
                        //CleanMine.showNumb++;
                        showEmpty(mark2,x,y);
                    }
                }
            }
        }else{
            if(b.getText().equals("$")){//若被标记是雷，则不显示
                return ;
            }
            b.setText(mark[r][c]+"");//若是数字，则将数字显示出来
            b.setBackground(Color.green);
            b.setCleared(true);
        }
    }

}
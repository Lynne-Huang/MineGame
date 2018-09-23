import java.util.*;
import javax.swing.*;
/*
 * Author：Lynne
 * Date：9/19/2018
 * 功能：
 */

public class LayMines{
    ImageIcon mineIcon;
    LayMines() {
        mineIcon=new ImageIcon(String.valueOf(ImageIconFactory.getMine())); //制定地雷图片
    }
    public void layMinesForBlock(Block block[][],int mineCount){  //初始化的地雷
        int row=block.length;
        int column=block[0].length;
        LinkedList<Block> list=new LinkedList<Block>();
        for(int i=0;i<row;i++) {
            for(int j=0;j<column;j++)
                list.add(block[i][j]);
        }
        while(mineCount>0){         //40个地雷随机不重复飞分配
            int size=list.size();             // list返回节点的个数
            int randomIndex=(int)(Math.random()*size);
            Block b=list.get(randomIndex);
            b.setIsMine(true);
            b.setName("雷");
            b.setMineIcon(ImageIconFactory.getMine());
            list.remove(randomIndex);        //list删除索引值为randomIndex的节点
            mineCount--;
        }
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                if(block[i][j].isMine()){  //是地雷
                    block[i][j].setIsOpen(false);
                    block[i][j].setIsMark(false);
                }
                else {  //不是地雷
                    int mineNumber=0;
                    for(int k=Math.max(i-1,0);k<=Math.min(i+1,row-1);k++) {
                        for(int t=Math.max(j-1,0);t<=Math.min(j+1,column-1);t++){
                            if(block[k][t].isMine())
                                mineNumber++;
                        }
                    }
                    block[i][j].setIsOpen(false);
                    block[i][j].setIsMark(false);
                    block[i][j].setName(""+mineNumber);
                    block[i][j].setAroundMineNumber(mineNumber);
                }
            }
        }
    }
}

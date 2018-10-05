import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/*
 * Author：Lynne
 * Date：9/19/2018
 * 功能：创建对象负责为block对象提供试图，以便用户通过该视图与block对象交互
 */

import javax.swing.*;
import java.awt.*;
public class BlockView extends JPanel{
    JLabel blockNameOrIcon; //用来显示Block对象的name、number和mineIcon属性
    JButton blockCover;     //用来遮挡blockNameOrIcon.
    CardLayout card;        //卡片式布局
    BlockView(){
        card=new CardLayout();
        setLayout(card);
        blockNameOrIcon=new JLabel("",JLabel.CENTER);
        blockNameOrIcon.setHorizontalTextPosition(AbstractButton.CENTER);
        blockNameOrIcon.setVerticalTextPosition(AbstractButton.CENTER);
        blockCover=new JButton();
        add("cover",blockCover);
        add("view",blockNameOrIcon);
    }
    public void giveView(Block block){
        if(block.isMine){
            blockNameOrIcon.setText(block.getName());
            blockNameOrIcon.setIcon(block.getMineicon());
        }
        else {
            int n=block.getAroundMineNumber();
            if(n>=1)
                blockNameOrIcon.setText(""+n);
            else
                blockNameOrIcon.setText(" ");
        }
    }
    public void seeBlockNameOrIcon(){
        card.show(this,"view");
        validate();
    }
    public void seeBlockCover(){
        card.show(this,"cover");
        validate();
    }
    public JButton getBlockCover(){
        return blockCover;
    }
}

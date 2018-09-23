import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.*;

public class help  extends JDialog implements ActionListener {
    JButton 关闭;

    public  help(){
        setTitle("扫雷规则");
        setBounds(200,200,240,220);
        setResizable(false);
        setModal(true);



        关闭=new JButton("关闭");
        关闭.addActionListener(this);

        JPanel p1=new JPanel();

        JLabel t1 = new JLabel();
        t1.setText("<html><h3 style='text-align:center;'>扫雷规则</h3>尽快找到雷区中的所有不是地雷的格子,<br />" +
                        "而不许踩到地雷。点开的数字是几,<br />" +
                        "则说明该数字旁边的8个位置中有几个雷,<br />" +
                        "如果挖开的是地雷,则会输掉游戏。</html></br>，"
                );

        p1.add(t1);
        //p1.add(l2);
        p1.add(关闭);
        add(p1);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        setVisible(false);

    }
}

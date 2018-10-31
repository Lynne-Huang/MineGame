import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
* 展示关于“扫雷”
*/

public class message extends JDialog implements ActionListener {


    // 设置标题栏的图标为face.gif

    JButton 关闭;
    public message(){
        setTitle("关于“扫雷”");
        setBounds(100,100,240,120);
        setResizable(false);
        setModal(true);

        JLabel l1=new JLabel("");
        JLabel l2=new JLabel("38115307 黄景琳制作");

        关闭=new JButton("关闭");
        关闭.addActionListener(this);
        JPanel p1=new JPanel();

        p1.add(l1);
        p1.add(l2);
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

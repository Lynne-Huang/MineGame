import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Custom extends JDialog implements ActionListener{
    public MineArea mineArea;
    JLabel length,high,mine;
    JTextField textlength,texthigh,textmine;
    JButton confirm,concel;
    int A,B,C,D;

    public Custom() {
        setTitle("自定义雷区");
        setBounds(100,100,244,193);
        setVisible(true);
        setResizable(false);
        setModal(true);
        setLayout(new GridLayout(2,1));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel p=new JPanel();
        length=new JLabel("长度：");
        high=new JLabel("高度：");
        mine=new JLabel("雷数：");

        textlength=new JTextField(4);
        texthigh=new JTextField(4);
        textmine=new JTextField(4);

        confirm=new JButton("确认");
        concel=new JButton("取消");

        p.add(length);p.add(textlength);
        p.add(high);p.add(texthigh);
        p.add(mine);p.add(textmine);
        p.add(confirm);p.add(concel);
        add(p);

        confirm.addActionListener(this);
        concel.addActionListener(this);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==confirm){
            A=Integer.parseInt(textlength.getText());
            B=Integer.parseInt(texthigh.getText());
            C=Integer.parseInt(textmine.getText());
            if(A<=8&&B<=8){
                D=1;
            }

            else if(A<=16&&B<=16){
                D=2;
            }

            else if(A<=22&&B<=22){
                D=3;
            }
            try {
                mineArea.initMineArea(A,B,C,D);
                setBounds(100,100,A*2,B*2);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        if(e.getSource()==concel){
            this.dispose();
        }
    }

}

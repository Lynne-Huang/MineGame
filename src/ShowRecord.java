import java.io.*;  //显示记录
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/*
 * Author：Lynne
 * Date：9/19/2018
 * 功能：展示当前用户的最好成绩
 */
public class ShowRecord extends JDialog implements ActionListener{
    File file=new File("英雄榜.txt");  //存入数据的文件
    String name=null;                  //用户名为空
    Hashtable hashtable=null;          //创建哈希表
    JButton 确定,重新记分;             //按钮
    JLabel label初级[],label中级[],label高级[];     //标签数组
    public ShowRecord(JFrame frame,Hashtable h) {   //外观
        setTitle("扫雷英雄榜");         //标题
        hashtable=h;
        setBounds(100,100,320,185);
        setResizable(false);
        setVisible(false);
        setModal(true);
        label初级=new JLabel[3];       //设置窗口数组
        label中级=new JLabel[3];
        label高级=new JLabel[3];
        for(int i=0;i<3;i++) {        //分配内存空间
            label初级[i]=new JLabel();
            label初级[i].setBorder(null);
            label中级[i]=new JLabel();
            label中级[i].setBorder(null);
            label高级[i]=new JLabel();
            label高级[i].setBorder(null);
        }
        label初级[0].setText("初级:");  //赋初值
        label初级[1].setText(""+999);
        label初级[2].setText("匿名");
        label中级[0].setText("中级:");
        label中级[1].setText(""+999);
        label中级[2].setText("匿名");
        label高级[0].setText("高级:");
        label高级[1].setText(""+999);
        label高级[2].setText("匿名");
        JPanel pCenter=new JPanel();
        pCenter.setLayout(new GridLayout(3,3));
        for(int i=0;i<3;i++)
            pCenter.add(label初级[i]);
        for(int i=0;i<3;i++)
            pCenter.add(label中级[i]);
        for(int i=0;i<3;i++)
            pCenter.add(label高级[i]);
        readAndShow();
        确定=new JButton("确定");
        重新记分=new JButton("重新记分");
        确定.addActionListener(this);
        重新记分.addActionListener(this);
        JPanel pSouth=new JPanel();
        pSouth.setLayout(new FlowLayout(FlowLayout.RIGHT));  //流式布局（按顺序），靠右
        pSouth.add(重新记分);
        pSouth.add(确定);
        add(pCenter,BorderLayout.CENTER);
        add(pSouth,BorderLayout.SOUTH) ;
    }
    public void readAndShow(){   //读、显示数据
        try{ FileInputStream in=new FileInputStream(file);
            ObjectInputStream object_in=new ObjectInputStream(in);
            hashtable=(Hashtable)object_in.readObject();
            object_in.close();
            in.close();   //数据建立连接
            String temp=(String)hashtable.get("初级:");
            StringTokenizer fenxi=new StringTokenizer(temp,"#");
            label初级[0].setText(fenxi.nextToken());
            label初级[1].setText(fenxi.nextToken());
            label初级[2].setText(fenxi.nextToken());
            temp=(String)hashtable.get("中级:");
            fenxi=new StringTokenizer(temp,"#");
            label中级[0].setText(fenxi.nextToken());
            label中级[1].setText(fenxi.nextToken());
            label中级[2].setText(fenxi.nextToken());
            temp=(String)hashtable.get("高级:");
            fenxi=new StringTokenizer(temp,"#");
            label高级[0].setText(fenxi.nextToken());
            label高级[1].setText(fenxi.nextToken());
            label高级[2].setText(fenxi.nextToken());
        }
        catch(Exception e){}
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==重新记分) {
            hashtable.put("初级","初级#"+999+"#匿名");
            label初级[0].setText("初级:");
            label初级[1].setText(""+999);
            label初级[2].setText("匿名");
            hashtable.put("中级","中级#"+999+"#匿名");
            label中级[0].setText("中级:");
            label中级[1].setText(""+999);
            label中级[2].setText("匿名");
            hashtable.put("高级","高级#"+999+"#匿名");
            label高级[0].setText("高级:");
            label高级[1].setText(""+999);
            label高级[2].setText("匿名");
            try{ FileOutputStream out=new FileOutputStream(file);
                ObjectOutputStream object_out=new ObjectOutputStream(out);
                object_out.writeObject(hashtable);
                object_out.close();
                out.close();
            }
            catch(IOException event){}
//            setVisible(false);
        }
        if(e.getSource()==确定){
            this.dispose();
        }
    }
}

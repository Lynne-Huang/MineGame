import java.io.*;   //写记录
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/*
 * Author：Lynne
 * Date：9/19/2018
 * 功能：保存用户的成绩到文件中
 */

public class Record extends JDialog implements ActionListener{  //动作监听器
    int time=0;
    String grade=null;
    String key=null;
    String message=null;
    JTextField textName;   //文本框（tom）
    JLabel label=null;   //标签控件
    JButton 确定,取消;
    public Record(){
        setTitle("记录你的成绩");   //标题
        this.time=time;   //用时
        this.grade=grade;  //级别
        setBounds(100,100,240,160);
        setResizable(false);
        setModal(true);   ///模式对话框
        确定=new JButton("确定");
        取消=new JButton("取消");
        textName=new JTextField(8);  //初始值至多8个字符
        textName.setText("匿名");    //初始值匿名
        确定.addActionListener(this);  //添加动作监听器
        取消.addActionListener(this);
        setLayout(new GridLayout(2,1));  //网格式布局管理（两行一列）
        label=new JLabel("您现在是...高手,输入您的大名上榜");
        add(label);
        JPanel p=new JPanel();
        p.add(textName);
        p.add(确定);
        p.add(取消);
        add(p);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  //设置关闭
    }
    public void setGrade(String grade){
        this.grade=grade;
        label.setText("您现在是"+grade+"高手,输入您的大名上榜");
    }
    public void setTime(int time){
        this.time=time;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==确定){
            message=grade+"#"+time+"#"+" "+textName.getText();  //级别和用时赋值message
            key=grade;
            writeRecord(key,message);  //扫雷信息写入
            setVisible(false);
        }
        if(e.getSource()==取消){
            setVisible(false);   //设置为不可视
        }
    }
    public void  writeRecord(String key,String message){
        File f=new File("英雄榜.txt");
        try{ FileInputStream in=new FileInputStream(f);  //调入对象/文件输入流
            ObjectInputStream object_in=new ObjectInputStream(in);
            Hashtable hashtable=(Hashtable)object_in.readObject();
            object_in.close();
            in.close();
            String temp=(String)hashtable.get(key);
            StringTokenizer fenxi=new StringTokenizer(temp,"#");  //字符串分析对象
            fenxi.nextToken();  //初级、中级、高级字段取出来
            int n=Integer.parseInt(fenxi.nextToken());  //扫雷用时字段
            if(time<n){
                hashtable.put(key,message);
                FileOutputStream out=new FileOutputStream(f);
                ObjectOutputStream object_out=new ObjectOutputStream(out);
                object_out.writeObject(hashtable);
                object_out.close();
                out.close();
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}

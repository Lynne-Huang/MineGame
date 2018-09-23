import javax.swing.ImageIcon;  //参数方块类
/*
 * Author：Lynne
 * Date：9/19/2018
 * 功能：雷区中“方块”的封装类
 */

public class Block {
    String name;            //名字,比如"雷"或数字
    int aroundMineNumber;   //周围雷的数目
    ImageIcon mineIcon;     //雷的图标
    boolean isMine=false;   //是否是雷
    boolean isMark=false;   //是否被标记
    boolean isOpen=false;   //是否被挖开
    public void setName(String name) {  //设置名字
        this.name=name;
    }
    public void setAroundMineNumber(int n) {  //设置周围雷的数字
        aroundMineNumber=n;
    }
    public int getAroundMineNumber() {  //获取周围雷的数字
        return aroundMineNumber;
    }
    public String getName() {   //获取用户名
        return name;
    }
    public boolean isMine() {
        return isMine;
    }
    public void setIsMine(boolean b) {  //设置地雷
        isMine=b;
    }
    public void setMineIcon(ImageIcon icon){
        mineIcon=icon;
    }
    public ImageIcon getMineicon(){
        return mineIcon;
    }
    public boolean getIsOpen() {
        return isOpen;
    }
    public void setIsOpen(boolean p) {
        isOpen=p;
    }
    public boolean getIsMark() {
        return isMark;
    }
    public void setIsMark(boolean m) {
        isMark=m;
    }
}

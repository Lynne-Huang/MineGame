import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Lynne
 * @date 2018/9/22 - 23:21
 **/
public class JBlockPanel extends JPanel{
    public JBlockPanel(){
        Border bevelBorder = BorderFactory
                .createBevelBorder(BevelBorder.LOWERED);

        //this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5,
                2, 5), bevelBorder));
        this.setPreferredSize(new Dimension(14, 14));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalStrut(5));// 填充不了哦。

    }
}

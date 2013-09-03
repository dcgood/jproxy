/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jproxy;

import com.mycompany.jproxy.netty.SocksServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author fenglei
 */
public class JProxyOptPanel extends JPanel
        implements ActionListener {

    protected JButton bl, bc, br;
    private SocksServer socksServer = null;

    public JProxyOptPanel() {

        ImageIcon leftButtonIcon = createImageIcon("./images/start.png");
        ImageIcon centerButtonIcon = createImageIcon("./images/stop.png");
        ImageIcon rightButtonIcon = createImageIcon("./images/config.png");
        //创建及设计左边的按钮
        bl = new JButton("Start", leftButtonIcon);
//        bl.setVerticalTextPosition(AbstractButton.CENTER); //设置文本相对于图标的垂直位置。
//        bl.setHorizontalTextPosition(AbstractButton.LEADING); //设置文本相对于图标的水平位置。leading 文字在图片左边 标识使用从左到右和从右到左的语言的文本开始边 
        bl.setActionCommand("start"); //设置此按钮的动作命令。 后面会取到此值
        bl.setMnemonic(KeyEvent.VK_L); //按钮快捷键 并且应该使用 java.awt.event.KeyEvent 中定义的 VK_XXX 键代码之一指定。助记符是不区分大小写的

        //创建及设计中间的按钮
        bc = new JButton("Stop", centerButtonIcon);
//        bc.setVerticalTextPosition(AbstractButton.BOTTOM);
//        bc.setHorizontalTextPosition(AbstractButton.CENTER);
        bc.setMnemonic(KeyEvent.VK_C); //快捷键是c
        bc.setActionCommand("stop");
//        br.setEnabled(false);

        //创建及设计右边的按钮
        br = new JButton("OK", rightButtonIcon);
        /*br.setVerticalTextPosition(AbstractButton.CENTER);
         br.setHorizontalTextPosition(AbstractButton.TRAILING);默认布局方式*/
        br.setMnemonic(KeyEvent.VK_R); //快捷键是r
        br.setActionCommand("config");
//        br.setEnabled(false);

        //监听bl和br按钮
        bl.addActionListener(this);
        bc.addActionListener(this);
        br.addActionListener(this);

        bl.setToolTipText("Start");
        bc.setToolTipText("Stop");
        br.setToolTipText("OK");

        JTextField jtf = new JTextField("Port", 5);
        jtf.setEditable(false);
        JTextPane jtp = new JTextPane();
        jtp.setText("8888");
        //Add Components to this container, using the default FlowLayout.
        add(bl);
        add(bc);
        add(jtf);
        add(jtp);
        add(br);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String targetActionCommand = e.getActionCommand();
        if ("start".equals(targetActionCommand)) {
            bl.setEnabled(false);
            bc.setEnabled(true);
            Thread startThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //判断是否已经运行
                    try {
                        socksServer = new SocksServer(8888);
                        socksServer.run();
                    } catch (Exception ex) {
                        Logger.getLogger(JProxyOptPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            startThread.start();
            //            br.setEnabled(true);
        } else if ("stop".equals(targetActionCommand)) {
            bl.setEnabled(true);
            bc.setEnabled(false);
            Thread stopThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (socksServer != null) {
                        try {
                            socksServer.stop();
                        } catch (Exception ex) {
                            Logger.getLogger(JProxyOptPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            stopThread.start();
//            br.setEnabled(false);
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
//        java.net.URL imgURL = JProxyOptPanel.class.getResource(path);
//        if (imgURL != null) {
//            System.out.println(imgURL + "____" + path);
//            return new ImageIcon(imgURL);
//        } else {
//            System.err.println("couldn't find file：" + path);
//            return null;
//        }
        ImageIcon icon = new ImageIcon(path);
        return icon;
    }
}

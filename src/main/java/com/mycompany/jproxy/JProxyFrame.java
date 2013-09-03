/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jproxy;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author fenglei
 */
public class JProxyFrame extends JFrame implements ActionListener {
    
    private static JProxyFrame jProxyFrame = null;
    private static TrayIcon trayIcon; // tray icon
    private static SystemTray tray; // tray instance

    /**
     *
     * @return
     */
    public static synchronized JProxyFrame getInstance() {
        if (jProxyFrame == null) {
            jProxyFrame = new JProxyFrame("JProxy");
        }
        JProxyFrame.setLocation();
        jProxyFrame.setSize(240, 180);
        jProxyFrame.setVisible(true);
        jProxyFrame.setResizable(false);
        JProxyFrame.initTray();
        JProxyFrame.initComponent();
        JProxyFrame.addEvent();
        return jProxyFrame;
    }
    
    private static void initComponent() {
//        jProxyFrame.add(jProxyFrame);
        JProxyOptPanel jProxyOptPanel = new JProxyOptPanel();
        jProxyOptPanel.setOpaque(true);
        jProxyOptPanel.setBorder(BorderFactory.createTitledBorder("Option"));
        jProxyFrame.setContentPane(jProxyOptPanel);
    }
    
    private JProxyFrame(String title) {
        super(title);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     *
     */
    private static void setLocation() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dm = toolkit.getScreenSize();
        int width = ((Double) dm.getWidth()).intValue();
        int height = ((Double) dm.getHeight()).intValue();
        
        int left = (width - jProxyFrame.getSize().width) / 2;
        int top = (height - jProxyFrame.getSize().height) / 2;
        jProxyFrame.setLocation(left, top);
    }
    
    private static void initTray() {
        tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例  
        ImageIcon icon = new ImageIcon("./images/tray.png"); // 将要显示到托盘中的图标  

        PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单  
        final MenuItem show = new MenuItem("Option");
        final MenuItem exit = new MenuItem("Close");
//        final MenuItem start = new MenuItem("start");
//        final MenuItem stop = new MenuItem("stop");
        pop.add(show);
//        pop.add(start);
//        pop.add(stop);
        pop.add(exit);
        trayIcon = new TrayIcon(icon.getImage(), "JProxy", pop);//实例化托盘图标  
        trayIcon.setImageAutoSize(true);
        //为托盘图标监听点击事件  
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)//鼠标双击图标
                {
                    jProxyFrame.setExtendedState(JFrame.NORMAL);//设置状态为正常  
                    jProxyFrame.setVisible(true);//显示主窗体  
                }
            }
        });

        //选项注册事件  
        ActionListener al2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //退出程序  
                if (e.getSource() == exit) {
                    System.exit(0);//退出程序  
                }
                //打开程序  
                if (e.getSource() == show) {
                    jProxyFrame.setExtendedState(JFrame.NORMAL);//设置状态为正常  
                    jProxyFrame.setVisible(true);
                }
            }
        };
        exit.addActionListener(al2);
        show.addActionListener(al2);
        
        try {
            tray.add(trayIcon);  
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void addEvent() {
        //为主窗体注册窗体事件  
        jProxyFrame.addWindowListener(new WindowAdapter() {
            //窗体最小化事件  
            public void windowIconified(WindowEvent e) {
                jProxyFrame.setVisible(false);//使窗口不可视  
                jProxyFrame.dispose();//释放当前窗体资源  
            }
        });
    }
}

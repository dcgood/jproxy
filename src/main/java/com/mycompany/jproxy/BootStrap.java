/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jproxy;

import javax.swing.SwingUtilities;

/**
 *
 * @author fenglei
 */
public class BootStrap {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JProxyFrame.getInstance();
            }
        });
    }
}

package utils;

import javax.swing.*;
import java.awt.*;

public class LayoutTools {

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * 添加组件到GridBag容器中
     *
     * @param p       panel
     * @param c       component
     * @param x       横向格子，从0开始
     * @param y       纵向格子，从0开始
     * @param width   横向占用格子数
     * @param height  纵向占用格子数
     * @param weightx 横向权重 0～1
     * @param weighty 纵向权重 0～1
     */
    public static void addItem(JPanel p, Component c, int x, int y, int width, int height, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        p.add(c, gbc);
    }

    public static void addItem(JPanel p, Component c, int x, int y, int width, int height, double weightx, double weighty, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.insets = insets;
        p.add(c, gbc);
    }

    public static void addItem(JPanel p, Component c, int x, int y, int width, int height, double weightx, double weighty, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = fill;
        p.add(c, gbc);
    }

    public static int getWindowX(int width) {

        return (int) (screenSize.getWidth() - width) / 2;
    }

    public static int getWindowY(int height) {
        return (int) (screenSize.getHeight() - height) / 2;
    }

    public static void setWindowCenter(JFrame frame) {
        frame.setLocation(getWindowX(frame.getWidth()), getWindowY(frame.getHeight()));
    }

    public static void setWindowCenter(JDialog dialog) {
        dialog.setLocation(getWindowX(dialog.getWidth()), getWindowY(dialog.getHeight()));
    }
}

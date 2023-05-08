package widgets.main;

import interfaces.Controller;
import widgets.userList.UserListWidget;

import javax.swing.*;
import java.awt.*;

public class MainWidget extends JFrame {
    final private Controller controller;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JPanel mainPanel;
    private JPanel listPanel;
    private JPanel chatPanel;
    private JList<String> chatList;

    public MainWidget(Controller c) {
        super("Chat");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        this.controller = c;

        // 布局
        this.layout = new GridBagLayout();
        this.constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        // 主Panel，border用于设置最外层边界
        this.mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        this.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setContentPane(this.mainPanel);

        // 好友列表
        this.listPanel = new UserListWidget();
        addItem(mainPanel, listPanel, 0, 0, 1, 1, 0.2, 0);

//        JTextArea text = new JTextArea("好友列表");
//        this.listPanel.add(text);
        this.chatPanel = new JPanel();
        JTextArea text2 = new JTextArea("聊天框");
        this.chatPanel.add(text2);
        addItem(mainPanel, chatPanel, 1, 0, 4, 1, 0.8, 0);
    }


    public void showWidget() {
//        this.pack();
        this.setVisible(true);
    }

    /**
     *
     * @param p
     * @param c
     * @param x 横向格子，从0开始
     * @param y 纵向格子，从0开始
     * @param width 横向占用格子数
     * @param height 纵向占用格子数
     * @param weightx 横向权重 0～1
     * @param weighty 纵向权重 0～1
     */
    private static void addItem(JPanel p, Component c, int x, int y, int width, int height, double weightx, double weighty) {
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
}

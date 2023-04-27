package widgets.main;

import clientEnum.Event;
import interfaces.Controller;
import widgets.chat.ChatWidget;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainWidget {
    private JPanel mainPanel;
    private JList userList;
    private JButton button1;
    private JPanel chatPanel;
    private JFrame frame;
    private Controller controller;

    public MainWidget(Controller controller) {
        this.controller = controller;
    }

    public void showWidget() {
        this.frame = new JFrame("MainWidget");
//        frame.setSize(new Dimension(800,800));
        frame.setMinimumSize(new Dimension(1000, 800));
//        userList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                if(getSelectName()!=null && !getSelectName().equals("")) {
//                    controller.handleEvent(Event.OPEN_CHAT_WIDGET);
//                }
//            }
//        });
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                if (e.getClickCount() >= 2 && getSelectName() != null && !getSelectName().equals("")) {
                    controller.handleEvent(Event.OPEN_CHAT_WIDGET);
//                    this.chatPanel.add(new ChatWidget(this.controller,"123").getMainPanel());
                }
            }
        });

        // mock data
        var listTest = new ArrayList<String>();
        listTest.add("angrychow");
        listTest.add("octopus");
        this.userList.setListData(listTest.toArray());
        this.frame.setContentPane(this.mainPanel);
//        this.chatPanel.add(new ChatWidget(this.controller,"123").getMainPanel());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
//        this.frame.setDefaultCloseOperation();
        this.frame.setVisible(true);

    }

    public String getSelectName() {
        if (this.userList.getSelectedValue() instanceof String) {
            return (String) this.userList.getSelectedValue();
        } else {
            return null;
        }
    }

    public void setChatPanel(String name) {
        this.chatPanel.removeAll();
        this.chatPanel.add(new ChatWidget(this.controller, name).getMainPanel());
        this.frame.pack();

    }

    public void hideWidget() {
        this.frame.setVisible(false);
    }

}

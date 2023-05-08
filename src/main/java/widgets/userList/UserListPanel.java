package widgets.userList;

import clientEnum.Event;
import interfaces.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

public class UserListPanel extends JPanel {
    private Controller controller;
    // 用户列表
    private JList userList;

    // 纵向滚动
    private JScrollPane scrollPane;


    public UserListPanel(Controller c) {
        this.controller = c;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        this.userList = new JList();

        // 控制列表渲染格式
        this.userList.setCellRenderer(new UserListRenderer());
        this.userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                controller.handleEvent(Event.OPEN_CHAT_WIDGET);
            }
        });

        // mock数据
        var mock = new ArrayList<String>();
        mock.add("123");
        mock.add("octo");

        this.userList.setListData(mock.toArray());

        this.scrollPane = new JScrollPane(userList);
        this.add(scrollPane);
    }

    public void showWidget() {
        this.setVisible(true);
    }


    public String getSelectedUser() {
        return (String) this.userList.getSelectedValue();
    }
}

class UserListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }
}
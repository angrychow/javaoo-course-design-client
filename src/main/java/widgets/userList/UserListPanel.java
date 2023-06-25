package widgets.userList;

import clientEnum.Event;
import entity.User;
import interfaces.Controller;
import utils.Bus;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class UserListPanel extends JPanel {
    private Controller controller;
    // 用户列表
    private JList userList;

    // 纵向滚动
    private JScrollPane scrollPane;
    private ArrayList<String> userListData;

    public UserListPanel(Controller c) {

        this.controller = c;


        this.setLayout(new BorderLayout());
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        this.userList = new JList();
//        this.userList.setSize(300,400);

        // 控制列表渲染格式
        this.userList.setCellRenderer(new UserListRenderer());
        this.userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                controller.handleEvent(Event.OPEN_CHAT_WIDGET);
            }
        });

        // List 数据 Convert
        this.userListData = new ArrayList<String>();
        for(User e:Bus.friendList) {
            userListData.add(e.name);
        }
        System.out.println(userListData);

        this.userList.setListData(userListData.toArray());

        this.scrollPane = new JScrollPane(userList);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);
        this.userList.addListSelectionListener((e)->{
            UserListPanel.this.controller.handleEvent(Event.USERLIST_CHANGE);
        });
//        this.userList.setSelectedValue();
    }

    public void showWidget() {
        this.setVisible(true);
    }


    public String getSelectedUser() {
        return (String) this.userList.getSelectedValue();
    }
    public int getSelectedUserId() {
        AtomicInteger idReturn = new AtomicInteger();
        for(User e:Bus.friendList) {
            if(e.name.equals(UserListPanel.this.getSelectedUser())) {
                idReturn.set(e.ID);
            }
        }
        return idReturn.get();
    }
    public void setSelectedById(int id) {


        var temp = this.userListData.toArray();
        String name = null;
        int idx = 0;
        for(User e:Bus.friendList) {
            if(e.ID == id) {
                name = e.name;
            }
        }
        System.out.println(userListData);
        System.out.println(userListData.toArray().length);
        for(int i=0;i<temp.length;i++) {
            System.out.println(i+" "+temp[i]);
            if(temp[i].equals(name)) {
                idx = i;
            }
        }
        this.userList.setSelectedIndex(idx);
    }

    public void updateUserList() {
        var idx = this.userList.getSelectedIndex();
        this.userListData = new ArrayList<String>();
        for(User e:Bus.friendList) {
            userListData.add(e.name);
        }
        this.userList.setListData(userListData.toArray());
        System.out.println(userListData);
//        this.scrollPane = new JScrollPane(userList);
//        this.add(scrollPane,BorderLayout.CENTER);
//        this.userList.updateUI();
        this.userList.setSelectedIndex(idx);
    }
}

class UserListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//        label.setHorizontalAlignment(SwingConstants.CENTER);
        setHorizontalAlignment(LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }
}
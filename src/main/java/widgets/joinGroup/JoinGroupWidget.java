package widgets.joinGroup;

import clientEnum.Event;
import interfaces.Controller;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

import static utils.LayoutTools.setWindowCenter;

public class JoinGroupWidget {
    private JList groupList;
    private JPanel joinGroupMainPanel;
    private JTextField nameTextField;
    private JButton joinButton;
    private JButton upgradeButton;
    private JButton exitButton;
    private JFrame frame;
    private Controller controller;

    public JoinGroupWidget(Controller c) {
        controller = c;
        frame = new JFrame("群聊管理");
        frame.setContentPane(joinGroupMainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        var mock = new ArrayList<String>();
        for(var e:Bus.friendList) {
            if(e.ID>=10000) {
                mock.add(e.name);
            }
        }
        this.groupList.setListData(mock.toArray());

        joinButton.addActionListener((e)-> {
            HashMap<String, Object> params = new HashMap<>();
            var dialog = new JDialog();
            dialog.setSize(200, 100);
            setWindowCenter(dialog);
            dialog.setModal(false);
            params.put("from", Bus.Uid);
            var name = nameTextField.getText();
            var toUid = -1;
            var groupName = groupList.getSelectedValue();
            var groupId = -1;
            for (var m : Bus.friendList) {
                if (m.name.equals(name)) {
                    toUid = m.ID;
                }
                if(m.name.equals(groupName)) {
                    groupId = m.ID;
                }
            }

            if(toUid == -1 || groupId == -1) {
                dialog.add(new JLabel("您没有这个好友或者你没有选中群聊"));
//                dialog.pack();
                dialog.setVisible(true);
            } else {
                params.put("invite",toUid);
                params.put("group",groupId);
                var result = ClientHttp.Post(BaseUrl.GetUrl("/group/invite"),null,params);
                System.out.println(result);
                if(result.get("statusCode").equals(200)) {
                    dialog.add(new JLabel("添加成功"));
//                    dialog.pack();
                    dialog.setVisible(true);
                    controller.handleEvent(Event.UPDATE_FRIEND_LIST);
                } else {
                    dialog.add(new JLabel("添加失败"));
//                    dialog.pack();
                    dialog.setVisible(true);
                }
            }

        });

        upgradeButton.addActionListener((e)-> {
            HashMap<String, Object> params = new HashMap<>();
            var dialog = new JDialog();
            dialog.setSize(200, 100);
            setWindowCenter(dialog);
            dialog.setModal(false);
            params.put("owner", Bus.Uid);
            var groupName = groupList.getSelectedValue();
            var groupId = -1;
            for (var m : Bus.friendList) {
                if (m.name.equals(groupName)) {
                    groupId = m.ID;
                }
            }

            if(groupId == -1) {
                dialog.add(new JLabel("你没有选中群聊"));
//                dialog.pack();
                dialog.setVisible(true);
            } else {
                params.put("groupId",groupId);
                var result = ClientHttp.Post(BaseUrl.GetUrl("/group/upgrade"),null,params);
                System.out.println(result);
                if(result.get("statusCode").equals(200)) {
                    dialog.add(new JLabel("升级成功"));
//                    dialog.pack();
                    dialog.setVisible(true);
                    controller.handleEvent(Event.UPDATE_FRIEND_LIST);
                } else {
                    dialog.add(new JLabel("升级失败，因为你不是群主"));
//                    dialog.pack();
                    dialog.setVisible(true);
                }
            }
        });


        exitButton.addActionListener((e)-> {
            HashMap<String, Object> params = new HashMap<>();
            var dialog = new JDialog();
            dialog.setSize(200, 100);
            setWindowCenter(dialog);
            dialog.setModal(false);
            params.put("user", Bus.Uid);
            var groupName = groupList.getSelectedValue();
            var groupId = -1;
            for (var m : Bus.friendList) {
                if (m.name.equals(groupName)) {
                    groupId = m.ID;
                }
            }

            if(groupId == -1) {
                dialog.add(new JLabel("你没有选中群聊"));
//                dialog.pack();
                dialog.setVisible(true);
            } else {
                params.put("group",groupId);
                var result = ClientHttp.Post(BaseUrl.GetUrl("/group/exit"),null,params);
                System.out.println(result);
                if(result.get("statusCode").equals(200)) {
                    dialog.add(new JLabel("离开成功"));
//                    dialog.pack();
                    dialog.setVisible(true);
                    controller.handleEvent(Event.UPDATE_FRIEND_LIST);
                } else {
                    dialog.add(new JLabel("离开失败"));
//                    dialog.pack();
                    dialog.setVisible(true);
                }
            }
        });


    }

    public void showWidget() {
        var mock = new ArrayList<String>();
        for (var e : Bus.friendList) {
            if (e.ID >= 10000) {
                mock.add(e.name);
            }
        }
        this.groupList.setListData(mock.toArray());
        this.joinGroupMainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        frame.pack();
        setWindowCenter(frame);

        frame.setVisible(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

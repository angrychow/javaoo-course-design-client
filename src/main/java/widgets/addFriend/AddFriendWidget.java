package widgets.addFriend;

import clientEnum.Event;
import entity.User;
import interfaces.Controller;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static utils.LayoutTools.setWindowCenter;

public class AddFriendWidget {
    private JPanel panel1;
    private JTextField idTextField;
    private JButton addButton;
    private JList userList;
    private JScrollPane scroll;
    private JFrame frame;
    private Controller controller;
    private ArrayList<User> users;

    public AddFriendWidget(Controller c) {
        controller = c;
        frame = new JFrame("AddFriend");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addButton.addActionListener((e -> {
            var params = new HashMap<String,Object>();
            params.put("from", Bus.Uid);
            params.put("to",Integer.parseInt(idTextField.getText()));
            var resp = ClientHttp.Post(BaseUrl.GetUrl("/relation/add"),null,params);
            System.out.println(resp);
            resp = (HashMap<String, Object>) resp.get("body");
            if((int)resp.get("statusCode") == 200) {
                var dialog = new JDialog();
                dialog.add(new JLabel("添加成功"));
                dialog.setSize(200, 100);
                setWindowCenter(dialog);
//                dialog.pack();
                dialog.setVisible(true);
            }
        }));
    }

    public void update() {
        users = new ArrayList<>();
        var result = ClientHttp.Get(BaseUrl.GetUrl("/user/loadAll"),null);
        var raws = (ArrayList<HashMap<String,Object>>)((HashMap<String, Object>)result.get("body")).get("data");
        for(var raw : raws) {
            users.add(new User((String) raw.get("name"),(int)raw.get("id")));
        }
        userList.setListData(users.toArray());
    }

    public void showWidget() {
        update();
//        frame.pack();
        frame.setSize(new Dimension(400, 300));

        // 设置窗口位置
        setWindowCenter(frame);
        frame.setVisible(true);
    }

}

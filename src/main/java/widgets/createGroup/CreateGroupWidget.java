package widgets.createGroup;

import interfaces.Controller;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;

import javax.swing.*;
import java.util.HashMap;

public class CreateGroupWidget {
    private JTextField gourpNameTextField;
    private JComboBox groupTypeComboBox;
    private JButton createButton;
    private JPanel mainPanel;
    private JFrame frame;
    private Controller controller;

    public CreateGroupWidget(Controller c) {
        controller = c;
        frame = new JFrame("CreateGroup");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.createButton.addActionListener((e)->{
            var name = gourpNameTextField.getText();
            var params = new HashMap<String,Object>();
            params.put("user", Bus.Uid);
            params.put("name", name);
            var resp = ClientHttp.Post(BaseUrl.GetUrl("/group/create"),null,params);
            resp = (HashMap<String, Object>) resp.get("body");
            var params2 = new HashMap<String,Object>();
            var uid = 1;
            for(var ele: Bus.friendList) {
                if(ele.name.equals(groupTypeComboBox.getSelectedItem())) {
                    uid = ele.ID;
                }
            }
            params2.put("from",Bus.Uid);
            params2.put("invite",uid);
            params2.put("group",((HashMap<String,Object>)(resp.get("data"))).get("id"));
            var resp2 = ClientHttp.Post(BaseUrl.GetUrl("/group/invite"),null,params2);
            if(resp!=null && (int)resp.get("statusCode") == 200 && resp2!=null && resp2!=null) {
                System.out.println(resp);
                var dialog = new JDialog();
                dialog.add(new JLabel("创建成功"));
                dialog.pack();
                dialog.setModal(false);
                dialog.setVisible(true);
            }
        });
    }

    public void showWidget() {
        for(var e: Bus.friendList) {
            if(e.ID<10000)groupTypeComboBox.addItem(e.name);
        }
        frame.pack();
        frame.setVisible(true);
    }
}

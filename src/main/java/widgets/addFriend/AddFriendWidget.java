package widgets.addFriend;

import clientEnum.Event;
import entity.User;
import interfaces.Controller;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;

import javax.swing.*;
import java.util.HashMap;

public class AddFriendWidget {
    private JPanel panel1;
    private JTextField idTextField;
    private JButton addButton;
    private JFrame frame;
    private Controller controller;

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
                dialog.pack();
                dialog.setVisible(true);
            }
        }));
    }

    public void showWidget() {
        frame.pack();
        frame.setVisible(true);
    }

}

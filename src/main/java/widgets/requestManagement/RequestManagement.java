package widgets.requestManagement;

import clientEnum.Event;
import entity.User;
import interfaces.Controller;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestManagement {
    private JPanel panel1;
    private JComboBox requestComboBox;
    private JButton agreeButton;
    private JButton rejectButton;
    private JFrame frame;
    private Controller controller;
    private ArrayList<HashMap<String,Object>> requestMap;

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("RequestManagement");
//        frame.setContentPane(new RequestManagement().panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }
    public RequestManagement(Controller c) {


        this.controller = c;
        this.frame = new JFrame("RequestManagement");
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        agreeButton.addActionListener((e)->{
            int id = -1;
            String name = "";
            for(var ele:requestMap) {
                if(((String)(ele.get("name"))).equals(requestComboBox.getSelectedItem())) {
                    id = (int)(ele.get("id"));
                    name = (String)(ele.get("name"));
                }
            }
            var params = new HashMap<String,Object>();
            params.put("from",id);
            params.put("to",Bus.Uid);
            System.out.println(params);
            var resp = ClientHttp.Post(BaseUrl.GetUrl("/relation/access"),null,params);
            System.out.println(resp);
            Bus.friendList.add(new User(name,id));
            controller.handleEvent(Event.UPDATE_FRIEND_LIST);
            var dialog = new JDialog();
            dialog.add(new JLabel("成功添加好友"));
            dialog.pack();
            dialog.setVisible(true);
            RequestManagement.this.UpdateRequest();
        });
        rejectButton.addActionListener((e)->{
            int id = -1;
            for(var ele:requestMap) {
                if(((String)(ele.get("name"))).equals(requestComboBox.getSelectedItem())) {
                    id = (int)ele.get("id");
                }
            }
            var params = new HashMap<String,Object>();
            params.put("from",Bus.Uid);
            params.put("to",id);
            ClientHttp.Post(BaseUrl.GetUrl("/relation/deny"),null,params);
            var dialog = new JDialog();
            dialog.add(new JLabel("成功拒绝添加好友"));
            dialog.pack();
            dialog.setVisible(true);
            RequestManagement.this.UpdateRequest();
        });
    }

    public void ShowWidget() {
        UpdateRequest();
        frame.setVisible(true);
    }

    public void UpdateRequest() {
        var params = new HashMap<String,Object>();
        params.put("user", Bus.Uid);
        var request = ClientHttp.Post(BaseUrl.GetUrl("/relation/request"),null,params);
        request = (HashMap<String, Object>) request.get("body");
        this.requestMap = (ArrayList<HashMap<String,Object>>)request.get("data");
        System.out.println(request.toString());
//        requestComboBox.removeAll();
        requestComboBox.removeAllItems();
        for(var e:requestMap) {
            requestComboBox.addItem((String)e.get("name"));
        }
//        requestComboBox.updateUI();

    }
}

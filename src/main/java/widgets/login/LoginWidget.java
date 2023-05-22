package widgets.login;

import clientEnum.Event;
import entity.User;
import interfaces.Controller;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginWidget {
    private JButton buttonLogin;
    private JTextField accountField;
    private JTextField pwdField;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel mainPanel;
    private JButton buttonRegister;
    private JFrame frame;
    private Controller controller;

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("LoginWidget");
//        frame.setContentPane(new LoginWidget().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }

    public LoginWidget(Controller controller) {
        this.controller = controller;
    }

    public void showWidget() {

        buttonLogin.addActionListener((e)->{
            var dialog = new JDialog();
            dialog.setModal(false);
            dialog.setTitle("Error!");
//            dialog.setSize(300,200);
            System.out.println(LoginWidget.this.accountField.getText());
            System.out.println(LoginWidget.this.pwdField.getText());
            var params = new HashMap<String,Object>();
            params.put("name",LoginWidget.this.accountField.getText());
            params.put("password",LoginWidget.this.pwdField.getText());
            var result = ClientHttp.Post(BaseUrl.GetUrl("/user/login"),null,params);
            if(result.get("statusCode").equals(200)) {
                var resultBody = (HashMap<String,Object>)result.get("body");
                if(resultBody.get("statusCode").equals(200)) {
//                    Bus.EmbedToken = (String) resultBody.get("data");
                    var resultBodyData = (HashMap<String,Object>)resultBody.get("data");
                    Bus.EmbedToken = (String)resultBodyData.get("token");
                    Bus.Uid = (int)resultBodyData.get("uid");
//                    System.out.println(Bus.Uid);
                    Bus.UserName = LoginWidget.this.accountField.getText();
                    var friendsParams = new HashMap<String,Object>();
                    friendsParams.put("user",Bus.Uid);
                    var friendsResult = ClientHttp.Post(BaseUrl.GetUrl("/relation/friends"),null,friendsParams);
                    if(friendsResult.get("statusCode").equals(200)) {
                        var friendsRawList = (ArrayList<HashMap<String,Object>>)((HashMap<String,Object>)friendsResult.get("body")).get("data");
//                        System.out.println(friendsRawList);
                        friendsRawList.stream().forEach((item)->{
                            var ret = new User((String)item.get("name"),(int)item.get("id"));
//                            ret.name = (String)item.get("name");
//                            ret.
//                            return ret;
                            Bus.friendList.add(ret);
                        });
                        System.out.println(Bus.friendList);
                    }
                    LoginWidget.this.controller.handleEvent(Event.LOGIN_SUCCESS);
                } else {
                    dialog.add(new JLabel("错误："+resultBody.get("msg")));
                    dialog.pack();
                    dialog.setVisible(true);
                }
            } else {
                dialog.add(new JLabel("错误：网络错误"));
                dialog.pack();
                dialog.setVisible(true);
            }
            System.out.println(result.toString());
        });
        buttonRegister.addActionListener((e)->{
            var dialog = new JDialog();
            dialog.setModal(false);
            dialog.setTitle("Error!");
//            dialog.setSize(300,200);
            System.out.println(LoginWidget.this.accountField.getText());
            System.out.println(LoginWidget.this.pwdField.getText());
            var params = new HashMap<String,Object>();
            params.put("name",LoginWidget.this.accountField.getText());
            params.put("password",LoginWidget.this.pwdField.getText());
            var result = ClientHttp.Post(BaseUrl.GetUrl("/user/register"),null,params);
            if(result.get("statusCode").equals(200)) {
                var resultBody = (HashMap<String,Object>)result.get("body");
                if(resultBody.get("statusCode").equals(200)) {
                    // LoginWidget.this.controller.handleEvent(Event.LOGIN_SUCCESS);
                    dialog.setTitle("Success!");
                    dialog.add(new JLabel("注册成功！！"));
                    dialog.pack();
                    dialog.setVisible(true);
                } else {
                    dialog.add(new JLabel("错误："+resultBody.get("data")));
                    dialog.pack();
                    dialog.setVisible(true);
                }
            } else {
                dialog.add(new JLabel("错误：网络错误"));
                dialog.pack();
                dialog.setVisible(true);
            }
            System.out.println(result.toString());
        });
        this.frame = new JFrame("LoginWidget");

        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void hideWidget() {
        this.frame.setVisible(false);
    }
}


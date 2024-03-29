package widgets.login;

import clientEnum.Event;
import entity.User;
import interfaces.Controller;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;
import utils.LayoutTools;
import widgets.face.FaceWidget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import static utils.LayoutTools.setWindowCenter;

public class LoginWidget {
    private JButton buttonLogin;
    private JTextField accountField;
    private JTextField pwdField;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel mainPanel;
    private JButton buttonRegister;
    private JButton faceLoginButton;
    private JButton faceRegisterButton;
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

        faceLoginButton.addActionListener((e)->{
            try {
                var faceLogin = new FaceWidget(this.accountField.getText(),0,controller);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        faceRegisterButton.addActionListener((e)->{
            try {
                var faceLogin = new FaceWidget(this.accountField.getText(),1,controller);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        buttonLogin.addActionListener((e)-> {
            var dialog = new JDialog();
            dialog.setModal(false);
            dialog.setTitle("Hint!");
            dialog.setSize(200, 100);
            setWindowCenter(dialog);

            System.out.println(LoginWidget.this.accountField.getText());
            System.out.println(LoginWidget.this.pwdField.getText());
            var params = new HashMap<String, Object>();
            params.put("name", LoginWidget.this.accountField.getText());
            params.put("password", LoginWidget.this.pwdField.getText());
            var result = ClientHttp.Post(BaseUrl.GetUrl("/user/login"), null, params);
            if (result.get("statusCode").equals(200)) {
                var resultBody = (HashMap<String, Object>) result.get("body");
                if (resultBody.get("statusCode").equals(200)) {
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
                    var groupsResult = ClientHttp.Post(BaseUrl.GetUrl("/group/groups"),null,friendsParams);

                    if(friendsResult.get("statusCode").equals(200)) {
                        var groupsRawList = (ArrayList<HashMap<String,Object>>)((HashMap<String,Object>)groupsResult.get("body")).get("data");
                        groupsRawList.stream().forEach((item)-> {
                            var isGroupCreator = "等级" + item.get("level") + " ";
                            if ((int) item.get("owner") == Bus.Uid) {
                                isGroupCreator = "(群主)" + isGroupCreator;
                            } else {
                                isGroupCreator = "(群员)" + isGroupCreator;
                            }
                            var ret = new User(isGroupCreator + item.get("groupName"), (int) item.get("id"));
                            Bus.friendList.add(ret);
                        });
                        System.out.println(Bus.friendList);
                    }

                    dialog.add(new JLabel("登陆成功，您的 UID 是："+Bus.Uid));
//                    dialog.pack();
                    dialog.setVisible(true);
                    LoginWidget.this.controller.handleEvent(Event.LOGIN_SUCCESS);
                } else {
                    dialog.add(new JLabel("错误："+resultBody.get("msg")));
//                    dialog.pack();
                    dialog.setVisible(true);
                }
            } else {
                dialog.add(new JLabel("错误：网络错误"));
//                dialog.pack();
                dialog.setVisible(true);
            }
            System.out.println(result.toString());
        });
        buttonRegister.addActionListener((e)-> {
            var dialog = new JDialog();
            dialog.setModal(false);
            dialog.setTitle("Hint!");
            dialog.setSize(200, 100);
            setWindowCenter(dialog);
            System.out.println(LoginWidget.this.accountField.getText());
            System.out.println(LoginWidget.this.pwdField.getText());
            var params = new HashMap<String, Object>();
            params.put("name", LoginWidget.this.accountField.getText());
            params.put("password", LoginWidget.this.pwdField.getText());
            var result = ClientHttp.Post(BaseUrl.GetUrl("/user/register"), null, params);
            if (result.get("statusCode").equals(200)) {
                var resultBody = (HashMap<String, Object>) result.get("body");
                if (resultBody.get("statusCode").equals(200)) {
                    // LoginWidget.this.controller.handleEvent(Event.LOGIN_SUCCESS);
                    dialog.setTitle("Success!");
                    dialog.add(new JLabel("注册成功！！"));
                    dialog.setLocation(LayoutTools.getWindowX(dialog.getWidth()), LayoutTools.getWindowY(dialog.getHeight()));
                    dialog.setVisible(true);
                } else {
                    dialog.add(new JLabel("错误："+resultBody.get("data")));
//                    dialog.pack();
                    dialog.setVisible(true);
                }
            } else {
                dialog.add(new JLabel("错误：网络错误"));
//                dialog.pack();
                dialog.setVisible(true);
            }
            System.out.println(result.toString());
        });
        this.frame = new JFrame("LoginWidget");


        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setSize(new Dimension(400, 300));

        // 设置窗口位置
        setWindowCenter(frame);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        frame.setLocation(LayoutTools.getWindowX(frame.getWidth()), LayoutTools.getWindowY(frame.getHeight()));
        frame.setVisible(true);
    }

    public void hideWidget() {
        this.frame.setVisible(false);
    }
}


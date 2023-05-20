package widgets.login;

import clientEnum.Event;
import interfaces.Controller;
import utils.BaseUrl;
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
                    // TODO: 添加token
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
            var result = ClientHttp.Post(BaseUrl.GetUrl("/user/login"),null,params);
            if(result.get("statusCode").equals(200)) {
                var resultBody = (HashMap<String,Object>)result.get("body");
                if(resultBody.get("statusCode").equals(200)) {
                    // TODO: 添加token
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
        this.frame = new JFrame("LoginWidget");
//        var params = new HashMap<String, Object>();
//        var array = new ArrayList<Integer>();
//        array.add(123);
//        array.add(123);
//        params.put("hello", array);
//        var result = ClientHttp.Post("https://119d25bf-546c-42f2-8b79-6e91a29fb770.mock.pstmn.io/post", params);
//        System.out.println(result.toString());
//        System.out.println(result.get("data") instanceof Integer);

        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void hideWidget() {
        this.frame.setVisible(false);
    }
}


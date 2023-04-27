package widgets.login;

import clientEnum.Event;
import interfaces.Controller;
import utils.ClientHttp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginWidget {
    private JButton buttonLogin;
    private JTextField textField1;
    private JTextField textField2;
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

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleEvent(Event.LOGIN_SUCCESS);
            }
        });
        this.frame = new JFrame("LoginWidget");
        var params = new HashMap<String, Object>();
        var array = new ArrayList<Integer>();
        array.add(123);
        array.add(123);
        params.put("hello", array);
        var result = ClientHttp.Post("https://19d25bf-546c-42f2-8b79-6e91a29fb770.mock.pstmn.io/post", params);
        System.out.println(result.toString());
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


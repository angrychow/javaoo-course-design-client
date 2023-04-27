package widgets.login;

import clientEnum.Event;
import interfaces.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void hideWidget() {
        this.frame.setVisible(false);
    }
}


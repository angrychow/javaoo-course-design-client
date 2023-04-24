package widgets.login;

import utils.ClientHttp;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginWidget {
    private JButton button1;
    private JTextField textField1;
    private JButton button2;
    private JTextField textField2;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel mainPanel;

    public static void main(String[] args) {

        var params = new HashMap<String,Object>();
        var array = new ArrayList<Integer>();
        array.add(123);array.add(123);
        params.put("hello",array);
        var result = ClientHttp.Post("https://119d25bf-546c-42f2-8b79-6e91a29fb770.mock.pstmn.io/post", params);
        System.out.println(result.toString());
//        System.out.println(result.get("data") instanceof Integer);

        JFrame frame = new JFrame("LoginWidget");
        frame.setContentPane(new LoginWidget().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}


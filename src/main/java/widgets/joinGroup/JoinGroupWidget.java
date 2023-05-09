package widgets.joinGroup;

import interfaces.Controller;

import javax.swing.*;
import java.util.ArrayList;

public class JoinGroupWidget {
    private JList list1;
    private JPanel joinGroupMainPanel;
    private JTextField textField1;
    private JButton joinButton;
    private JFrame frame;
    private Controller controller;

    public JoinGroupWidget(Controller c) {
        controller = c;
        frame = new JFrame("JoinGroup");
        frame.setContentPane(joinGroupMainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        var mock = new ArrayList<String>();
        mock.add("123");
        mock.add("octo");

        this.list1.setListData(mock.toArray());


    }

    public void showWidget(){
        frame.pack();
        frame.setVisible(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

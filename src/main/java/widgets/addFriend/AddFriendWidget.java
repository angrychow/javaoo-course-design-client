package widgets.addFriend;

import interfaces.Controller;

import javax.swing.*;

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
    }

    public void showWidget() {
        frame.pack();
        frame.setVisible(true);
    }

}

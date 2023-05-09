package widgets.createGroup;

import interfaces.Controller;

import javax.swing.*;

public class CreateGroupWidget {
    private JTextField gourpNameTextField;
    private JComboBox groupTypeComboBox;
    private JButton createButton;
    private JPanel mainPanel;
    private JFrame frame;
    private Controller controller;

    public CreateGroupWidget(Controller c) {
        controller = c;
        frame = new JFrame("CreateGroup");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    }

    public void showWidget() {
        frame.pack();
        frame.setVisible(true);
    }
}

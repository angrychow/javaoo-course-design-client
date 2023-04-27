package widgets.chat;

import interfaces.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatWidget {
    private JPanel mainPanel;
    private JButton buttonSend;
    private JButton buttonClear;
    private JTextArea recordTextArea;
    private JTextArea inputTextArea;
    private JPanel textPanel;
    private JPanel recordPanel;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JFrame frame;
    private Controller controller;
    private String remoteName;

    public ChatWidget(Controller controller,String name) {
        this.controller = controller;
        this.remoteName = name;
    }

    public String getRemoteName() {
        return this.remoteName;
    }

    public void showWidget() {
        this.frame = new JFrame(remoteName);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
//                frame.setVisible(false);
//                System.out.println(frame.getDefaultCloseOperation());
            }
        });
        this.recordTextArea.append("  您现在在与"+remoteName+"聊天，此人很可疑，谨防上当受骗！\n");
        frame.setMinimumSize(new Dimension(400,400));
        frame.setContentPane(this.mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String args[]) {
//        var chatWidget = new ChatWidget();
//        chatWidget.showWidget();
//    }
}

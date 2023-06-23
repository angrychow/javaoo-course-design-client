package widgets.chat;

import clientEnum.Event;
import interfaces.Controller;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;
import utils.LayoutTools;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ChatPanel extends JPanel {
    private JPanel mainPanel;
    private JPanel sendPanel;
    private JTextArea inputTextArea;
    private JScrollPane sendScrollPane;
    private JScrollPane recordScrollPane;
    private JButton sendButton;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JLabel name;
    private JTextArea recordTextArea;
    private Controller controller;

    public ChatPanel(String name, Controller c) {
        this.controller = c;
        this.layout = new GridBagLayout();
        this.constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        this.setLayout(layout);

        this.name = new JLabel(name);
        LayoutTools.addItem(this, this.name, 0, 0, 1, 1, 1, 0.1);

        this.recordTextArea = new JTextArea();
        this.recordTextArea.setLineWrap(true);
        this.recordTextArea.setWrapStyleWord(true);
        this.recordTextArea.setEditable(false);

        this.recordScrollPane = new JScrollPane(this.recordTextArea);
        Insets i = new Insets(10, 0, 10, 0);
        LayoutTools.addItem(this, this.recordScrollPane, 0, 1, 1, 1, 1, 0.5, i);

        this.sendPanel = new JPanel();
        this.sendPanel.setLayout(new GridBagLayout());

        this.inputTextArea = new JTextArea();
        this.inputTextArea.setLineWrap(true);
        this.inputTextArea.setWrapStyleWord(true);

        this.sendScrollPane = new JScrollPane(inputTextArea);
        LayoutTools.addItem(this.sendPanel, sendScrollPane, 0, 0, 1, 1, 0.8, 1, GridBagConstraints.BOTH);

        this.sendButton = new JButton("发送");
        LayoutTools.addItem(this.sendPanel, sendButton, 1, 0, GridBagConstraints.REMAINDER, 1, 0.2, 0);

        this.sendPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        LayoutTools.addItem(this, sendPanel, 0, 2, 1, 1, 1, 0.4);

        this.sendButton.addActionListener((e)->{
            HashMap<String,Object> params = new HashMap<>();
            params.put("from", Bus.Uid);
            params.put("to",controller.getNowChatUid());
            params.put("msg",ChatPanel.this.inputTextArea.getText());
            this.controller.handleEvent(Event.SEND_MESSAGE);
            HashMap<String,Object> resp;
            resp = controller.getNowChatUid() <10000 ?ClientHttp.Post(BaseUrl.GetUrl("/chat/send"),null,params):ClientHttp.Post(BaseUrl.GetUrl("/group/send"),null,params);
            System.out.println(resp.toString());
        });
    }

    public void replaceText(String text) {
        this.recordTextArea.setText(text);
    }

    public void appendText(String text) {
        this.recordTextArea.append(text);
    }

    public String getChatText() {
        return this.inputTextArea.getText();
    }

    public void clearChatText() {
        this.inputTextArea.setText("");
    }

    public void setName(String name) {
        this.name.setText(name);
    }
}

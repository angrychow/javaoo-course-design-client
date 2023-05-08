package widgets.chat;

import utils.LayoutTools;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
    private JPanel mainPanel;
    private JPanel sendPanel;
    private JTextArea inputTextArea;
    private JScrollPane sendScrollPane;
    private JButton sendButton;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JLabel name;
    private JTextArea recordTextArea;

    public ChatPanel(String name) {
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
        this.recordTextArea.setText("test");
        LayoutTools.addItem(this, this.recordTextArea, 0, 1, 1, 1, 1, 0.5);

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
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notice;

/**
 *
 * @author adsd3
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class NoticeView extends JFrame {
    private final JList<String> noticeList;
    private final JTextField textField;
    private final JButton addButton, deleteButton, updateButton;

    public NoticeView() {
        setTitle("공지사항 관리");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        noticeList = new JList<>();
        textField = new JTextField();
        addButton = new JButton("추가");
        deleteButton = new JButton("삭제");
        updateButton = new JButton("수정");

        setLayout(new BorderLayout());
        add(new JScrollPane(noticeList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("공지사항:"));
        panel.add(textField);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        add(panel, BorderLayout.SOUTH);
    }

    public void setNoticeList(List<String> notices) {
        noticeList.setListData(notices.toArray(new String[0]));
    }

    public String getSelectedNotice() {
        return noticeList.getSelectedValue();
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addUpdateButtonListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }
}

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
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class NoticeListView extends JFrame {
    private final DefaultListModel<Notice> listModel;
    private final JList<Notice> noticeList;
    private final JButton addButton, editButton, deleteButton, toggleReadButton;
    private final JTextField searchField;
    private final JComboBox<String> filterCombo;

    public NoticeListView() {
        setTitle("공지사항 관리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        searchField = new JTextField(15);
        filterCombo = new JComboBox<>(new String[]{"전체", "일반", "긴급", "시스템"});
        toggleReadButton = new JButton("읽음/안읽음");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("검색:"));
        topPanel.add(searchField);
        topPanel.add(new JLabel("카테고리:"));
        topPanel.add(filterCombo);
        topPanel.add(toggleReadButton);
        add(topPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        noticeList = new JList<>(listModel);
        noticeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(noticeList);
        add(scrollPane, BorderLayout.CENTER);

        addButton = new JButton("추가");
        editButton = new JButton("수정");
        deleteButton = new JButton("삭제");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setNotices(List<Notice> notices) {
        listModel.clear();
        for (Notice n : notices) listModel.addElement(n);
    }

    public int getSelectedIndex() {
        return noticeList.getSelectedIndex();
    }

    public String getSearchText() {
        return searchField.getText().trim();
    }

    public String getFilterCategory() {
        return filterCombo.getSelectedItem().toString();
    }

    public void addSearchListener(DocumentListener l) {
        searchField.getDocument().addDocumentListener(l);
    }

    public void addFilterListener(ActionListener l) {
        filterCombo.addActionListener(l);
    }

    public void addToggleReadListener(ActionListener l) {
        toggleReadButton.addActionListener(l);
    }

    public void addAddListener(ActionListener l) {
        addButton.addActionListener(l);
    }

    public void addEditListener(ActionListener l) {
        editButton.addActionListener(l);
    }

    public void addDeleteListener(ActionListener l) {
        deleteButton.addActionListener(l);
    }
}

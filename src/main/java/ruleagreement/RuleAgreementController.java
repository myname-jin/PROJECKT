/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ruleagreement;

/**
 *
 * @author adsd3
 */
import management.NextPage;
import ServerClient.LogoutUtil;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class RuleAgreementController {
    private final RuleAgreementModel model;
    private final RuleAgreementView view;

    // ✅ 소켓과 출력 스트림을 생성자에서 받아서 로그아웃 처리에 활용
    public RuleAgreementController(String userId, Socket socket, BufferedWriter out) throws Exception {
        String absolutePath = "src/resources/rules.txt"; // 경로는 필요시 수정
        this.model = new RuleAgreementModel(absolutePath);
        this.view = new RuleAgreementView(model.getRules());

        // ✅ 창 닫을 때 LOGOUT 메시지 전송
        LogoutUtil.attach(view, userId, socket, out);

        view.getNextButton().addActionListener(e -> {
            if (view.allChecked()) {
                JOptionPane.showMessageDialog(view, "모든 규칙에 동의하셨습니다. 다음 단계로 이동합니다.");
                view.dispose();
                new NextPage(userId, socket, null, out).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "모든 규칙에 동의해야 합니다.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });

        view.setVisible(true);
    }
}
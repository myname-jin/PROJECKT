/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rulemanagement;

/**
 *
 * @author adsd3
 */
import javax.swing.*;
import java.io.IOException;

/**
 * 독립 실행용 Main 클래스.
 * 모든 Swing 초기화는 EDT에서 수행됩니다.
 */
public class RuleManagementMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                RuleManagementController controller =
                    new RuleManagementController("src/main/resources/rules.txt");
                controller.showView();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                    "규칙 관리 초기화 실패: " + e.getMessage(),
                    "치명적 오류", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

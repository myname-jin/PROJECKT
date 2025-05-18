/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rulemanagement;



/**
 *
 * @author adsd3
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class RuleManagementControllerTest {

    private RuleManagementController controller;

    @BeforeEach
    public void setUp() throws IOException {
        // 테스트용 규칙 파일 생성
        FileWriter writer = new FileWriter("test_rules.txt");
        writer.write("안전모 착용\n");
        writer.close();

        controller = new RuleManagementController("test_rules.txt");
    }

    private void setPrivateField(String fieldName, Object value) {
        try {
            Field field = RuleManagementController.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(controller, value);
        } catch (Exception e) {
            fail("Failed to set field '" + fieldName + "': " + e.getMessage());
        }
    }

    private Object getPrivateField(String fieldName) {
        try {
            Field field = RuleManagementController.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(controller);
        } catch (Exception e) {
            fail("Failed to get field '" + fieldName + "': " + e.getMessage());
            return null;
        }
    }

    @Test
    public void testAddRuleButtonClick() {
        RuleManagementView view = (RuleManagementView) getPrivateField("view");
        JTextField textField = new JTextField("장갑 착용");
        setPrivateFieldInView(view, "newRuleField", textField);

        JButton addButton = view.getAddButton();
        addButton.doClick(); // 버튼 클릭으로 addRule 수행

        assertTrue(view.getRuleCheckBoxes().stream()
                .anyMatch(cb -> cb.getText().equals("장갑 착용")));
    }

    @Test
    public void testDeleteRuleButtonClick() {
        RuleManagementView view = (RuleManagementView) getPrivateField("view");

        // 체크박스를 강제로 선택
        JCheckBox toDelete = view.getRuleCheckBoxes().stream()
                .filter(cb -> cb.getText().equals("안전모 착용"))
                .findFirst()
                .orElse(null);

        assertNotNull(toDelete);
        toDelete.setSelected(true);

        // 삭제 버튼 클릭
        JButton deleteButton = view.getDeleteButton();
        // JOptionPane 때문에 YES 선택이 필요하지만 테스트 환경에서는 생략할 수도 있음
        deleteButton.doClick();

        // 삭제되었는지 확인
        assertFalse(view.getRuleCheckBoxes().stream()
                .anyMatch(cb -> cb.getText().equals("안전모 착용")));
    }

    private void setPrivateFieldInView(Object view, String fieldName, Object value) {
        try {
            Field field = view.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(view, value);
        } catch (Exception e) {
            fail("Cannot access field in view: " + e.getMessage());
        }
    }
}
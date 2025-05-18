/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rulemanagement;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RuleManagementModelTest {

    private RuleManagementModel model;
    private final String testFile = "test_rules_model.txt";

    @BeforeEach
    public void setUp() throws IOException {
        // 테스트 파일 초기화
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("안전모 착용\n실험복 착용\n");
        }
        model = new RuleManagementModel(testFile);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFile);
        if (file.exists()) file.delete();
    }

    @Test
    public void testGetRules() {
        List<String> rules = model.getRules();
        assertEquals(2, rules.size());
        assertTrue(rules.contains("안전모 착용"));
        assertTrue(rules.contains("실험복 착용"));
    }

    @Test
    public void testAddRule() throws IOException {
        model.addRule("장갑 착용");

        List<String> rules = model.getRules();
        assertTrue(rules.contains("장갑 착용"));
        assertEquals(3, rules.size());
    }

    @Test
    public void testDeleteRule() throws IOException {
        model.deleteRules(Collections.singletonList("안전모 착용"));

        List<String> rules = model.getRules();
        assertFalse(rules.contains("안전모 착용"));
        assertEquals(1, rules.size());
    }
}
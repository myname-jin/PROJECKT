/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package management;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.*;
/**
 *
 * @author adsd3
 */
public class NoticeControllerTest {
    
    public NoticeControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of saveNotice method, of class NoticeController.
     */
     private final String FILE_PATH = "C:\\SWG\\JAVAPROJECKT\\Notice.txt";

    @BeforeEach
    public void clearNoticeFile() {
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            writer.print(""); // 파일 완전 초기화
        } catch (IOException e) {
            e.printStackTrace();
            fail("파일 초기화 실패");
        }
    }

    @Test
    public void testSaveNotice() {
        NoticeModel model = new NoticeModel();
        NoticeController controller = new NoticeController(model);

        String content = "테스트 공지입니다.";
        controller.saveNotice(content);

        String[] result = controller.loadNotices();

        assertTrue(result.length > 0);
        assertEquals(content, result[result.length - 1]);
    }

    @Test
    public void testLoadNoticesWhenEmpty() {
        NoticeModel model = new NoticeModel();
        NoticeController controller = new NoticeController(model);

        String[] expected = new String[0];
        String[] result = controller.loadNotices();

        assertArrayEquals(expected, result, "공지사항이 없어야 합니다.");
    }
}

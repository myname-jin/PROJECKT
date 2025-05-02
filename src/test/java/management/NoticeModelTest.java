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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *
 * @author adsd3
 */
public class NoticeModelTest {
    
    public NoticeModelTest() {
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
     * Test of saveNotice method, of class NoticeModel.
     */
    
   
      private final String FILE_PATH = "C:\\SWG\\JAVAPROJECKT\\Notice.txt";

    // ✅ 테스트 전에 파일 내용 비우기 (자동 호출됨)
    @BeforeEach
    public void clearNoticeFile() {
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            writer.print("");  // 내용 완전히 초기화
        } catch (IOException e) {
            e.printStackTrace();
            fail("테스트 전에 파일 초기화에 실패했습니다.");
        }
    }

    @Test
    public void testLoadNoticesWhenEmpty() {
        NoticeModel instance = new NoticeModel();
        String[] expected = new String[0];
        String[] result = instance.loadNotices();

        assertArrayEquals(expected, result, "공지사항이 없어야 합니다.");
    }

    @Test
    public void testSaveAndLoadNotice() {
        NoticeModel instance = new NoticeModel();
        String content = "테스트 공지입니다.";

        instance.saveNotice(content);
        String[] result = instance.loadNotices();

        assertTrue(result.length > 0);
        assertEquals(content, result[result.length - 1]);
    }
    
}

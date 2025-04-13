/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author adsd3
 */
public class NoticeModel {
    // 파일 경로 설정
    private static final String FILE_PATH = "C:\\SWG\\JAVAPROJECKT\\Notice.txt";
    
    // 파일에 공지사항을 저장하는 메서드
    public void saveNotice(String content) {
        File file = new File(FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(content);
            writer.newLine();  // 줄바꿈 추가
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일에서 공지사항 내용을 읽어오는 메서드
    public String[] loadNotices() {
        File file = new File(FILE_PATH);
        ArrayList<String> notices = new ArrayList<>();
        
        // 파일을 읽어서 내용 저장
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notices.add(line);  // 각 줄을 notices 리스트에 추가
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 리스트를 배열로 변환하여 반환
        return notices.toArray(new String[0]);
    }
}
    


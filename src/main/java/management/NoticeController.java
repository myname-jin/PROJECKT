/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

/**
 *
 * @author adsd3
 */
public class NoticeController {
    
    private NoticeModel model;


    public NoticeController(NoticeModel model) {
        this.model = model;
    }

    // 공지사항 저장
    public void saveNotice(String noticeContent) {
        model.saveNotice(noticeContent);  // 파일에 저장
    }

    // 공지사항 목록을 읽어서 반환
    public String[] loadNotices() {
        return model.loadNotices();  // 파일에서 공지사항 읽어오기
    }
}
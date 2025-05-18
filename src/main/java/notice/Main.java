/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notice;

/**
 *
 * @author adsd3
 */
/* Main.java */
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/Notice.txt";
        NoticeRepository repo = new NoticeRepository(filePath);
        NoticeModel model = new NoticeModel(repo);

        SwingUtilities.invokeLater(() -> {
            NoticeListView listView = new NoticeListView();
            NoticeEditorView editorView = new NoticeEditorView(listView);
            NoticeController controller = new NoticeController(model, listView, editorView);
            listView.setVisible(true);

         
        });
    }
}

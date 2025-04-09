/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adsd3          Controller 
 */
public class NewClass1 {
    private NewClass model;

    public NewClass1(NewClass model) {
        this.model = model;
    }

    // 텍스트를 모델에 저장하고 리턴
    public String processText(String input) {
        model.setText(input);
        return model.getText(); // 그대로 리턴
    }
}

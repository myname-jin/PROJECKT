/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

/**
 *
 * @author suk22
 */
public class ReservationMgmtModel {

    private String studentId;
    private String department;
    private String name;
    private String room;
    private String time;
    //private String approved;

    public ReservationMgmtModel(String studentId, String department, String name, String room, String time, String approved) {
        this.studentId = studentId;
        this.department = department;
        this.name = name;
        this.room = room;
        this.time = time;
        //this.approved = approved;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public String getTime() {
        return time;
    }
}

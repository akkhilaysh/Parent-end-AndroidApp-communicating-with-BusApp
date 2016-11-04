package com.example.user.probbc;

/**
 * Created by Harshad Shinde on 23-03-2016.
 */
public class StudentStatusModel {
    private String schoolName;
    private String studentDetail;
    private String studentID;
    private String studentName;
    private String studentNumber;
    private String studentStatus;

    public StudentStatusModel(String schoolName, String studentID, String studentName, String studentNumber, String studentStatus) {
        this.schoolName = schoolName;
        //this.studentDetail = studentDetail;
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.studentStatus = studentStatus;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

  /*  public String getStudentDetail() {
        return studentDetail;
    }*/

   /* public void setStudentDetail(String studentDetail) {
        this.studentDetail = studentDetail;
    }*/

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        if(studentStatus==null){
            studentStatus="Absent";
        }

        this.studentStatus = studentStatus;
    }
}

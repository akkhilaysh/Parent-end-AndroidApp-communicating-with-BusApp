package com.example.user.probbc;

/**
 * Created by Harshad Shinde on 29-01-2016.
 */

public class StudentListProvider {
    private String childName;
    private  String childStatus;
    private int childPic;

    public StudentListProvider(int childPic, String childName, String childStatus) {
        this.setChildPic( childPic);
        this.setChildStatus( childStatus);
        this.setChildName( childName);
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getChildPic() {
        return childPic;
    }

    public void setChildPic(int childPic) {
        this.childPic = childPic;
    }

    public String getChildStatus() {
        return childStatus;
    }

    public void setChildStatus(String childStatus) {


        this.childStatus = childStatus; }



}

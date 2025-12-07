package model;


import service.MemberFunction;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author User
 */
public class Member {

    private int memberId;
    private String name;
    private char gender;
    private int age;

    public Member(int memberId, String name, char gender, int age) {
        this.memberId = memberId;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static boolean isMember(int memberId) {
        ArrayList<String[]> membersList = MemberFunction.scanMembers();
        ArrayList<Integer> memberIdList = new ArrayList<>();
        
        for (String[] m : membersList) {
            memberIdList.add(Integer.parseInt(m[0]));
        }
        
        for (Integer memId : memberIdList){
            if(memId.intValue() == memberId){
                return true;
            }
        }
        
        return false;
    }

    //Override the toString method.
    @Override
    public String toString() {
        String genderStr;
        if (this.gender == 'M') {
            genderStr = "Male";
        } else {
            genderStr = "Female";
        }
        return "Member " + memberId + "'s name is " + name + ", Gender " + genderStr + " with age " + age;
    }
}

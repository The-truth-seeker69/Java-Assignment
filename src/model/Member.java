package model;

import service.MemberService;
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

    /**
     * Checks if a member ID exists in the system.
     * 
     * @param memberId The member ID to check
     * @return true if member exists, false otherwise
     */
    public static boolean isMember(int memberId) {
        return MemberService.isMember(memberId);
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

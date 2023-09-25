/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author User
 */
public class MemberFunction {

    static Scanner scanner = new Scanner(System.in);

    public static ArrayList<String[]> scanMembers() {
        ArrayList<String[]> members = new ArrayList<>();
        String fileName = "members.txt";

        try {
            File member = new File(fileName);
            Scanner membersReader = new Scanner(member);
            members.clear();  // Clear the arrays before populating them again

            while (membersReader.hasNextLine()) {
                String line = membersReader.nextLine();
                String tokens[] = line.split(" ");

                if (tokens.length == 4) {
                    members.add(tokens);
                } else {
                    System.out.println("Invalid data format. " + line);
                }
            }

            membersReader.close();

        } catch (FileNotFoundException e) {

            System.out.println("An error occurred while reading to the file.");
            e.printStackTrace();

        }

        return members;
    }

    public static void addMember(String newMemberStr) {

        try {
            //Open the file for writing
            FileWriter memberAdd = new FileWriter("members.txt", true);
            BufferedWriter writeMember = new BufferedWriter(memberAdd);
            writeMember.write(newMemberStr);
            writeMember.close();

        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteMember(ArrayList<String[]> memberList, int deleteElement) {
        try {
            File memberFile = new File("members.txt");
            FileWriter fw = new FileWriter(memberFile);
            PrintWriter pw = new PrintWriter(fw);
            int count = 0;

            for (String[] m : memberList) {
                if (count == deleteElement) {
                    count++;
                    continue;
                } else {
                    count++;
                    String formattedMember = String.format("%s %s %s %s", m[0], m[1], m[2], m[3]);
                    pw.println(formattedMember);
                }
                
            }

            pw.close();
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

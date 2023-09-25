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

/**
 *
 * @author Wei Quan
 */
public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String name;
        char gender = 0;
        int age;
        char confirm = 0;
        int choice = -1;
        String tempMemberName;
        String[] tempMemberNameArray;
        String memberName;
        char continueAddInput = 0;
        char continueSearchInput = 0;
        char continueDelInput = 0;
        boolean continueAdd = false;
        boolean continueSearch = false;
        boolean continueDel = false;
        char continueMember = 0;

        do {
            System.out.println("\n\nMembers Menu");
            System.out.println("------------------");
            System.out.println("1. Add Members");
            System.out.println("2. Display Members");
            System.out.println("3. Search Members");
            System.out.println("4. Delete Members");
            System.out.println("0. Back");
            System.out.print("Enter Your Option: ");

            //loop until it reaches the break statement.
            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();  // clear the newline character
                    if (choice >= 0 && choice <= 4) {
                        break;
                    } else {
                        System.out.println("\nInvalid input. Please enter a valid option.");
                        System.out.print("Enter Your Option: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a valid option.");
                    scanner.next();  // Clear the invalid input
                    System.out.print("Enter Your Option: ");
                }
            }

            switch (choice) {
                case 1: {
                    do {
                        ArrayList<String[]> members = MemberFunction.scanMembers();
                        int lastIndex = members.size() - 1;
                        String[] lastmember = members.get(lastIndex);
                        boolean isValidGender = false;
                        Member addMember;
                        do {
                            System.out.print("Enter Member Name: ");
                            tempMemberName = scanner.nextLine();
                            if (!Validations.isValidName(tempMemberName)) {
                                System.out.println("Invalid Member Name, Please Try Again.");
                            }

                        } while (!Validations.isValidName(tempMemberName));

                        tempMemberNameArray = tempMemberName.split(" ");
                        memberName = String.join("_", tempMemberNameArray);

                        while (!isValidGender) {
                            System.out.print("Please Enter a Gender ( M / F ): ");
                            gender = scanner.next().charAt(0);

                            if (gender != 'M' && gender != 'm' && gender != 'F' && gender != 'f') {
                                System.out.println("Gender Not valid, Please Try Again.");
                            } else {
                                isValidGender = true;
                                gender = Character.toUpperCase(gender);
                                break;
                            }
                        }

                        while (true) {
                            try {
                                System.out.print("Enter Member Age: ");
                                age = scanner.nextInt();
                                scanner.nextLine();  // clear the newline character
                                if (Validations.isValidAge(age)) {
                                    break;
                                } else {
                                    System.out.println("\nInvalid Age. Please enter a valid age.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("\nInvalid input. Please enter a valid integer age.");
                                scanner.next();  // Clear the invalid input
                            }
                        }

                        StringBuilder newMember = new StringBuilder();
                        Integer lastMemberInt = Integer.valueOf(lastmember[0]) + 1;
                        Member newMemObj = new Member(lastMemberInt.intValue(), tempMemberName, gender, age);
                        newMember.append(lastMemberInt + " " + memberName + " " + gender + " " + age + "\n");
                        String newMemberStr = newMember.toString();
                        System.out.println("\n\nMember ID :" + newMemObj.getMemberId());
                        System.out.println("Member Name: " + newMemObj.getName());
                        System.out.println("Member Gender (Male / Female): " + newMemObj.getGender());
                        System.out.println("Member Age: " + newMemObj.getAge());
                        System.out.print("\nDo you want to add this member details into the system? (Y/N): ");
                        confirm = scanner.next().charAt(0);
                        scanner.nextLine();

                        while (confirm != 'Y' && confirm != 'y' && confirm != 'N' && confirm != 'n') {
                            System.out.println("Invalid input, Please Try Again.");
                            System.out.print("Do you want to add this member details into the system? (Y/N): ");
                            confirm = scanner.next().charAt(0);
                            scanner.nextLine();
                        }

                        if (confirm == 'Y' || confirm == 'y') {
                            MemberFunction.addMember(newMemberStr);
                            addMember = new Member(newMemObj.getMemberId(), newMemObj.getName(), newMemObj.getGender(), newMemObj.getAge());
                            System.out.println(addMember.toString() + " Has Been Successfully added to the Database.\n");
                        } else {
                            System.out.println("Member not added.");
                        }

                        System.out.print("Do you want to continue to add a new Member? (Y/N): ");
                        continueAddInput = scanner.next().charAt(0);
                        scanner.nextLine();

                        while (continueAddInput != 'Y' && continueAddInput != 'y' && continueAddInput != 'N' && continueAddInput != 'n') {
                            System.out.println("Invalid input, Please Try Again.");
                            System.out.print("Do you want to continue to add a new Member? (Y/N): ");
                            continueAddInput = scanner.next().charAt(0);
                            scanner.nextLine();
                        }

                        if (continueAddInput == 'Y' || continueAddInput == 'y') {
                            continueAdd = true;
                        } else {
                            continueAdd = false;
                            System.out.println("Redirected you out of the Add Member Function.");
                        }
                    } while (continueAdd == true);
                    break;
                }

                case 2: {
                    ArrayList<String[]> members = MemberFunction.scanMembers();
                    int count = 0;
                    System.out.printf("\n%-15s %-30s %-10s %-10s\n", "Member ID", "Member Name", "Gender", "Age");
                    for (String[] m : members) {
                        String formattedName = m[1].replace("_", " ");
                        System.out.printf("%-15s %-30s %-10s %-10s\n", m[0], formattedName, m[2], m[3]);
                        count++;
                    }
                    System.out.printf("\n %30s : %d Members\n", "Total Member Counts", count);
                    break;
                }

                case 3: {
                    do {
                        ArrayList<String[]> members = MemberFunction.scanMembers();
                        ArrayList<Integer> memberIdList = new ArrayList<>();
                        Member searchMember;
                        int element = 0;
                        int memberId;
                        boolean memberFound = false;

                        for (String[] m : members) {
                            memberIdList.add(Integer.parseInt(m[0]));
                        }

                        while (true) {
                            try {
                                System.out.print("Enter the Member ID you want to search: ");
                                memberId = scanner.nextInt();
                                scanner.nextLine();  // clear the newline character

                                while (!memberFound) {
                                    element = 0;
                                    for (Integer i : memberIdList) {
                                        if (i == memberId) {
                                            memberFound = true;
                                            break;
                                        }
                                        element++;
                                    }
                                    if (memberFound) {
                                        break;
                                    }
                                    System.out.println("Member ID not Found. Please Try Again.");
                                    System.out.print("Enter the Member ID you want to search: ");
                                    memberId = scanner.nextInt();
                                    scanner.nextLine();  // Consume the newline character
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("\nInvalid input. Please enter a valid integer for the member ID.");
                                scanner.next();  // Clear the invalid input
                            }
                        }

                        // Print the found member's details
                        String[] m = members.get(element);
                        String formattedName = m[1].replace("_", " ");
                        searchMember = new Member(Integer.parseInt(m[0]), formattedName, m[2].charAt(0), Integer.parseInt(m[3]));
//                        System.out.printf("\n%-15s %-30s %-10s %-10s\n", "Member ID", "Member Name", "Gender", "Age");
//                        System.out.printf("%-15s %-30s %-10s %-10s\n", m[0], formattedName, m[2], m[3]);
                        System.out.println("\n" + searchMember.toString() + ".\n");

                        System.out.print("Do you want to continue to search a Member? (Y/N): ");
                        continueSearchInput = scanner.next().charAt(0);
                        scanner.nextLine();

                        while (continueSearchInput != 'Y' && continueSearchInput != 'y' && continueSearchInput != 'N' && continueSearchInput != 'n') {
                            System.out.println("Invalid input, Please Try Again.");
                            System.out.print("Do you want to continue to search a Member? (Y/N): ");
                            continueSearchInput = scanner.next().charAt(0);
                            scanner.nextLine();
                        }

                        if (continueSearchInput == 'Y' || continueSearchInput == 'y') {
                            continueSearch = true;
                        } else {
                            continueSearch = false;
                            System.out.println("Redirected you out of the Search Member Function.");
                        }
                    } while (continueSearch == true);
                    break;
                }

                case 4: {
                    do {
                        ArrayList<String[]> members = MemberFunction.scanMembers();
                        ArrayList<Integer> memberIdList = new ArrayList<>();
                        Member delMember;
                        String[] delMemberStrArr;
                        int element = 0;
                        int memberId;
                        boolean memberFound = false;
                        char confirmDel = 0;

                        for (String[] m : members) {
                            memberIdList.add(Integer.parseInt(m[0]));
                        }

                        while (true) {
                            try {
                                System.out.print("Enter the Member ID you want to delete: ");
                                memberId = scanner.nextInt();
                                scanner.nextLine();  // clear the newline character

                                while (!memberFound) {
                                    element = 0;
                                    for (Integer i : memberIdList) {
                                        if (i == memberId) {
                                            memberFound = true;
                                            break;
                                        }
                                        element++;
                                    }
                                    if (memberFound) {
                                        break;
                                    }
                                    System.out.println("\nMember ID not Found. Please Try Again.");
                                    System.out.print("Enter the Member ID you want to delete: ");
                                    memberId = scanner.nextInt();
                                    scanner.nextLine();  // clear the newline character
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid integer for the member ID.");
                                scanner.next();  // Clear the invalid input
                            }
                        }

                        delMemberStrArr = members.get(element);
                        String formattedName = delMemberStrArr[1].replace("_", " ");
                        delMember = new Member(Integer.parseInt(delMemberStrArr[0]), formattedName, delMemberStrArr[2].charAt(0), Integer.parseInt(delMemberStrArr[3]));

                        System.out.println("\n" + delMember.toString() + " has been found.");
                        System.out.print("Are you sure you want to delete this member? (Y / N): ");
                        confirmDel = scanner.next().charAt(0);
                        scanner.nextLine();

                        while (confirmDel != 'y' && confirmDel != 'Y' && confirmDel != 'n' && confirmDel != 'N') {
                            System.out.println("\nInvalid Input. Please Try Again.");
                            System.out.print("Are you sure you want to delete this member? (Y / N): ");
                            confirmDel = scanner.next().charAt(0);
                            scanner.nextLine();
                        }

                        if (Character.toUpperCase(confirmDel) == 'Y') {
                            MemberFunction.deleteMember(members, element);
                            System.out.println("\n" + delMember.toString() + " Has Been Deleted SuccessFully from the Database.");
                        } else {
                            System.out.println("Member " + delMemberStrArr[0] + " has not been deleted.\n");
                        }

                        System.out.print("Do you want to continue to delete a Member? (Y/N): ");
                        continueDelInput = scanner.next().charAt(0);
                        scanner.nextLine();

                        while (continueDelInput != 'Y' && continueDelInput != 'y' && continueDelInput != 'N' && continueDelInput != 'n') {
                            System.out.println("Invalid input, Please Try Again.");
                            System.out.print("Do you want to continue to delete a Member? (Y/N): ");
                            continueDelInput = scanner.next().charAt(0);
                            scanner.nextLine();
                        }

                        if (continueDelInput == 'Y' || continueDelInput == 'y') {
                            continueDel = true;
                        } else {
                            continueDel = false;
                            System.out.println("Redirected you out of the Delete Member Function.");
                        }
                    } while (continueDel == true);
                    break;
                }

                //case 0:
                default: {
                    break;
                }
            }

            if (choice == 0) {
                break;
            }

            System.out.print("Do you want to continue to view the Member's Module? (Y/N): ");
            continueMember = scanner.next().charAt(0);
            scanner.nextLine();

            while (continueMember != 'Y' && continueMember != 'y' && continueMember != 'N' && continueMember != 'n') {
                System.out.println("Invalid input, Please Try Again.");
                System.out.print("Do you want to continue to view the Member's Module? (Y/N): ");
                continueMember = scanner.next().charAt(0);
                scanner.nextLine();
            }

        } while (continueMember == 'Y' || continueMember == 'y');
        System.out.println("Thank you for using the Member Module, Redirecting you out from the Member Module now.");
    }
}

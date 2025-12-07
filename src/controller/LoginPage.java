package controller;

import model.Order;
import model.CashPayment;
import model.CreditCardPayment;
import model.Product;
import model.User;
import model.Member;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

//THIS IS THE MAIN DRIVER

public class LoginPage {

    static Scanner scanner = new Scanner(System.in);
    static Scanner scan = new Scanner(System.in);
    static ArrayList<Double> productsPriceArr = Product.getProductsPriceArr();
    static ArrayList<String> productsArr = Product.getProductsArr();

    public static void main(String[] args) throws Exception {
        String usernameInput;
        String passwordInput;
        Scanner scanner = new Scanner(System.in);
        int menuChoice = -1;

        User defaultUser = new User("admin", "admin");

        System.out.print("Enter Username: ");
        usernameInput = scanner.nextLine();

        System.out.print("Enter PassWord: ");
        passwordInput = scanner.nextLine();

        while (!defaultUser.equalsUsername(usernameInput) || !defaultUser.equalsPassword(passwordInput)) {
            System.out.println("Invalid Login Credentials. Please try again.\n");
            System.out.print("Enter Username: ");
            usernameInput = scanner.nextLine();

            System.out.print("Enter PassWord: ");
            passwordInput = scanner.nextLine();

            if (defaultUser.equalsUsername(usernameInput) && defaultUser.equalsPassword(passwordInput)) {
                break;
            }
        }

        System.out.println("\nSuccessfully Logged In.\n");

        do {
            System.out.println("\nWhat category do you wanna view?");
            System.out.println("1. Order\n"
                    + "2. Product \n"
                    + "3. Member\n"
                    + "4. Payment\n"
                    + "0. Logout\n");
            System.out.print("Enter your option: ");
            while (true) {
                try {
                    menuChoice = scanner.nextInt();
                    scanner.nextLine();  // clear the newline character
                    if (menuChoice >= 0 && menuChoice <= 4) {
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

            switch (menuChoice) {
                case 1: {
                    orderModule();
                    break;
                }

                case 2: {
                    productModule();
                    break;
                }

                case 3: {
                    memberModule();
                    break;
                }

                case 4: {
                    paymentModule();
                    break;
                }

                case 0: {
                    break;
                }
            }
        } while (menuChoice != 0);
        System.out.println("Thanks for using TARUMT Grocery POS System.");
        System.out.println("Hope to see you next time.");
    }

    public static void memberModule() {
        int choice = -1;
        char continueAddInput = 0;
        char continueSearchInput = 0;
        char continueDelInput = 0;
        char continueEditInput = 0;
        boolean continueAdd = false;
        boolean continueSearch = false;
        boolean continueDel = false;
        boolean continueEdit = false;
        char continueMember = 0;

        System.out.println(Member.isMember(1000));

        do {
            System.out.println("\n\nMembers Menu");
            System.out.println("------------------");
            System.out.println("1. Add Members");
            System.out.println("2. Display Members (Members Report)");
            System.out.println("3. Search Members");
            System.out.println("4. Delete Members");
            System.out.println("5. Edit Members");
            System.out.println("0. Back");
            System.out.print("Enter Your Option: ");

            //loop until it reaches the break statement.
            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();  // clear the newline character
                    if (choice >= 0 && choice <= 5) {
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
                        addMember();

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
                    displayMember();
                    break;
                }

                case 3: {
                    do {
                        searchMember();
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
                        deleteMember();

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

                case 5: {
                    do {
                        editMember();

                        System.out.print("Do you want to continue to edit a Member Details? (Y/N): ");
                        continueEditInput = scanner.next().charAt(0);
                        scanner.nextLine();

                        while (continueEditInput != 'Y' && continueEditInput != 'y' && continueEditInput != 'N' && continueEditInput != 'n') {
                            System.out.println("Invalid input, Please Try Again.");
                            System.out.print("Do you want to continue to edit a Member Details? (Y/N): ");
                            continueEditInput = scanner.next().charAt(0);
                            scanner.nextLine();
                        }

                        if (continueEditInput == 'Y' || continueEditInput == 'y') {
                            continueEdit = true;
                        } else {
                            continueEdit = false;
                            System.out.println("Redirected you out of the Edit Member Function.");
                        }
                    } while (continueEdit == true);
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

    public static void editMember() {
        ArrayList<String[]> members = MemberFunction.scanMembers();
        int memberId;
        int element = -1;
        boolean memberFound = false;
        String newMemberName;
        char newGender = 0;
        boolean isValidGender = false;
        char confirmEdit = 0;

        // Get the member ID to edit
        while (true) {
            System.out.print("Enter the Member ID you want to edit: ");
            if (scanner.hasNextInt()) {
                memberId = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                scanner.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }

        }

        // Search for the member
        for (int i = 0; i < members.size(); i++) {
            int currentMemberId = Integer.parseInt(members.get(i)[0]);
            if (currentMemberId == memberId) {
                element = i;
                memberFound = true;
                break;
            }
        }

        if (memberFound) {
            // Member found, proceed with editing
            // Assuming members have the following format: memberId memberName gender age

            // Display current member details
            System.out.println("\nCurrent Member Details:");
            String foundMemberName = members.get(element)[1].replace("_", " ");
            Member foundMember = new Member(Integer.parseInt(members.get(element)[0]), foundMemberName, members.get(element)[2].charAt(0), Integer.parseInt(members.get(element)[3]));
            System.out.println(foundMember.toString());

            // Modify member details
            do {
                System.out.print("\nEnter New Member Name: ");
                newMemberName = scanner.nextLine();
                if (!Validations.isValidName(newMemberName)) {
                    System.out.println("Invalid Member Name, Please Try Again.");
                }

            } while (!Validations.isValidName(newMemberName));

            while (!isValidGender) {
                System.out.print("Enter New Gender (M/F): ");
                newGender = scanner.next().charAt(0);

                if (newGender != 'M' && newGender != 'm' && newGender != 'F' && newGender != 'f') {
                    System.out.println("Gender Not valid, Please Try Again.");
                } else {
                    isValidGender = true;
                    newGender = Character.toUpperCase(newGender);
                    break;
                }
            }

            int newAge;
            while (true) {
                try {
                    System.out.print("Enter New Age: ");
                    newAge = scanner.nextInt();
                    if (Validations.isValidAge(newAge)) {
                        break;
                    } else {
                        System.out.println("Invalid Age. Please enter a valid age.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer age.");
                    scanner.next();  // Clear the invalid input
                }
            }

            // Update member details
            members.get(element)[1] = newMemberName.replace(" ", "_");
            members.get(element)[2] = String.valueOf(newGender);
            members.get(element)[3] = String.valueOf(newAge);

            System.out.print("Are you sure you want to edit this member's details? (Y / N): ");
            confirmEdit = scanner.next().charAt(0);
            scanner.nextLine();

            while (confirmEdit != 'y' && confirmEdit != 'Y' && confirmEdit != 'n' && confirmEdit != 'N') {
                System.out.println("\nInvalid Input. Please Try Again.");
                System.out.print("Are you sure you want to edit this member's details? (Y / N): ");
                confirmEdit = scanner.next().charAt(0);
                scanner.nextLine();
            }

            if (Character.toUpperCase(confirmEdit) == 'Y') {
                MemberFunction.editMember(members);
                System.out.println("\nMember " + foundMember.getMemberId() + " Has Been Modified SuccessFully from the Database.\n");
            } else {
                System.out.println("\nMember " + foundMember.getMemberId() + " Has Not Been Modified from the Database.\n");
            }
        } else {
            System.out.println("\nMember " + memberId + " Not Found in the Database.");
        }

    }

    public static void addMember() {
        ArrayList<String[]> members = MemberFunction.scanMembers();
        String tempMemberName;
        String[] tempMemberNameArray;
        String memberName;
        char gender = 0;
        int age;
        char confirm = 0;
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
    }

    public static void deleteMember() {
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
    }

    public static void searchMember() {
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
    }

    public static void displayMember() {
        ArrayList<String[]> members = MemberFunction.scanMembers();
        int count = 0;
        System.out.printf("\n%-15s %-30s %-10s %-10s\n", "Member ID", "Member Name", "Gender", "Age");
        for (String[] m : members) {
            String formattedName = m[1].replace("_", " ");
            System.out.printf("%-15s %-30s %-10s %-10s\n", m[0], formattedName, m[2], m[3]);
            count++;
        }
        System.out.printf("\n %30s : %d Members\n", "Total Member Counts", count);
    }

    public static void productModule() throws Exception {
        String productName;
        Double productPrice;
        Scanner scan = new Scanner(System.in);
        char cont;
        char confirmAdd;
        int itemNum = 1;
        char continueProduct;

        ProductModule productmodule = new ProductModule();
        int choice = -1;

        char contDlt;
        char contMod;

        do {

            System.out.println("\n\n Product Menu");
            System.out.println("------------------");
            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Delete Products");
            System.out.println("4. Modify Products");
            System.out.println("0. Back");
            System.out.print("Enter Your Option: ");

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
                        System.out.print("Enter the product name you wanna add: ");
                        productName = scan.nextLine();
                        productName = productName.substring(0, 1).toUpperCase() + productName.substring(1).toLowerCase();
                        while (true) {
                            System.out.print("Enter the product price : ");
                            if (scan.hasNextInt()) {
                                productPrice = scan.nextDouble();
                                scan.nextLine();
                                break;
                            } else {
                                scan.next();
                                System.out.println("Invalid input. Please enter a valid number.");
                            }
                        }

                        while (true) {
                            System.out.print("Confirm add product? (Y/N): ");
                            System.out.print("");
                            confirmAdd = scan.next().charAt(0);

                            if (confirmAdd == 'Y' || confirmAdd == 'y') {
                                productmodule.addProduct(productName, productPrice);
                                break; // Exit the loop when valid input
                            } else if (confirmAdd == 'N' || confirmAdd == 'n') {
                                break;
                            } else {
                                productmodule.invalidMessage();
                            }
                        }

                        while (true) {
                            System.out.print("Do you still want to continue adding products? (Y/N): ");
                            cont = scan.next().charAt(0);
                            scan.nextLine();

                            if (cont == 'Y' || cont == 'y' || cont == 'N' || cont == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }

                    } while (cont != 'N' && cont != 'n');
                    break;
                }

                case 2: {
                    System.out.printf(" %-15s %-15s %-30s \n", "Product Code ", "ProductName", "Product Price(RM)");
                    productmodule.productsMenu();
                    break;
                }
                case 3: {

                    do {
                        productmodule.deleteProduct();
                        while (true) {
                            System.out.print("Do you still wanna continue delete item? (Y/N): ");
                            contDlt = scan.next().charAt(0);
                            scan.nextLine();

                            if (contDlt == 'Y' || contDlt == 'y' || contDlt == 'N' || contDlt == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contDlt != 'N' && contDlt != 'n');
                    break;
                }

                case 4: {
                    do {
                        productmodule.modifyProduct();
                        while (true) {
                            System.out.print("Do you still wanna continue modify item? (Y/N): ");
                            contMod = scan.next().charAt(0);
                            scan.nextLine();

                            if (contMod == 'Y' || contMod == 'y' || contMod == 'N' || contMod == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contMod != 'N' && contMod != 'n');
                    break;
                }

                case 0: {
                    break;
                }
            }

            if (choice == 0) {
                break;
            }

            System.out.print("Do you want to continue to view the Product's Module? (Y/N): ");
            continueProduct = scanner.next().charAt(0);
            scanner.nextLine();

            while (continueProduct != 'Y' && continueProduct != 'y' && continueProduct != 'N' && continueProduct != 'n') {
                System.out.println("Invalid input, Please Try Again.");
                System.out.print("Do you want to continue to view the Product's Module? (Y/N): ");
                continueProduct = scanner.next().charAt(0);
                scanner.nextLine();
            }

        } while (continueProduct != 'n' && continueProduct != 'N');
        System.out.println("Thank you for viewing the product module, Redirecting you back out to the main menu.");
    }

    public static void orderModule() throws Exception {
        ProductModule productmodule = new ProductModule();
        char contAddOrder = 0;
        char contSearchOrder = 0;
        char contDelOrder = 0;
        int orderChoice;
        char continueOrder;
        do {
            System.out.println("\nOrder Menu");
            System.out.println("==========");
            System.out.println("1. Add Order");
            System.out.println("2. Display Order");
            System.out.println("3. Search Order");
            System.out.println("4. Delete Order");
            System.out.println("0. Back");
            System.out.print("Enter your Option:");
            while (true) {
                try {
                    orderChoice = scan.nextInt();
                    scan.nextLine();  // clear the newline character
                    if (orderChoice >= 0 && orderChoice <= 4) {
                        break;
                    } else {
                        System.out.println("\nInvalid input. Please enter a valid option.");
                        System.out.print("Enter Your Option: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a valid option.");
                    scan.next();  // Clear the invalid input
                    System.out.print("Enter Your Option: ");
                }
            }

            switch (orderChoice) {
                case 1: {
                    do {
                        addOrder();
                        while (true) {
                            System.out.print("Do you still wanna continue to add an order? (Y/N): ");
                            contAddOrder = scan.next().charAt(0);
                            scan.nextLine();

                            if (contAddOrder == 'Y' || contAddOrder == 'y' || contAddOrder == 'N' || contAddOrder == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contAddOrder != 'N' && contAddOrder != 'n');
                    break;
                }

                case 2: {
                    displayOrder();
                    break;
                }

                case 3: {
                    do {
                        searchOrder();
                        while (true) {

                            System.out.print("Do you still wanna continue to search an order? (Y/N): ");
                            contSearchOrder = scan.next().charAt(0);
                            scan.nextLine();

                            if (contSearchOrder == 'Y' || contSearchOrder == 'y' || contSearchOrder == 'N' || contSearchOrder == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contSearchOrder != 'N' && contSearchOrder != 'n');
                    break;
                }

                case 4: {
                    do {
                        deleteOrder();
                        while (true) {

                            System.out.print("Do you still wanna continue to delete an order? (Y/N): ");
                            contDelOrder = scan.next().charAt(0);
                            scan.nextLine();

                            if (contDelOrder == 'Y' || contDelOrder == 'y' || contDelOrder == 'N' || contDelOrder == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contDelOrder != 'N' && contDelOrder != 'n');
                    break;
                }

                case 0:
                    break;
            }

            if (orderChoice == 0) {
                break;
            }
            System.out.print("Do you want to continue to view the Order's Module? (Y/N): ");
            continueOrder = scan.next().charAt(0);
            scan.nextLine();

            while (continueOrder != 'Y' && continueOrder != 'y' && continueOrder != 'N' && continueOrder != 'n') {
                System.out.println("Invalid input, Please Try Again.");
                System.out.print("Do you want to continue to view the Order's Module? (Y/N): ");
                continueOrder = scan.next().charAt(0);
                scan.nextLine();
            }
        } while (continueOrder != 'n' && continueOrder != 'N');

        System.out.println("Thank you for viewing the order module, Redirecting you back out to the main menu.");
    }

    public static void addOrder() throws Exception {
        int orderCount = 0;
        char confirmAdd;
        char cont;
        Integer orderNo;
        int lastOrderNo;
        final double discount = 0.1;
        double discountAmt = 0;
        boolean isMember;
        double subtotal = 0;
        double total = 0;
        String joinedOrderString;
        int productIndex = 0;
        int prodQuantity = 0;
        int memberId;

        File order = new File("order.txt");
        Scanner orderReader = new Scanner(order);
        ArrayList<Integer> orderNoList = new ArrayList<>();
        String[] orderDetailsArr;
        String orderDetails;

        while (orderReader.hasNextLine()) {
            orderDetails = orderReader.nextLine();
            orderDetailsArr = orderDetails.split("_");
            String tempOrderNo = orderDetailsArr[0];
            orderNo = Integer.valueOf(tempOrderNo);
            orderNoList.add(orderNo);
        }

        int lastOrderNoElement = orderNoList.size() - 1;
        lastOrderNo = orderNoList.get(lastOrderNoElement);
        ArrayList<String> orderArr = new Order().getOrderArr();

        FileWriter orderFile = new FileWriter("order.txt", true);
        BufferedWriter writerOrder = new BufferedWriter(orderFile);
        ProductModule Productmodule = new ProductModule();

        int count = Productmodule.initializeProducts();

        do {
            System.out.println("Product Menu\n==========================");
            Productmodule.productsMenu();
            while (true) {
                System.out.print("Enter the product no you want to add to the order: ");
                if (scan.hasNextInt()) {
                    productIndex = scan.nextInt();
                    scan.nextLine();

                    if (productIndex > 0 && productIndex <= count) {
                        // Valid input, break out of the loop
                        break;
                    } else {
                        System.out.println("Invalid Product ID. Please enter a valid number.");
                    }
                } else {
                    scan.next(); // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            while (true) {
                System.out.print("Enter the quantity: ");

                if (scan.hasNextInt()) {
                    prodQuantity = scan.nextInt();

                    if (prodQuantity > 0) {
                        // Valid input, break out of the loop
                        break;
                    } else {
                        System.out.println("Invalid Quantity. Please enter a valid number.");
                    }
                } else {
                    scan.next(); // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            String orderLine = Product.getProductsArr().get(productIndex - 1) + " "
                    + Product.getProductsPriceArr().get(productIndex - 1) + " "
                    + prodQuantity;
            orderArr.add(orderLine);

            joinedOrderString = String.join(",", orderArr);
            System.out.println("\nYour Order Details:");

            for (String orderArrPrint : orderArr) {
                String[] tempOrder = orderArrPrint.split(" ");

                System.out.println(tempOrder[0] + " x " + tempOrder[2] + " units.");
            }

            total += Product.getProductsPriceArr().get(productIndex - 1) * prodQuantity;
            // Write the order details to the text file

            while (true) {
                System.out.print("\nContinue adding to the order? (Y/N): ");
                cont = scan.next().charAt(0);

                if (cont == 'Y' || cont == 'y') {
                    // Increment orderNo if the user continues to add to the order
                    break;
                } else if (cont == 'N' || cont == 'n') {
                    break;
                }
                System.out.println("Invalid Option.");
            }

        } while (cont != 'N' && cont != 'n');
        while (true) {
            System.out.print("Are you a Member? (Y/N): ");
            cont = scan.next().charAt(0);

            if (cont == 'Y' || cont == 'y') {

                while (true) {
                    System.out.print("Enter your member ID: ");
                    if (scan.hasNextInt()) {
                        memberId = scan.nextInt();
                        scan.nextLine();
                        break;
                    } else {
                        scan.next();
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

                isMember = Member.isMember(memberId);
                if (isMember) {
                    System.out.println("Member " + memberId + " is a member of our company.");
                    System.out.println("10% Discount is provided.");

                    discountAmt = total * discount;

                } else {
                    System.out.println("Sorry, Unfortunately Member " + memberId + " is not a member of our company.");
                    System.out.println("No discount provided.");
                }
                break;
            } else if (cont == 'N' || cont == 'n') {
                break;
            }
            System.out.println("Invalid Option.");
        }
        int itemCount = 0;
        subtotal = total - discountAmt;
        System.out.println("\nYour Sum Order Details:");
        System.out.printf("%-15s %-15s %-15s\n", "Product Name", "Price", "Quantity");
        String[] sumOrderArr = joinedOrderString.split(",");
        for (String s : sumOrderArr) {
            itemCount++;
            String[] tempS = s.split(" ");
            System.out.printf("%-15s %-15s %-15s\n", tempS[0], tempS[1], tempS[2]);
        }
        System.out.printf("\nTotal Amount (RM): %.2f\n", total);
        System.out.printf("Discounted Amount (RM): %.2f\n", discountAmt);
        System.out.printf("Subtotal (RM): %.2f\n", subtotal);

        makePayment(subtotal, sumOrderArr, itemCount, lastOrderNo + 1);

        try {
            lastOrderNo++;
            writerOrder.write(lastOrderNo + "_" + joinedOrderString);
            writerOrder.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writerOrder.close();
    }
//display order function

    public static void displayOrder() {
        String orderDetails;
        String orderNo;
        int moreThanOneItem = 0;
        double subtotal = 0;

        try {
            File order = new File("order.txt");
            Scanner orderReader = new Scanner(order);

            ArrayList<String[]> orderDetailsList = new ArrayList<>();
            System.out.printf("%-15s %-15s %-10s %-10s\n", "Order ID", "Product Name", "Price", "Quantity");
            System.out.println("===================================================\n");
            while (orderReader.hasNextLine()) {
                orderDetails = orderReader.nextLine();
                String[] temp = orderDetails.split("_");
                orderDetailsList.add(temp);
            }

            for (String[] s : orderDetailsList) {
                orderNo = s[0];
                String[] tempDetails = s[1].split(",");
                System.out.printf("%-15s ", orderNo);

                for (String tod : tempDetails) {
                    String[] tempOrderDetails = tod.split(" ");
                    if (moreThanOneItem == 0) {
                        System.out.printf("%-15s %-10s %-10s\n", tempOrderDetails[0], tempOrderDetails[1], tempOrderDetails[2]);
                        subtotal += Double.parseDouble(tempOrderDetails[1]) * Double.parseDouble(tempOrderDetails[2]);
                        moreThanOneItem++;
                    } else {
                        System.out.printf("%-15s %-15s %-10s %-10s\n", "", tempOrderDetails[0], tempOrderDetails[1], tempOrderDetails[2]);
                        subtotal += Double.parseDouble(tempOrderDetails[1]) * Double.parseDouble(tempOrderDetails[2]);
                    }
                }

                moreThanOneItem = 0;

                System.out.println("Sum Net Total For Order No " + orderNo + " is RM " + subtotal + "\n\n");
                subtotal = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteOrder() throws Exception {
        ArrayList<String[]> orderDetailsList = new ArrayList<>();
        ArrayList<String> orderNoList = new ArrayList<>();
        int ordDltNo;
        String orderDetails;
        char confirmDlt = 0;

        try {
            File order = new File("order.txt");
            Scanner orderReader = new Scanner(order);

            while (orderReader.hasNextLine()) {
                orderDetails = orderReader.nextLine();
                String[] orderDetailsArray = orderDetails.split("_");
                orderDetailsList.add(orderDetailsArray);
                // order id
                orderNoList.add(orderDetailsArray[0]);
            }

            orderReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayOrder();

        while (true) {
            System.out.print("\nEnter the order id that you want to delete: ");
            if (scan.hasNextInt()) {
                ordDltNo = scan.nextInt();
                scan.nextLine();
                break;
            } else {
                scan.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        int element = -1; // Initialize to an invalid index
        for (int i = 0; i < orderNoList.size(); i++) {
            if (Integer.parseInt(orderNoList.get(i)) == ordDltNo) {
                element = i;
                break;
            }
        }

        if (element != -1) {
            while (true) {
                System.out.printf("Do you want to delete Order ID %03d ? (Y/N): ", ordDltNo);
                confirmDlt = scan.next().charAt(0);
                scan.nextLine();

                if (confirmDlt == 'Y' || confirmDlt == 'y' || confirmDlt == 'N' || confirmDlt == 'n') {
                    break; // Exit the loop when valid input
                } else {
                    System.out.println("Invalid Option.");
                }
            }

            if (confirmDlt == 'N' || confirmDlt == 'n') {
                System.out.printf("Order ID %03d is Not Deleted.\n", ordDltNo);
            } else {

                orderDetailsList.remove(element);

                try {
                    File order = new File("order.txt");
                    FileWriter orderFile = new FileWriter(order);
                    PrintWriter writerOrder = new PrintWriter(orderFile);

                    for (String[] s : orderDetailsList) {
                        writerOrder.write(s[0] + "_" + s[1] + "\n");
                    }

                    writerOrder.close();

                    System.out.printf("Order ID %03d Deleted Successfully.\n", ordDltNo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Order ID not found.");
        }
    }

    public static void searchOrder() throws Exception {
        String orderNo;
        int moreThanOneItem = 0;
        double subtotal = 0;
        int element = 0;
        char cont;
        ArrayList<String[]> orderDetailsList = new ArrayList<>();
        int searchedOrderId;
        String orderDetails;

        try {
            File order = new File("order.txt");
            Scanner orderReader = new Scanner(order);

            while (orderReader.hasNextLine()) {
                orderDetails = orderReader.nextLine();
                String[] orderDetailsArray = orderDetails.split("_");
                orderDetailsList.add(orderDetailsArray);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean orderFound = false;

        while (true) {
            System.out.print("\nEnter the order ID that you want to search : ");
            if (scan.hasNextInt()) {
                searchedOrderId = scan.nextInt();
                scan.nextLine();
                break;
            } else {
                scan.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        while (orderFound == false) {
            element = 0;
            for (String[] s : orderDetailsList) {
                if (Integer.parseInt(s[0]) == searchedOrderId) {
                    orderFound = true;
                    System.out.printf("\n%-15s %-15s %-10s %-10s\n", "Order ID", "Product Name", "Price", "Quantity");
                    System.out.println("===================================================");
                    orderNo = s[0];
                    String[] tempDetails = s[1].split(",");
                    System.out.printf("%-15s ", orderNo);

                    for (String tod : tempDetails) {
                        String[] tempOrderDetails = tod.split(" ");
                        if (moreThanOneItem == 0) {
                            System.out.printf("%-15s %-10s %-10s\n", tempOrderDetails[0], tempOrderDetails[1], tempOrderDetails[2]);
                            subtotal += Double.parseDouble(tempOrderDetails[1]) * Double.parseDouble(tempOrderDetails[2]);
                            moreThanOneItem++;
                        } else {
                            System.out.printf("%-15s %-15s %-10s %-10s\n", "", tempOrderDetails[0], tempOrderDetails[1], tempOrderDetails[2]);
                            subtotal += Double.parseDouble(tempOrderDetails[1]) * Double.parseDouble(tempOrderDetails[2]);
                        }
                    }

                    moreThanOneItem = 0;

                    System.out.println("Sum Net Total For Order No " + orderNo + " is RM " + subtotal + "\n\n");
                    subtotal = 0;

                    break;
                } else {
                    element++;
                }
            }

            if (orderFound == false) {
                System.out.println("Order ID not found. Please Try Again");
                System.out.print("\nEnter the order id that you want to search :");
                searchedOrderId = scan.nextInt();
            }
        }
    }

    public static void paymentModule() {

        String paymentMenuChoice = "";
        boolean continueDel = false;
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("Payment Menu");
            System.out.println("================================");
            System.out.println("    1.View payment history");
            System.out.println("    2.Search payment history");
            System.out.println("    3.Delete payment history");
            System.out.println("    0.Back");
            System.out.println("================================");
            System.out.print("Enter an option:");
            paymentMenuChoice = scan.next();
            scan.nextLine();

            if (paymentMenuChoice.charAt(0) != '0' && paymentMenuChoice.charAt(0) != '1' && paymentMenuChoice.charAt(0) != '2' && paymentMenuChoice.charAt(0) != '3' || paymentMenuChoice.length() != 1) {
                System.out.println("Invalid input!");
            }

            if (paymentMenuChoice.charAt(0) == '1' && paymentMenuChoice.length() == 1) {
                ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                int count = 0;
                System.out.println("\nPayment History");
                System.out.println("=========================================");
                System.out.printf("%-11s %-11s  %-10s\n", "Payment ID", "Total Amount", "Payment Method");
                System.out.println("=========================================");
                for (String[] m : payments) {
                    String formattedMethod = m[2].replace("_", " ");
                    System.out.printf("%-11s RM%9s   %-10s\n", m[0], m[1], formattedMethod);
                    count++;
                }
                System.out.println("=========================================");
                System.out.printf(" %21s : %d Payment(s)\n\n", "Total Payment Count", count);
            }

            if (paymentMenuChoice.charAt(0) == '2' && paymentMenuChoice.length() == 1) {
                ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                ArrayList<Integer> paymentIDList = new ArrayList<>();
                int element = 0;
                int paymentId;
                boolean paymentFound = false;

                for (String[] m : payments) {
                    paymentIDList.add(Integer.parseInt(m[0]));
                }

                while (true) {
                    try {
                        System.out.print("Enter the Payment ID you want to search: ");
                        paymentId = scan.nextInt();
                        scan.nextLine();  // clear the newline character

                        while (!paymentFound) {
                            element = 0;
                            for (Integer i : paymentIDList) {
                                if (i.intValue() == paymentId) {
                                    paymentFound = true;
                                    break;
                                }
                                element++;
                            }
                            if (paymentFound) {
                                break;
                            }
                            System.out.println("Payment ID not Found. Please Try Again.");
                            System.out.print("Enter the Payment ID you want to search: ");
                            paymentId = scan.nextInt();
                            scan.nextLine();  // Consume the newline character
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("\nInvalid input. Please enter a valid integer for the Payment ID.");
                        scan.next();  // Clear the invalid input
                    }
                }

                // Print the found payment's details
                String[] m = payments.get(element);
                String formattedMethod = m[2].replace("_", " ");

                if (formattedMethod.equals("Cash")) {
                    CashPayment searchPayment;
                    searchPayment = new CashPayment(Double.parseDouble(m[1]), Integer.parseInt(m[0]));
                    System.out.println("\n" + searchPayment.toString() + ".\n");
                } else if (formattedMethod.equals("Credit Card")) {
                    CreditCardPayment searchPayment;
                    searchPayment = new CreditCardPayment(Double.parseDouble(m[1]), Integer.parseInt(m[0]), "dummy");
                    System.out.println("\n" + searchPayment.toString() + ".\n");
                }

            }

            if (paymentMenuChoice.charAt(0) == '3' && paymentMenuChoice.length() == 1) {
                do {
                    ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                    ArrayList<Integer> paymentIdList = new ArrayList<>();
                    String[] delPaymentStrArr;
                    int element = 0;
                    int paymentId;
                    boolean paymentFound = false;
                    char confirmDel = 0;
                    char continueDelInput = 0;

                    for (String[] m : payments) {
                        paymentIdList.add(Integer.parseInt(m[0]));
                    }

                    while (true) {
                        try {
                            System.out.print("Enter the Payment ID you want to delete: ");
                            paymentId = scan.nextInt();
                            scan.nextLine();  // clear the newline character

                            while (!paymentFound) {
                                element = 0;
                                for (Integer i : paymentIdList) {
                                    if (i == paymentId) {
                                        paymentFound = true;
                                        break;
                                    }
                                    element++;
                                }
                                if (paymentFound) {
                                    break;
                                }
                                System.out.println("\nPayment ID not Found. Please Try Again.");
                                System.out.print("Enter the Payment ID you want to delete: ");
                                paymentId = scan.nextInt();
                                scan.nextLine();  // clear the newline character
                            }
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid integer for the Payment ID.");
                            scan.next();  // Clear the invalid input
                        }
                    }

                    delPaymentStrArr = payments.get(element);
                    String formattedName = delPaymentStrArr[2].replace("_", " ");

                    System.out.println("\nPayment with ID:" + paymentId + " has been found.");
                    System.out.print("Are you sure you want to delete this payment history? (Y / N): ");
                    confirmDel = scan.next().charAt(0);
                    scan.nextLine();

                    while (confirmDel != 'y' && confirmDel != 'Y' && confirmDel != 'n' && confirmDel != 'N') {
                        System.out.println("\nInvalid Input. Please Try Again.");
                        System.out.print("Are you sure you want to delete this payment history? (Y / N): ");
                        confirmDel = scan.next().charAt(0);
                        scan.nextLine();
                    }

                    if (Character.toUpperCase(confirmDel) == 'Y') {
                        PaymentFileFunction.deletePayment(payments, element);
                        System.out.println("\nPayment with ID:" + delPaymentStrArr[0] + " has been deleted successFully.");
                    } else {
                        System.out.println("Payment with ID:" + delPaymentStrArr[0] + " has not been deleted.\n");
                    }

                    System.out.print("Do you want to continue to delete a payment? (Y/N): ");
                    continueDelInput = scan.next().charAt(0);
                    scan.nextLine();

                    while (continueDelInput != 'Y' && continueDelInput != 'y' && continueDelInput != 'N' && continueDelInput != 'n') {
                        System.out.println("Invalid input, Please Try Again.");
                        System.out.print("Do you want to continue to delete a payment? (Y/N): ");
                        continueDelInput = scan.next().charAt(0);
                        scan.nextLine();
                    }

                    if (continueDelInput == 'Y' || continueDelInput == 'y') {
                        continueDel = true;
                    } else {
                        continueDel = false;
                        System.out.println("End of delete payment function.");
                    }
                } while (continueDel == true);
            }

        } while (paymentMenuChoice.charAt(0) != '0' || paymentMenuChoice.length() != 1);

    }

    public static void makePayment(double subtotal, String[] orderArr, int numOfItems, int orderID) {
        String selectPayment = "";
        double totalPaid = 0;
        ArrayList<String> itemsArr = new ArrayList<>();
        ArrayList<Double> pricesArr = new ArrayList<>();
        ArrayList<Integer> quantitiesArr = new ArrayList<>();
        //call payment
        do {

            String printChoice = "";
            boolean checkCardNum;
            // Create a Date object
            Date currentDate = new Date();

            // Define a date and time pattern
            String pattern = "dd-MM-yyyy HH:mm:ss"; // Example pattern

            // Create a SimpleDateFormat object with the specified pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

            // Convert the Date to a String using the SimpleDateFormat
            String dateStr = dateFormat.format(currentDate);

            System.out.println("\nPayment Methods");
            System.out.println("==============================");
            System.out.println("    1.Credit card");
            System.out.println("    2.Cash");
            System.out.println("==============================");
            System.out.print("Select payment method:");
            selectPayment = scan.next();
            scan.nextLine();

            if (selectPayment.charAt(0) != '1' && selectPayment.charAt(0) != '2' || selectPayment.length() != 1) {
                System.out.println("Invalid input!\n");
            }

            if (selectPayment.charAt(0) == '1' && selectPayment.length() == 1) {
                do {
                    System.out.print("\nEnter credit card number(12digits):");
                    String inputCardNum = scan.nextLine();
                    double totalAmount = subtotal;

                    ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                    int lastIndex = payments.size() - 1;
                    String[] lastpayment = payments.get(lastIndex);
                    Integer lastPaymentInt = Integer.valueOf(lastpayment[0]) + 1;

                    CreditCardPayment creditCardPayment = new CreditCardPayment(totalAmount, lastPaymentInt, inputCardNum);
                    checkCardNum = creditCardPayment.checkCardID(inputCardNum);
                    if (!checkCardNum) {
                        System.out.println("Credit card number is invalid!");
                    } else {
                        creditCardPayment.paymentSuccessful(totalAmount, creditCardPayment.getPaymentID());
                        String[] tempPaymentMethod = creditCardPayment.getPaymentMethod().split(" ");
                        String paymentMethod = String.join("_", tempPaymentMethod);
                        StringBuilder newPayment = new StringBuilder();
                        newPayment.append(lastPaymentInt + " " + totalAmount + " " + paymentMethod);
                        String newPaymentStr = lastPaymentInt + " " + totalAmount + " " + paymentMethod + "\n";
                        PaymentFileFunction.addPayment(newPaymentStr);
                    }

                    if (checkCardNum) {
                        do {
                            System.out.print("Thanks for purchasing. Print receipt? (Y/N):");
                            printChoice = scan.next();
                            scan.nextLine();

                            if (printChoice.charAt(0) == 'Y' || printChoice.charAt(0) == 'y' && printChoice.length() == 1) {
                                Receipt receipt = new Receipt();

                                for (String s : orderArr) {
                                    String[] orderDetails = s.split(" ");

                                    itemsArr.add(orderDetails[0]);
                                    pricesArr.add(Double.parseDouble(orderDetails[1]));
                                    quantitiesArr.add(Integer.parseInt(orderDetails[2]));

                                }

                                String[] itemsArray = itemsArr.toArray(new String[itemsArr.size()]);
                                String[] items = new String[itemsArray.length];

                                for (int i = 0; i < itemsArray.length; i++) {
                                    items[i] = itemsArray[i].toString();
                                }

                                Double[] pricesArray = pricesArr.toArray(new Double[pricesArr.size()]);
                                double[] prices = new double[pricesArray.length];

                                for (int i = 0; i < pricesArray.length; i++) {
                                    prices[i] = pricesArray[i].doubleValue();
                                }

                                Integer[] quantitiesArray = quantitiesArr.toArray(new Integer[quantitiesArr.size()]);
                                int[] quantities = new int[quantitiesArray.length];

                                for (int i = 0; i < itemsArray.length; i++) {
                                    quantities[i] = quantitiesArray[i].intValue();
                                }

                                receipt.printReceipt(subtotal, dateStr, items, prices, quantities, itemsArray.length, creditCardPayment.getBALANCE(), totalAmount, orderID, creditCardPayment.getPaymentID(), creditCardPayment.getPaymentMethod());
                            }

                            if (printChoice.charAt(0) != 'Y' && printChoice.charAt(0) != 'y' && printChoice.charAt(0) != 'n' && printChoice.charAt(0) != 'N' || printChoice.length() > 1) {
                                System.out.println("Invalid input!\n");
                            }
                        } while (printChoice.length() != 1 || printChoice.charAt(0) != 'Y' && printChoice.charAt(0) != 'y' && printChoice.charAt(0) != 'n' && printChoice.charAt(0) != 'N');
                    }
                } while (!checkCardNum);

            } else if (selectPayment.charAt(0) == '2' && selectPayment.length() == 1) {

                boolean validTotal = false;
                while (!validTotal) {

                    System.out.print("Enter total paid:");
                    String input = scan.nextLine();

                    try {

                        // Attempt to parse the input as a double
                        totalPaid = Double.parseDouble(input);
                        if (totalPaid < subtotal) {
                            System.out.println("Total paid cannot be lesser than subtotal!");
                        } else {

                            // If parsing succeeds, input is valid
                            validTotal = true;
                        }
                    } catch (NumberFormatException e) {
                        // Parsing failed, input is not a valid double
                        System.out.println("Invalid input. Please enter a valid double value.");
                    }
                }

                ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                int lastIndex = payments.size() - 1;
                String[] lastpayment = payments.get(lastIndex);
                Integer lastPaymentInt = Integer.valueOf(lastpayment[0]) + 1;
                CashPayment cashPayment = new CashPayment(totalPaid, lastPaymentInt);
                cashPayment.calculateBalance(totalPaid, subtotal);
                cashPayment.paymentSuccessful(totalPaid, cashPayment.getPaymentID());
                String[] tempPaymentMethod = cashPayment.getPaymentMethod().split(" ");
                String paymentMethod = String.join("_", tempPaymentMethod);
                StringBuilder newPayment = new StringBuilder();
                newPayment.append(lastPaymentInt + " " + totalPaid + " " + paymentMethod);
                String newPaymentStr = lastPaymentInt + " " + totalPaid + " " + paymentMethod + "\n";
                PaymentFileFunction.addPayment(newPaymentStr);

                do {
                    System.out.print("Thanks for purchasing. Print receipt? (Y/N):");
                    printChoice = scan.next();
                    scan.nextLine();

                    for (String s : orderArr) {
                        String[] orderDetails = s.split(" ");

                        itemsArr.add(orderDetails[0]);
                        pricesArr.add(Double.parseDouble(orderDetails[1]));
                        quantitiesArr.add(Integer.parseInt(orderDetails[2]));

                    }

                    String[] itemsArray = itemsArr.toArray(new String[itemsArr.size()]);
                    String[] items = new String[itemsArray.length];

                    for (int i = 0; i < itemsArray.length; i++) {
                        items[i] = itemsArray[i].toString();
                    }

                    Double[] pricesArray = pricesArr.toArray(new Double[pricesArr.size()]);
                    double[] prices = new double[pricesArray.length];

                    for (int i = 0; i < pricesArray.length; i++) {
                        prices[i] = pricesArray[i].doubleValue();
                    }

                    Integer[] quantitiesArray = quantitiesArr.toArray(new Integer[quantitiesArr.size()]);
                    int[] quantities = new int[quantitiesArray.length];

                    for (int i = 0; i < itemsArray.length; i++) {
                        quantities[i] = quantitiesArray[i].intValue();
                    }

                    if (printChoice.charAt(0) == 'Y' || printChoice.charAt(0) == 'y' && printChoice.length() == 1) {
                        Receipt receipt = new Receipt();

                        receipt.printReceipt(subtotal, dateStr, items, prices, quantities, itemsArray.length, cashPayment.getBalance(), totalPaid, orderID, cashPayment.getPaymentID(), cashPayment.getPaymentMethod());
                    }
                    if (printChoice.charAt(0) != 'Y' && printChoice.charAt(0) != 'y' && printChoice.charAt(0) != 'n' && printChoice.charAt(0) != 'N' || printChoice.length() > 1) {
                        System.out.println("Invalid input!\n");
                    }
                } while (printChoice.length() != 1 || printChoice.charAt(0) != 'Y' && printChoice.charAt(0) != 'y' && printChoice.charAt(0) != 'n' && printChoice.charAt(0) != 'N');
            }

        } while (selectPayment.charAt(0) != '1' && selectPayment.charAt(0) != '2' || selectPayment.length() != 1);
    }
}

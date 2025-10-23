package Lesson_6;
import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of task:");
        int n = Integer.parseInt(sc.nextLine());

        switch(n){
            case 1: {
                Set<Student> person = new HashSet<>();
                person.add(new Student("Alex", 1, 2, new int[]{5, 4, 5, 4}));
                person.add(new Student("Sofa", 1, 1, new int[]{3, 2, 2, 2}));
                person.add(new Student("Ivan", 2, 3, new int[]{4, 5, 5, 4}));

                System.out.println("\n\t-- All students in our school --");
                for (Student arr:person){
                    arr.getInformation();
                }

                Student.removePerson(person);
                Student.movePerson(person);
                Student.printStudents(person,3);
                Student.printStudents(person,4);
            } break;
            case 2:{
                Directory notebook = new Directory();

                notebook.addNew("Alexandrov",new String[] {"89221100006","89112345566"});
                notebook.addNew("Ivanov",new String[] {"88765544333","87443655544"});
                notebook.addNew("Kirov");
                notebook.addNew("Chikov");

                notebook.getSearch("Alexandrov");
                notebook.getSearch("Ivanov");
                notebook.getSearch("Kirov");
            } break;
        }


    }
}

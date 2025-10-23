package Lesson_6;
import java.util.*;

// -- Задание 1

// 1) 1.1 - Создайте класс Student, содержащий следующие характеристики – имя, группа, курс, оценки по предметам. 
// 1.2 - Создайте коллекцию, содержащую объекты класса Student. 

// 2) 2.1 - Следует написать метод, который удаляет студентов со средним баллом < 3, 
// 2.2 - а также отдельный метод, который переводит студента на следующий курс, если средний балл >= 3. 

// 3) Напишите метод printStudents(Set<Student> students, int course), который получает список студентов и номер курса. 
// Метод печатает на консоль имена тех студентов, которые обучаются на данном курсе.

public class Student {
    String name; int group; int course;
    int[] graids;

    public Student(String name, int group, int course, int[] graids){
        this.name = name;
        this.group = group;
        this.course = course;
        this.graids = graids;
    }
    public String getName(){return name;}
    public int getGroup(){return group;}
    public int getCourse(){return course;}
    public int[] getGrades(){return graids;}
    public double getAverege(){
        return (Arrays.stream(graids).sum())/graids.length;
    }

    public void setCourse(int course){
        this.course=course;
    }

    public void getInformation(){
        System.out.println(
            "Student "+ name+
            " from group "+ group+
            " and course "+ course+
            " has following grades: "+ Arrays.toString(graids)
        );
    }

    public static void removePerson(Set<Student> person){
        Set<Student> toRemove = new HashSet<>();
        for (Student arr:person){
            if(arr.getAverege()<3)toRemove.add(arr);
        }
        person.removeAll(toRemove);
    }

    public static void movePerson(Set<Student> person){
        for (Student arr:person){
            if(arr.getAverege()>=3)arr.setCourse(arr.getCourse()+1);
        }
    }

    public static void printStudents(Set<Student> person, int course){
        System.out.printf("Students stadying on the course %d => %n",course);
        for (Student arr:person) {
            if (arr.getCourse() == course) System.out.println(arr.getName());
        }
    }
}

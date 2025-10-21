package Lesson_6;
import java.util.*;

// -- Задание 2

// Написать простой класс Телефонный Справочник, который хранит в себе список фамилий и телефонных номеров. 
//В этот телефонный справочник с помощью метода add() можно добавлять записи, а с помощью метода get() искать номер телефона по фамилии. 
//Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),тогда при запросе такой фамилии должны выводиться все телефоны.


import java.util.ArrayList;
import java.util.HashMap;

public class Directory {
    String lastName; String[] phone_number;
    public Directory(){}

    Map<String, List<String>> phoneBook = new HashMap<>();

    public void addNew(String lastName, String... phone_number){
        List<String> phoneNumber;
        if (!phoneBook.containsKey(lastName)){
            phoneNumber=new ArrayList<>();
            phoneBook.put(lastName, phoneNumber);
        } else {phoneNumber=phoneBook.get(lastName);}

        if (phone_number != null ){
            for (String arr : phone_number) {
                phoneNumber.add(arr);
            }
        }
    }
    public void getSearch(String lastName){
        if(phoneBook.containsKey(lastName)) {
            List<String> phoneNumber = phoneBook.get(lastName);
            System.out.println( "Number phone of "+lastName+" => "+phoneBook.get(lastName));
        } else {
            System.out.println("This lastname isn't exist <= "+lastName);
        }
    }


}

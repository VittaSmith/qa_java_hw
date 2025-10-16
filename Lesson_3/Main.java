import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args){
        
        // 2. Создать массив из 5 товаров. 

        Product[] productsArray = new Product[5];
        productsArray[0] = new Product(
            "Samsung S25 Ultra", 
            LocalDate.of(2025,02,01),
            "Samsung Corp.", 
            "Korea", 
             5599.00, 
             true
             );
        productsArray[1] = new Product(
            "Sony PlayStation 6", 
            LocalDate.of(2025, 3, 20),
            "Sony Interactive Entertainment", 
            "Japan", 
            899.00, 
            true
            );
        productsArray[2] = new Product(
            "Apple MacBook Pro 16\"", 
            LocalDate.of(2024, 11, 10),
            "Apple Inc.", 
            "USA", 
            7999.99, 
            true
            );
        productsArray[3] = new Product(
            "Xiaomi Smart Vacuum X20", 
            LocalDate.of(2024, 9, 5),
            "Xiaomi", 
            "China", 
            649.50, 
            true
        );
        productsArray[4] = new Product(
            "Dyson Supersonic Hair Dryer", 
            LocalDate.of(2023, 12, 15),
            "Dyson Ltd.", 
            "UK", 
            499.99, 
            true
            );

        for (Product product : productsArray){
            product.getInfo();
            System.out.println();
        }


        Park park = new Park();
        Park.ThingsPark[] attractions = new Park.ThingsPark[1];
        attractions[0] = park.new ThingsPark(
            "Ferris Wheel",
            LocalDate.of(2025, 8, 1),
            30,
            true,
            LocalTime.of(10, 0),
            LocalTime.of(22, 0),
            5.0
        );
        for(Park.ThingsPark arr : attractions){
            arr.getInfoTree();
        }
        
        
        
    }
}

/* 1. Создать класс "Товар" с полями: название, дата производства, производитель, страна происхождения, цена, состояние бронирования покупателем. 
Конструктор класса должен заполнять эти поля при создании объекта. 
Внутри класса «Товар» написать метод, который выводит информацию об объекте в консоль.*/

class Product {
    String name;
    LocalDate date;
    String producer, country;
    double price;
    Boolean state;
    Product(){}
    Product(String name, LocalDate date, String producer, String country, double price, Boolean state){
        this.name = name;
        this.date = date;
        this.producer = producer;
        this.country = country;
        this.price = price;
        this.state = state;
    }
    public void getInfo(){
        System.out.println(
            "Name product: " + name + 
            "\nDate of production: " + date +
            "\nProducer is " + producer + 
            "\nCountry is " + country + 
            "\nPrice: " + price +
            "\nStatus: " + state
        );
    }
}


// 3. Создать класс Park с внутренним классом, с помощью объектов которого можно хранить информацию об аттракционах, 
//времени их работы и стоимости. 

class Park{
    Boolean tiket=true;
    
    class ThingsPark{
        String name;
        LocalDate dateOfLastCheck;
        int numberOfPerson;
        Boolean workToday;
        LocalTime openTime;
        LocalTime closeTime;
        double price;

        ThingsPark(){}
        ThingsPark(String name,LocalDate dateOfLastCheck,int numberOfPerson,Boolean workToday,LocalTime openTime,LocalTime closeTime,double price){
            this.name = name;
            this.dateOfLastCheck = dateOfLastCheck;
            this.numberOfPerson = numberOfPerson;
            this.workToday = workToday;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.price = price;
        }

        void getInfoTree() {
            System.out.println(
                "Attraction: " + name +
                "\nLast safety check: " + dateOfLastCheck +
                "\nCapacity: " + numberOfPerson +
                "\nWorking today: " + (workToday ? "Yes" : "No") +
                "\nOpen: " + openTime + " — " + closeTime +
                "\nTicket price: " + price 
            );
    }
}
}

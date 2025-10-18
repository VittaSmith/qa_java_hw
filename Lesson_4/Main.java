package Lesson_4;
import java.util.*;


public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        while (true){
            System.out.println("Enter the number of task >>");
            int task = Integer.valueOf(sc.nextInt());
        
            switch(task){
                case 1: {
                    //Задание номер 1
                    System.out.println("Enter the number of cats:");
                    int catNumber = sc.nextInt();
                    System.out.println("Enter the number of dogs:");
                    int dogNumber = sc.nextInt();
                    System.out.println("Enter the number of food for cats:");
                    int foodCount = sc.nextInt();

                    Plate plate = new Plate(foodCount);

                    Cat[] cats = new Cat[catNumber];
                    for(int i = 0;i<catNumber;i++) {
                        cats[i] = new Cat();
                        cats[i].run((i+1)*10);
                        cats[i].swim(i+1);
                        cats[i].askToEat(plate, 10);
                        cats[i].info();
                    }

                    Animal[] dogs = new Dog[dogNumber];
                    for(int i = 0;i<dogNumber;i++) {
                        dogs[i] = new Dog();
                        dogs[i].run((i+1)*20);
                        dogs[i].swim((i+1)*3);
                    }
                    
                    System.out.println("\n\t* Count of amimals *");
                    System.out.println("Number of cates: " + Cat.catCount);
                    System.out.println("Number of doges: " + Dog.dogCount);
                    System.out.println("Total animals: " + Animal.animalCount);

                    break;
                }

                case 2:{
                    //Задание номер 2
                    Figure[] figures = {
                        new Circl(6,"Red","Black"),
                        new Rectangle(2,4,"Blue","Black"),
                        new Triangle(3,4,5,"Orange","Black")
                    };
                    for(Figure f:figures) f.printInfo();
                    break;
                }

                case 0: {
                    System.out.println("Exiting program. Goodbye!"); 
                    sc.close(); 
                    return;
                }

                default: System.out.println("No such task.");

            }
        }
        
    }
}

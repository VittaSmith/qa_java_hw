package Lesson_4;
/* 
----Задание 1
1.1)
Создать классы Собака и Кот с наследованием от класса Животное. 
Все животные могут бежать и плыть. В качестве параметра каждому методу передается длина препятствия. 
Результатом выполнения действия будет печать в консоль. (Например, dogBobik.run(150); -> 'Бобик пробежал 150 м.'); 
У каждого животного есть ограничения на действия (бег: кот 200 м., собака 500 м.; плавание: кот не умеет плавать, собака 10 м.). 
Добавить подсчет созданных котов, собак и животных.*/ 

/* 
1.2)
Расширить задачу, добавив для котов возможность кушать из миски, выполнив следующие пункты: 
    -Сделать так, чтобы в миске с едой не могло получиться отрицательного количества еды (например, в миске 10 еды, а кот пытается покушать 15-20). 
    -Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны). Если коту удалось покушать (хватило еды), сытость = true. 
    -Считаем, что если коту мало еды в миске, то он её просто не трогает, то есть не может быть наполовину сыт (это сделано для упрощения логики программы).

    -Создать массив котов и миску с едой, попросить всех котов покушать из этой миски и потом вывести информацию о сытости котов в консоль. 
    -Добавить метод, с помощью которого можно было бы добавлять еду в миску */

abstract public class Animal {
    int meter;
    boolean hungry;
    static int animalCount=0;
    public Animal(){
        animalCount++;
    }
    abstract public void run(int meter);
    abstract public void swim(int meter);
}

class Plate {
    int food;
    public Plate(int food){
        if (food<0) food=0;
        this.food=food;
    }
    public boolean lessFood(int count){
        if (count<=food) {
            food-=count; return true;
        }
        else return false;

    }
    public void addFood(int count){
        if (count>0) food+=count;
    }
    public void info(){
        System.out.printf("Right now in this plate %d food%n", food);
    }
}

class Cat extends Animal{
    int meter;
    static int catCount=0;
    private boolean wellFed = false;

    public Cat(){
        catCount++;
        System.out.println("\nInformation about your CAT "+catCount);
    }
    @Override
    public void run(int meter){
        if(meter<200) System.out.printf(" - Your cat number %d was able to run %d meters\n", catCount ,meter);
        else System.out.println(" - Cat can run only 200 meters!");
    };
    @Override
    public void swim(int meter){
        System.out.println(" - Oh no! Cat cannot swiming...");
    };
    public void askToEat(Plate plate, int countEat){
        if (wellFed) {
            System.out.println(" - Cat has already eaten and don't want to eat...");
            return;
        }
        boolean check = plate.lessFood(countEat);
        if (check) {
            System.out.println(" - Cat just ate " + countEat + (countEat>1?" servings":" serving"));
            wellFed = true;
        }
        else {
            System.out.println(" - Looks like there wasn't enough food for cat...");
        }
    }
    public void info(){
        System.out.println("Cat number " + catCount + " is "+(wellFed?"well-fed":"hungry"));
    }
    
}

class Dog extends Animal{
    int meter;
    boolean hungry = true;
    static int dogCount=0;

    public Dog(){
        dogCount++;
        System.out.println("\nInformation about your DOG "+dogCount);
    }
    @Override
    public void run(int meter){
        if(meter<500)  System.out.printf(" - Your dog was able to run %d meters\n", meter);
        else System.out.println("Your dog could only run 500 meters\n");
    };
    @Override
    public void swim(int meter){
        if(meter<10) System.out.printf(" - Your dog can swim almost %d meters\n", meter);
        else System.out.println(" - Your dog number "+ dogCount + " could only swim 10 meters!\n");
    };
}
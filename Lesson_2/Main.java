import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int task;
        
        while (true){
            System.out.print("Select a task between 1 and 14: ");
            task = sc.nextInt();
            sc.nextLine();

            switch(task){
                case 1: printThreeWords(); break;
                case 2: System.out.println("Enter numbers (a and b): "); checkSumSign(sc.nextInt(), sc.nextInt()); break;
                case 3: System.out.println("Enter value: "); printColor(sc.nextInt()); break;
                case 4: System.out.println("Enter numbers (a and b): "); compareNumbers(sc.nextInt(), sc.nextInt()); break;
                case 5: System.out.println(checkIntWithin(sc)); break;
                case 6: System.out.print("Enter an integer: "); checkPositiv(sc.nextInt()); break;
                case 7: System.out.print("Enter an integer: "); System.out.println(checkPositivBool(sc.nextInt())); break;
                case 8: System.out.println("Enter a string and count: ");  stringInteger(sc.nextLine(), sc.nextInt()); break;
                case 9: System.out.print("Enter year: "); System.out.println(checkYear(Integer.parseInt(sc.nextLine()))); break;
                case 10: arrayChange(); break;
                case 11: array(); break;
                case 12: arraySixToDouble(); break;
                case 13: System.out.print("Matrix size: "); arraySquare(sc.nextInt()); break;
                case 14: System.out.print("len and initialValue: "); arrayLenInitial(sc.nextInt(), sc.nextInt()); break;
                case 0: System.out.println("Exiting program. Goodbye!"); sc.close(); return;
                default: System.out.println("No such task.");
            }
            
        }
    }

    //1. Создайте метод printThreeWords(), который при вызове должен отпечатать в столбец три слова: Orange, Banana, Apple

    public static void printThreeWords(){
        String[] arrWords = {"Orange", "Banana", "Apple"};
        for (String arr : arrWords){
            System.out.println(arr);
        }
    }

    /*2. Создайте метод checkSumSign(), 
    в теле которого объявите две int переменные a и b, и инициализируйте их любыми значениями, которыми захотите. 
    Далее метод должен просуммировать эти переменные, и если их сумма больше или равна 0, 
    то вывести в консоль сообщение “Сумма положительная”, в противном случае - “Сумма отрицательная”;*/

    public static void checkSumSign(int a, int b){
        String result = (a+b>0)? "Сумма положительная" : "Сумма отрицательная";
        System.out.println(result);
    }

    /*3. Создайте метод printColor() в теле которого задайте int переменную value и инициализируйте ее любым значением. 
    Если value меньше 0 (0 включительно), то в консоль метод должен вывести сообщение “Красный”, 
    если лежит в пределах от 0 (0 исключительно) до 100 (100 включительно), то “Желтый”, если больше 100 (100 исключительно) - “Зеленый”;*/

    public static void printColor(int value){
        String nameColore = "";
        if (value < 0) nameColore="Красный";
        else if (value >= 0 && value<=100) nameColore="Желтый";
        else nameColore="Зеленый";
        System.out.println(nameColore);
    }

    /*4. Создайте метод compareNumbers(), в теле которого объявите две int переменные a и b, и инициализируйте их любыми значениями, 
    которыми захотите. Если a больше или равно b, то необходимо вывести в консоль сообщение “a >= b”, в противном случае “a < b”;*/

    public static void compareNumbers(int a, int b){
        String test = a>=b? "a >= b":"a < b";
        System.out.println(test);
    }

    /*5. Напишите метод, принимающий на вход два целых числа и проверяющий, что их сумма лежит в пределах от 10 до 20 (включительно), 
    если да – вернуть true, в противном случае – false.*/

    public static boolean checkIntWithin(Scanner sc){
        System.out.println("Enter two integers (a and b):");
        int a = Math.abs(sc.nextInt()), b = Math.abs(sc.nextInt());
        if ((a>=10 && a<=20)&&(b>=10 && b<=20)){
            return true;
        } else {
            return false;
        }

    } 

    /*6. Напишите метод, которому в качестве параметра передается целое число, 
    метод должен напечатать в консоль, положительное ли число передали или отрицательное. Замечание: ноль считаем положительным числом.*/

    public static void checkPositiv(int n){
        if (n>=0) System.out.println("Positive");
        else System.out.println("Negative");
    }

    /*7. Напишите метод, которому в качестве параметра передается целое число. Метод должен вернуть true, 
    если число отрицательное, и вернуть false если положительное.
    Замечание: ноль считаем положительным числом.*/

    public static boolean checkPositivBool(int n){
        if (n>=0) return false;
        else return true;
    }

    /*8. Напишите метод, которому в качестве аргументов передается строка и число, 
    метод должен отпечатать в консоль указанную строку, указанное количество раз;*/

    public static void stringInteger(String s, int n){
        for (int i=0;i<n;i++){
            System.out.print(s);
        }
        System.out.println();
    }

    /*9. Напишите метод, который определяет, является ли год високосным, и возвращает boolean (високосный - true, 
    не високосный - false). Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.*/

    public static boolean checkYear(int year){
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /*10. Задать целочисленный массив, состоящий из элементов 0 и 1. Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ]. 
    С помощью цикла и условия заменить 0 на 1, 1 на 0;*/

    public static void arrayChange(){
        int[] arr = {0,0,1,1,0,1,0,1};
        for (int mas : arr){
            if (mas==1) mas=0;
            else mas = 1;
            System.out.print(mas + " ");
        }
        System.out.println();
    }

    //11. Задать пустой целочисленный массив длиной 100. С помощью цикла заполнить его значениями 1 2 3 4 5 6 7 8 ... 100;

    public static void array(){
        int n = 100;
        int[] arr = new int[n];
        for(int i=0;i<n;i++) arr[i]=i+1;
        System.out.println(Arrays.toString(arr));
        
    }

    //12. Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ] пройти по нему циклом, и числа меньшие 6 умножить на 2;

    public static void arraySixToDouble(){
        int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for(int mas : arr){
            if (mas<6) mas*=2;
            System.out.print(mas + " ");
        }
        System.out.println();
    }

    /*13. Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое), 
    и с помощью цикла(-ов) заполнить его диагональные элементы единицами (можно только одну из диагоналей, если обе сложно). 
    Определить элементы одной из диагоналей можно по следующему принципу: индексы таких элементов равны, 
    то есть [0][0], [1][1], [2][2], ..., [n][n];*/

    public static void arraySquare(int size){
        int[][] arr = new int[size][size];
        for(int i=0;i<size;i++) arr[i][i]=1;
        System.out.println(Arrays.deepToString(arr));
    }

    /*14. Написать метод, принимающий на вход два аргумента: len и initialValue, 
    и возвращающий одномерный массив типа int длиной len, каждая ячейка которого равна initialValue.*/

    public static int[] arrayLenInitial(int len, int initialValue){
        int[] arr = new int[len];
        for(int i=0;i<len;i++) arr[i]=initialValue;
        System.out.println(Arrays.toString(arr));
        return arr;
    }
}


package Lesson_5;
import java.util.*;

// 1) 1.1 - Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4. 
// При подаче массива другого размера необходимо бросить исключение MyArraySizeException. 
// 1.2 - Далее метод должен пройтись по всем элементам массива, преобразовать в int и просуммировать. 
// 1.3 - Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа), 
//должно быть брошено исключение MyArrayDataException с детализацией, в какой именно ячейке лежат неверные данные. 
// 1.4 - В методе main() вызвать полученный метод, обработать возможные исключения MyArraySizeException и 
//MyArrayDataException и вывести результат расчета.    
// 2) Напишите код для генерации и поимки ArrayIndexOutOfBoundsException.

class MyArraySizeException extends Exception{
    public MyArraySizeException(String message){
        super(message);
    }
}
class MyArrayDataException extends Exception{
    public MyArrayDataException(int i, int j, String message){
        super("Error in array cells: ["+i+"] ["+j+"] " + message + " ... etc");
    }
}

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter two numbers for two-dimensional array's length >>");
        int num = Integer.parseInt(sc.nextLine());
        String[][] arr = new String[num][num];
        for(int i=0;i<num;i++){
            for(int j=0;j<num;j++){ arr[i][j]=sc.nextLine(); }
        }
        System.out.println("You entered: "+Arrays.deepToString(arr));
        System.out.println();
        // -- 1)
        try {
            int result = method(arr);
            System.out.println("- Result of the sum: "+result);

        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println("Error! Look at your mistake - " + e.getMessage());
        } 
        System.out.println();
        // -- 2)
        try {
            int[][] errorArray = {{1,2,3,4},{3,6,7,8}};
            System.out.println(errorArray[3][5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Catch the error named <<ArrayIndexOutOfBoundsException>> - "+ e.getMessage());
        }
        System.out.println();
    }
    
    public static int method(String[][] array) throws MyArraySizeException, MyArrayDataException {
        if (array.length!=4) throw new MyArraySizeException("Your array is't correct size (4x4)");

        int sum=0;
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length;j++){
                try{
                    int q = Integer.parseInt(array[i][j]); 
                    sum+=q;
                } catch (Exception e){
                    throw new MyArrayDataException(i,j,array[i][j]);
                }   
            }
        }
        return sum;
    }
}


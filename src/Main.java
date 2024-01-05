
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите колличество каннибалов:");
        int N = in.nextInt();
        System.out.println("Введите колличество миссионеров: ");
        int N_1 = in.nextInt();
        System.out.println("Введите максимальное колличество людей в лодке:");
        int M = in.nextInt();
        State start= new State('l',N,M,N_1);
        in.close();
        long startTimer = System.currentTimeMillis();
        State finalState = aStar.run(start);
        long end = System.currentTimeMillis();
        if(finalState == null) System.out.println("Решений нет.");
        else {
            State temp = finalState;
            ArrayList<State> tree = new ArrayList<>();
            tree.add(finalState);
            while(temp.getFather() != null) {
                tree.add(temp.getFather());
                temp = temp.getFather();
            }
            Collections.reverse(tree);
            for(State node: tree) System.out.println(node);
            System.out.println();
            System.out.println("Время: " + (double)(end - startTimer) / 1000 + " сек.");
        }
    }

}

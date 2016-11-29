package programme;

public class Recursive {
    public static int decrement(int n){
        if(n > 0){
            return decrement(n-1);
        }
        return n;
    }
}

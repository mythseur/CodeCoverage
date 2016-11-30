package programme;

import java.util.ArrayList;

public class For {

    private int n;

    public For(int number) {
        this.n = number;
    }

    public ArrayList<Integer> buildList(){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i=0; i<n; i++){
            res.add(i);
        }
        return res;
    }

    public ArrayList<Integer> buildReverseList(){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i=n-1; i>=0; i--){
            res.add(i);
        }
        return res;
    }
}

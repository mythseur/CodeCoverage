package programme;

public class If {
    public java.lang.String run(int n) {
        if (n == 1)
        {
            java.lang.System.out.println("Je suis 1 !");
            return "Je suis 1 !";
        } else if (n == 2) {
            java.lang.System.out.println("Je suis 2 !");
            return "Je suis 2 !";
        } else {
            java.lang.System.out.println("Je suis autre chose !");
            return "Je suis autre chose !";
        }
    }
}
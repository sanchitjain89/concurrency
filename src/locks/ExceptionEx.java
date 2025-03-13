package locks;

public class ExceptionEx {

    public static void main(String[] args) {
        System.out.println(5/0);
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("This will run??");
        }
    }
}

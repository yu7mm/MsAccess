public class Test { 
    static {
        System.out.println("1. static block executed");
    }
 
    {
        System.out.println("3. block executed");
    }
 
    public Test() {
        System.out.println("4. constructor executed");
    }
 
    public void fun() {
        System.out.println("5. fun executed");
    }
 
    public static void main(String args[ ])  {
        System.out.println("2. main started");
        new Test().fun();
    } 
}

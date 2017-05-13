package lambda;

public class DefaultMethod
{

    public static void main( String[] args )
    {
        DefaultMethod D = new DefaultMethod();
        C c = D.new C();
        c.fun();

        C2 c2 = D.new C2();
        c2.fun();

        C2 c22 = new DefaultMethod().new C2();
    }

    class C extends F1 implements F2
    {
        @Override
        public void fun()
        {
            super.fun();//F1.super.fun();
            // complie error F2.super.fun();
        }
    }

    class C2 implements F2, F3
    {
        @Override
        public void fun()
        {
            F3.super.fun();
        }
    }

    class F1 implements F2
    {
        public void fun()
        {
            System.out.println( "F1" );
        }
    }

    interface F2
    {
        default void fun()
        {
            System.out.println( "F2" );
        }
    }

    interface F3
    {
        default void fun()
        {
            System.out.println( "F3" );
        }
    }
}

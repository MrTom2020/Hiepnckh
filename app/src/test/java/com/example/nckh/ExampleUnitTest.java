package com.example.nckh;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect()  throws Exception
    {
        MyClass myClass = new MyClass();
        int result = myClass.add(-200,7);
        int kq = 5;
        assertEquals(kq, result);
    }

}
class MyClass
{
    public int add(int a,int b)
    {
        return a + b;
    }
}

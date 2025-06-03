package rough;

import org.testng.annotations.Test;

public class BasicTestNGTest {
    
    @Test
    public void testPrintMessage() {
        System.out.println("Hello, TestNG!");
    }
    @Test
    public void testMathOperation() {
        int result = 5 + 3;
        assert result == 8 : "Math test failed!";
        System.out.println("Math test passed!");
    }

}

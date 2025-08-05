package testNG.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class MyAnnotationTransformer implements IAnnotationTransformer
{
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) 
    {
        if (testMethod.getName().equals("methodB")) {
            annotation.setEnabled(false); // Disable methodB
        }
        
        if (testMethod.getName().equals("methodC")) {
            annotation.setPriority(1); // Set priority for methodC
        }
        
        if (testMethod.getName().equals("methodD")) {
            annotation.setDescription("This is method D"); // Set description for methodD
        }
        
        // You can add more transformations as needed       
    }
}
`
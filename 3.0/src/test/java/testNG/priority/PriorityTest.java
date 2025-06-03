package testNG.priority;

import org.testng.annotations.Test;

public class PriorityTest 
{
    @Test(priority = 0)
    public void startTheCar()//by default runs with method name alphabetical order
    {
        System.out.println("Engine started");
    }

    @Test(priority = 1)
    public void putFirstGear()
    {
        System.out.println("First gear");
    }

    @Test(priority = 2)
    public void putSecondGear()
    {
        System.out.println("Second gear");
    }

    @Test(priority = 3)
    public void putThirdGear()
    {
        System.out.println("Third gear");
    }

    @Test(priority = 5)
    public void putFourthGear()
    {
        System.out.println("Fourth gear");
    }

    @Test(priority = 7)
    public void highSpeedBrake()
    {
        System.out.println("High Speed Brake");
    }
}

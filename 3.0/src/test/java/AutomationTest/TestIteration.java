package AutomationTest;

public class TestIteration 
{
    public static void main(String[] args) throws Exception 
    {
        int x=0;
        try 
        {
            SalesOrderCreate soc=new SalesOrderCreate();
            for(int i=1;i<=30;i++)
            {
                soc.orderCreation();
                x++;
            }
            System.out.println("Completed "+x+" iterations");
        } catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println("Failed at "+x+" iteration");
        }
      
    }
    
}

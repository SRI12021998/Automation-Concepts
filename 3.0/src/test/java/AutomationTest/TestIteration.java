package AutomationTest;

public class TestIteration 
{
    public static void main(String[] args) throws Exception 
    {
        int x=0;
        try 
        {
            for(int i=1;i<=30;i++)
            {
                SalesOrderCreate soc=new SalesOrderCreate();
                soc.orderCreation();
                x++;
            }
            System.out.println("Completed "+x+" iterations");
        } catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println("Failed at "+x+" iteration");
        }
        // finally
        // {
        //     //close the browser
		//     soc.closeBrowser();
        // }
        
    }
    
}

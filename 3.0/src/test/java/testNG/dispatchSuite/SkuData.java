package testNG.dispatchSuite;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class SkuData 
    {

    public HashMap <String,Integer> skuDetails()
    {
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        Workbook wb=null;
        FileInputStream fis=null;
        HashMap <String,Integer> sku=new HashMap<>();
        File file = new File("src/test/java/data/DataSheet.xlsx");
        try 
        {
            fis=new FileInputStream(file);
            wb=WorkbookFactory.create(fis);
            Sheet sheet=wb.getSheet("SKU_Data");
            for(int i=1;i<=sheet.getLastRowNum();i++)
            {
                Row row=sheet.getRow(i);
                if(row==null)
                {
                    continue;
                }
                Cell product=row.getCell(0);
                Cell quantity=row.getCell(1);
                
                switch (product.getCellType())
                {
                    case STRING : sku.put(product.getStringCellValue(), (int)quantity.getNumericCellValue());
                    break;

                    case NUMERIC : sku.put(String.valueOf((long)product.getNumericCellValue()), (int)quantity.getNumericCellValue());
                    break;
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                wb.close();
                fis.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        return sku;
    }
}

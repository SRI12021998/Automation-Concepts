package testNG.dispatchSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.print.DocFlavor.STRING;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;

public class SkuData 
    {
    @Test
    public HashMap<String, Integer> skuDetails()
    {
        Workbook wb=null;
        FileInputStream fis=null;
        HashMap <String,Integer> sku=new HashMap<>();
        File file=new File("D:\\VS Local repo\\Automation-Concepts\\3.0\\src\\test\\java\\data\\DataSheet.xlsx");
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
                sku.put(product.getStringCellValue(), (int)quantity.getNumericCellValue());
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

package testNG.dispatchSuite;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class SellerRetailerData 
{
    public String seller;
    public String retailer;
    public void sellerRetailerDetails()
    {
        File file=new File("D:\\VS Local repo\\Automation-Concepts\\3.0\\src\\test\\java\\data\\DataSheet.xlsx");
        try 
        {
            FileInputStream fis=new FileInputStream(file);
            Workbook wb=WorkbookFactory.create(fis);
            Sheet sheet=wb.getSheet("SellerRetailer_Data");
            Row row=sheet.getRow(1);
            seller=row.getCell(0).getStringCellValue();
            retailer=row.getCell(1).getStringCellValue();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}

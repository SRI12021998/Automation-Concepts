package excelreadingwriting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SheetWriting 
{
    public static void main(String[] args) 
    {
        File file = new File("D:\\VS Local repo\\Automation-Concepts\\3.0\\src\\test\\java\\data\\DataSheet2.xlsx");
        
        try 
        {
            Workbook wb;
            FileInputStream fis;
            if(file.exists()==false)
            {
                file.createNewFile();
                wb=new XSSFWorkbook();
            }
            else
            {
                fis=new FileInputStream(file);
                wb=WorkbookFactory.create(file);
                fis.close();
            }
            Sheet sheet=wb.createSheet("New_Copy");
            Row row=sheet.createRow(0);
            Cell cell=row.createCell(0);
            cell.setCellValue("Hi");
            FileOutputStream fos=new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }
}

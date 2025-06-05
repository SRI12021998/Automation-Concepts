package testNG.dispatchSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;
import org.apache.poi.ss.usermodel.CellType;


public class Credentials 
{
    public static String login;
    public static String pwd;
   
    public void credentialsFetch(String user) 
    {
        File file=new File("D:\\VS Local repo\\Automation-Concepts\\3.0\\src\\test\\java\\data\\DataSheet.xlsx");
        int targetCol=0;
        int targetRow=0;
        Workbook wb=null;
        FileInputStream fis=null;
        try 
        {
            fis=new FileInputStream(file);
            wb=WorkbookFactory.create(fis);
            Sheet sheet=wb.getSheet("Credentials");
            int rowNum=sheet.getLastRowNum();
            for(int i=0;i<=rowNum;i++)
            {
                Row row=sheet.getRow(i);
                if(row==null)
                {
                    continue;
                }
                int cellNum=row.getLastCellNum();
                for(int j=0;j<=cellNum;j++)
                {
                    Cell cell=row.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    CellType ct=cell.getCellType();
                    if(ct == CellType.STRING)
                    {
                        if(cell.getStringCellValue().equalsIgnoreCase("Login"))
                        {
                             targetCol=j;
                        }
                        else if(cell.getStringCellValue().equalsIgnoreCase(user))
                        {
                            targetRow=i;
                        }
                    }
                }
            }
            login=String.valueOf((long)sheet.getRow(targetRow).getCell(targetCol).getNumericCellValue());
            pwd=String.valueOf((long)sheet.getRow(targetRow).getCell(targetCol+1).getNumericCellValue());

        } catch (EncryptedDocumentException | IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                wb.close();
                fis.close();
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}

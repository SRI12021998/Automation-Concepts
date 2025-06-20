package excelreadingwriting;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SheetReading
{
    public static void main(String[] args) 
    { 
        FileInputStream fis=null;
        Workbook wb=null;
        try 
        {
            File file=new File("3.0/src/test/java/data/DataSheet.xlsx");
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
                int cellNum=sheet.getRow(0).getLastCellNum();
                    for(int j=0;j<=cellNum;j++)
                    {
                        Cell cell=row.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        switch (sheet.getRow(i).getCell(j).getCellType()) 
                        {
                            case STRING: System.out.print(sheet.getRow(i).getCell(j).getStringCellValue()+" | ");
                                
                                break;
                        
                            case NUMERIC: System.out.print((long)sheet.getRow(i).getCell(j).getNumericCellValue()+" | ");
                                
                                break;

                            case BOOLEAN: System.out.print(sheet.getRow(i).getCell(j).getBooleanCellValue()+" | ");

                                break;
                        }
                        
                    }
                System.out.println();
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
                if(wb!=null)
                {
                    wb.close();
                }
                if (fis!=null) 
                {
                    fis.close();
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        
        }
    }
}

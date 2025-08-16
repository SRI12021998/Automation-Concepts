package excelreadingwriting;


import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SheetReading
{
    @Test
    public void sheetRead() 
    { 
        FileInputStream fis=null;
        Workbook wb=null;
        try 
        {
             InputStream input=Thread.currentThread().getContextClassLoader()
                                       .getResourceAsStream("DataSheet.xlsx");
            // File file=new File("3.0/src/test/resources/DataSheet.xlsx");
            // fis=new FileInputStream(file);
            wb=WorkbookFactory.create(input);
            Sheet sheet=wb.getSheet("Credentials");
            int rowNum=sheet.getLastRowNum();
            for(int i=0;i<=rowNum;i++)
            {
                Row row=sheet.getRow(i);
                if(row==null)
                {
                    continue;// Skip empty rows
                }
                int cellNum=sheet.getRow(0).getLastCellNum();
                    for(int j=0;j<=cellNum;j++)
                    {
                        Cell cell=row.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        switch (cell.getCellType()) 
                        {
                            case STRING: System.out.print(cell.getStringCellValue()+" | ");
                                
                                break;
                        
                            case NUMERIC: System.out.print((long)cell.getNumericCellValue()+" | ");
                                
                                break;

                            case BOOLEAN: System.out.print(cell.getBooleanCellValue()+" | ");

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

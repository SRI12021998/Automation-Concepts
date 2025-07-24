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

public class SheetReadWrite 
{
    public static void main(String[] args) 
    {
        String input="3.0/src/test/resources/DataSheet.xlsx";
        String output="3.0/src/test/resources/DataSheet2.xlsx";
        sheetReadWrite( input,  output);
    }
    public static void sheetReadWrite(String input, String output)
    {
        File ifile=new File(input);
        File ofile=new File(output);
        Workbook wbi;
        Workbook wbo;
        FileInputStream ifis;
        FileInputStream ofis;
        FileOutputStream ofos;

        if (ifile.exists())
        {
            try 
            {
                if(ofile.exists()==false)
                {
                    ofile.createNewFile();
                    wbo=new XSSFWorkbook();
                }
                else
                {
                    ofis=new FileInputStream(ofile);
                    wbo=WorkbookFactory.create(ofis);
                    ofis.close();
                }

                ifis=new FileInputStream(ifile);
                wbi=WorkbookFactory.create(ifis);
                ifis.close();
                int isheetCount=wbi.getNumberOfSheets();
                for(int i=0;i<isheetCount;i++)
                {
                    Sheet isheet=wbi.getSheetAt(i);
                    Sheet oSheet=wbo.createSheet(wbi.getSheetName(i));

                    int rowCount=isheet.getLastRowNum();
                        for(int j=0;j<rowCount;j++)
                        {
                            Row irow=isheet.getRow(j);
                            Row orow=oSheet.createRow(j);

                            if(irow==null)
                            {
                                continue;
                            }
                            int cellCount=irow.getLastCellNum();
                                for(int k=0;k<cellCount;k++)
                                {
                                    Cell icell=irow.getCell(k, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                    Cell ocell=orow.createCell(k);

                                    switch (icell.getCellType()) 
                                    {
                                        case STRING: ocell.setCellValue(icell.getStringCellValue());
                                            
                                        break;
                                    
                                        case NUMERIC:ocell.setCellValue(icell.getNumericCellValue());
                                            
                                        break;
                                    }
                                }
                        }
                }
                ofos=new FileOutputStream(ofile);
                wbo.write(ofos);
                ofos.close();
                wbi.close();
                wbo.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
}

package AutomationTest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FiscalData 
{
	static Workbook wb=null;
	public static String [][] dataSet()
	{
		InputStream input=Thread.currentThread().getContextClassLoader().getResourceAsStream("FiscalReasonMaster.xlsx");
		try 
		{
			wb=WorkbookFactory.create(input);
		} 
		catch (EncryptedDocumentException|IOException e) {
			e.printStackTrace();
		} 
		Sheet sheet=wb.getSheet("ScriptData");
		int rowNum=sheet.getPhysicalNumberOfRows();
		int cellNum=sheet.getRow(0).getPhysicalNumberOfCells();
		String [][] master=new String[rowNum-1][cellNum];
		
		for (int i=1;i<rowNum;i++)
		{
			Row row=sheet.getRow(i);
			
			for (int j=0;j<cellNum;j++)
			{
				Cell cell=row.getCell(j);
				switch (cell.getCellType())
				{
					case STRING: master[i-1][j]=cell.getStringCellValue();
					break;
					case NUMERIC: master[i-1][j]=String.valueOf(cell.getNumericCellValue());
					break;
				}
			}
		}
		try {
			input.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return master;
	}
}

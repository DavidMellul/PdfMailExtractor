package XLS;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Utils.Utils;

public class XLSWriter {
	public XLSWriter() {
		
	}
	
	public String generateXLSData(ArrayList<String> listEmails) {
		//Classeur Excel et feuille du classeur qui contiendra les adresses mail
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet hs = wb.createSheet("Liste des adresses mails");
		
		
		for(int i = 0; i < listEmails.size(); i++) {
			hs.createRow(i);
			hs.getRow(i).createCell(0);
			hs.getRow(i).getCell(0).setCellValue(listEmails.get(i));
		}
		hs.autoSizeColumn(0);
		
		String fileName = Utils.getRandomFileName();
		File file = new File(fileName);
		file.getParentFile().mkdirs();
		
		FileOutputStream fOut = null;
		
		try {
			file.createNewFile();
			fOut = new FileOutputStream(fileName);
			wb.write(fOut);
			wb.close();
			fOut.close();
		}catch(Exception e) { 
            JOptionPane.showMessageDialog(null, "Impossible de générer le classeur Excel.", "PdfMailExtractor - Erreur lors de la génération", JOptionPane.ERROR_MESSAGE, new ImageIcon(XLSWriter.class.getResource("/Resources/logo.png")) );
            return null;
		}
		return fileName;
	}
}

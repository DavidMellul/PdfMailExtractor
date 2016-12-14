package PDF;

import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class PDFDownloader {
    public PDFDownloader() {

    }

    public File downloadPDF(URL address) {
        File pdfFile = new File(System.getenv("APPDATA")+File.separator+"PdfMailExtractor"+File.separator+address.toString().substring(address.toString().lastIndexOf('/')));
        try {
            FileUtils.copyURLToFile(address, pdfFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Impossible de r�cup�rer le fichier distant.", "PdfMailExtractor - Erreur lors du t�l�chargement", JOptionPane.ERROR_MESSAGE, new ImageIcon(PDFDownloader.class.getResource("/Resources/logo.png")) );
            e.printStackTrace();
        }
        return pdfFile;
    }
}

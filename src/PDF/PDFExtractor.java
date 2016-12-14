package PDF;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
import org.apache.pdfbox.util.PDFTextStripper;


public class PDFExtractor {
    public PDFExtractor() {

    }

    public ArrayList<String> extract(String fileURL) {
        ArrayList<String> listEmails = new ArrayList<String>();
        PDDocument pd;
        
        try {
            //Ouverture et chargement du PDF
            File input = new File(fileURL);
            pd = PDDocument.load(input);
            
            if(pd.isEncrypted()) {
            	StandardDecryptionMaterial dm = new StandardDecryptionMaterial(JOptionPane.showInputDialog("Le document est prot�g�, saisir son mot de passe"));
            	pd.openProtection(dm);
            }
            
            PDFTextStripper stripper = new PDFTextStripper();

            //Récupération du contenu du PDF sous forme de texte brut
            StringBuilder sb = new StringBuilder();
            sb.append(stripper.getText(pd));

            //Analyseur que l'on parcourt pour ne récupérer que les adresses mail
            Matcher m = Pattern.compile("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})").matcher(sb.toString());

            while (m.find()){
                if(listEmails.contains(m.group()) == false)
                    listEmails.add(m.group());
            }

            if (pd != null) {
                pd.close();
            }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Impossible de traiter le fichier PDF.", "PdfMailExtractor - Erreur lors de l'analysee", JOptionPane.ERROR_MESSAGE, new ImageIcon(PDFExtractor.class.getResource("/Resources/logo.png")) );
            }
            return listEmails;
        }


}

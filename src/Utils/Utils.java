package Utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Utils {
	public static Icon darken(Icon ic) {
		float scales[] = {0.5f,0.5f,0.5f,1f};
		BufferedImage bi = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = bi.createGraphics();
		ic.paintIcon(null, g, 0, 0);
		
		RescaleOp rsop = new RescaleOp(scales, new float[4], null);
		bi = rsop.filter(bi, null);
		ic = new ImageIcon(bi);
		return ic;
	}
	
	public static Icon brighten(Icon ic) {
		float scales[] = {2f,2f,2f,1f};
		BufferedImage bi = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = bi.createGraphics();
		ic.paintIcon(null, g, 0, 0);
		
		RescaleOp rsop = new RescaleOp(scales, new float[4], null);
		bi = rsop.filter(bi, null);
		ic = new ImageIcon(bi);
		return ic;
	}
	
	public static String getRandomFileName() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HHmmss");
		
		String userLocation = System.getProperty("user.home");
		String dir = userLocation+File.separator+"PdfMailExtractor";
		
		String fileName = "Rapport_"+sdf.format(d)+".xls";
		return dir+File.separator+fileName;
	}
}

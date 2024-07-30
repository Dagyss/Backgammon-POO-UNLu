package ar.edu.unlu.backgammon.vista.grafica;

import javax.swing.ImageIcon;

public class Images {
	
	private ImageIcon fichaX0Image = new ImageIcon();
    private ImageIcon[] grisXImages = new ImageIcon[13];
    private ImageIcon[] negraXImages = new ImageIcon[13];
    private ImageIcon[] dadoX = new ImageIcon[7];
    
    public Images() {
        loadImages();
    }

    private void loadImages() {
    	fichaX0Image = new ImageIcon("images/FichaX0.png");
        for (int i = 1; i <= 12; i++) {
            grisXImages[i] = new ImageIcon("images/FichaGrisX" + i + ".png");
            negraXImages[i] = new ImageIcon("images/FichaNegraX" + i + ".png");
            if (i<=6) {
            	dadoX[i] = new ImageIcon("images/Dado"+i+".png");
            }
        }
    }

    public ImageIcon getFichaX0Image() {
        return fichaX0Image; 
    }

    public ImageIcon getGrisXImage(int index) {
        if (index >= 1 && index <= 12) {
            return grisXImages[index];
        }
        return null;
    }

    public ImageIcon getNegraXImage(int index) {
        if (index >= 1 && index <= 12) {
            return negraXImages[index];
        }
        return null; 
    }
    
    public ImageIcon getDadoXImage(int index) {
    	if (index >= 1 && index <= 6) {
            return dadoX[index];
        }
        return null;
    }
    
}



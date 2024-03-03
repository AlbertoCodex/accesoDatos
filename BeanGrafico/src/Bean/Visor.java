package Bean;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Visor extends  JLabel{
    private File archivo = null;
    public Visor(){
        setBorder(BorderFactory.createEtchedBorder());
    }

    public void setImagen(String rutaImagen) {
        try{
            archivo = new File(rutaImagen);
            setIcon(new ImageIcon(ImageIO.read(archivo)));
        } catch (IOException e) {
            archivo = null;
            setIcon(null);
        }
    }

    public String getImagen() {
        if(archivo != null) {
            return archivo.getPath();
        }
        return null;
    }

    public Dimension getPreferredSize() {
        return new Dimension(600,400);
    }
}

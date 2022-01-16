import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;

public class Image{

    public int [][] pixelGrid;
    
    public Image(String pictureFile) throws IOException{
        try{
            BufferedImage image = ImageIO.read(new File(pictureFile));
            pixelGrid = getPixelGrid(image);
        } catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }
    }

    private int[][] getPixelGrid(BufferedImage image){
        
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixelGrid = new int [height] [width];
        for(int ii = 0; ii < width; ii++){
            for(int jj=0;jj<height;jj++){
                boolean pixel = (blackWhiteConvert(image.getRGB(ii, jj)));
                pixelGrid[jj][ii] = pixel ? 1:0;
            }
        }
        return pixelGrid;
    }

    private boolean blackWhiteConvert(int value){
        boolean black = false;
        Color num = new Color(value);
        int red = num.getRed();
        int green = num.getGreen();
        int blue = num.getBlue();
        if(red+green+blue < 255*3/2){
            black = true;
        }
        return black;
    }

}
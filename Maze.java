import java.io.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.nio.file.*;

public class Maze extends Image{

    private Integer [][] entrance = new Integer[2][2];
    private String ogMazeFile;
    private String filePath;

    private String solvedMazeFile;
    private String solvedFilePath;

    private static final String FILE_NAME_ADDITION = "Solved";
    private String fileExtensionType;

    private int rgb;
    
    public Maze(String mazeFile) throws IOException{
        super(mazeFile);
        Color myColor = new Color(255, 0, 0); // Color white
        rgb = myColor.getRGB();
        ogMazeFile = mazeFile;
        filePath = new File(mazeFile).getAbsolutePath();
        solvedMazeFile = solvedMazeNameCreate(mazeFile);
        solvedFilePath = new File(solvedMazeFile).getAbsolutePath();
        //saveNewMaze();
        findEntrance(pixelGrid);     
    }

    private void findEntrance(int[][] grid) throws IOException{
        int rowNum = grid.length;
        int colNum = grid[0].length;
        ArrayList<Integer[]> entranceList = new ArrayList<Integer[]>();

        for(int ii = 0; ii < grid.length; ii += rowNum - 1){
            for(int jj = 0;jj<grid[ii].length; jj++){
                if(grid[ii][jj] == 0){
                    addEntrance(ii, jj, entranceList);
                }
            }
        }

        for(int jj = 0; jj < grid[0].length; jj += colNum - 1){
            for(int ii = 0;ii<grid.length; ii++){
                if(grid[ii][jj] == 0){
                    addEntrance(ii, jj, entranceList);
                }
            }
        }

        if(entranceList.size() != 2){
            throw new IOException("There is not 2 entrances.");
        } 
        entrance[0] = entranceList.get(0);
        entrance[1] = entranceList.get(1);

    }

    private void addEntrance(int row, int col, ArrayList<Integer []> entranceList){
        Integer [] entranceCoord = new Integer [2];
        entranceCoord[0] = row;
        entranceCoord[1] = col;
        entranceList.add(entranceCoord);
    }

    public Integer[][] getEntrance(){ return entrance; }

    public void printEntrance(){
        for(int ii = 0; ii < entrance.length; ii++){
            System.out.println("Entrance " + ii + ": " + entrance[ii][0] + ", " + entrance[ii][1]);
        }
    }

    private String solvedMazeNameCreate(String ogFile){
        String newFile = null;
        int extensionPos = ogFile.indexOf(".");
        fileExtensionType = ogFile.substring(extensionPos);
        newFile = ogFile.substring(0, extensionPos) + FILE_NAME_ADDITION + fileExtensionType;
        return newFile;
    }


    public void colorPath(SolveMaze maze){
        try{
            BufferedImage image = ImageIO.read(new File(filePath));
            //image.setRGB(x, y, argb);
            ArrayList<MazeNode> solution = maze.getMazePath();
            for(int ii = 0; ii < solution.size()-1;ii++){
                MazeNode node1 = solution.get(ii);
                int x1 = node1.getX();
                int y1 = node1.getY();

                MazeNode node2 = solution.get(ii+1);
                int x2 = node2.getX();
                int y2 = node2.getY();

                if(x1 == x2){
                    if(y1 < y2){
                        for(int cc = y1; cc < y2+1; cc++){
                            image.setRGB(x1, cc, rgb);
                        }
                    } else{
                        for(int cc = y2; cc < y1+1;cc++){
                            image.setRGB(x1, cc, rgb);
                        }
                    }
                } else if(y1 == y2){
                    if(x1 < x2){
                        for(int cc = x1; cc < x2+1; cc++){ 
                            image.setRGB(cc, y1, rgb); 
                        }
                    } else{
                        for(int cc = x2; cc < x1+1;cc++){ 
                            image.setRGB(cc, y1, rgb); 
                        }
                    }
                }
            }
            ImageIO.write(image, "png", new File(solvedFilePath));
           
        } catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        } catch(IOException ioe){
            System.out.println("Oh dear, not again.");
        }
    }
}
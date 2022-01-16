import java.io.IOException;

public class MazeSolver{
    public static void main(String [] parms){
        String file = "Maze2.png";
        Maze maze1 = null;
        try{
            maze1 = new Maze(file);
            SolveMaze run = new SolveMaze(maze1);
            System.out.println(run.toString());
        } catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }

    }

    public static void printArray(int [][] arr){
        for(int ii = 0;ii<arr.length;ii++){
            for(int jj=0;jj<arr[ii].length;jj++){
                System.out.print(arr[ii][jj] + " ");
            }
            System.out.println();
        }
    }

}
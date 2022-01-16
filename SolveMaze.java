import java.io.IOException;
import java.util.ArrayList;

public class SolveMaze{
    
    private ArrayList<MazeNode> nodes = new ArrayList<MazeNode>();
    private ArrayList<MazeNode> path = new ArrayList<MazeNode>();
    private ArrayList<MazeNode> solution = new ArrayList<MazeNode>();
    private Maze maze;
    private Integer [][] entrance;
    private int [][] grid;
    private MazeNode end;
    private Boolean solved;

    public SolveMaze(Maze maze) throws IOException{
        this.maze = maze;
        solved = false;
        grid = maze.pixelGrid;
        entrance = maze.getEntrance();//Must add these as nodes
        entranceToNodeConvert(entrance, maze.pixelGrid);
        findNodes();
        pathingStart();
        maze.colorPath(this);
    }

    private void findNodes(){
        for(int ii = 1; ii < grid.length-1;ii++){
            for(int jj=1;jj<grid[ii].length-1;jj++){
                if(grid[ii][jj] == 0){
                    isNode(jj, ii, grid);
                }
            }
        }
    }

    /**
     * A node is defined as a coordinate in the maze that either changes the direction in the path, like a corner,
     * or multiple paths can be taken. Entrances are by nodes by default.
     */
    private void isNode(int x, int y, int [][] grid){
        int up = grid[y-1][x]; //Value of 1
        int down = grid[y+1][x]; //Value of 2
        int left = grid[y][x-1]; //Value of 4
        int right = grid[y][x+1]; //Value of 8

        int nodeValue = 0;
        /**
         * Potential nodes with a value of 0, 3, or 12 are not nodes.
         * Potential nodes with a value of 1, 2, 4, or 8 are deadend nodes.
         * Potential nodes with a value of 5, 6, 9, 10 are corner nodes. One path in, one path out.
         */

        if(up == 0){nodeValue += 1;}
        if(down == 0){nodeValue += 2;}
        if(left == 0){nodeValue += 4;}
        if(right == 0){nodeValue += 8;}

        if(nodeValue != 0 && nodeValue != 3 && nodeValue != 12){
            nodes.add(new MazeNode(x,y,nodeValue));
        }
    }

    private void entranceToNodeConvert(Integer [][] entrance, int [][] grid){
        for(int ii = 0; ii < entrance.length;ii++){
            int x = entrance[ii][1];
            int y =entrance[ii][0];
            int nodeValue = 0;
            if(x == 0){ nodeValue = 8; }
            else if(x == grid[0].length-1){ nodeValue = 4;}
            else if(y == 0){ nodeValue = 2;}
            else if(y == grid.length-1){ nodeValue = 1;}
            nodes.add(new MazeNode(x,y,nodeValue));
        }
    }

    private void pathingStart() throws IOException{
        int startX = entrance[0][1];
        int startY = entrance[0][0];
        MazeNode curr = nodeAtCoord(startX, startY);
        end = nodeAtCoord(entrance[1][1], entrance[1][0]);
        checkCoord(curr);
    }

    private void checkCoord(MazeNode node) throws IOException{
        int val = 0;
        int x = -1;
        int y = -1;
        if(node != null){
            path.add(node);
            if(node.getX() == end.getX() && node.getY() == end.getY()){
                foundEnd();
            } else {
                val = node.getNodeVal();
                x = node.getX();
                y = node.getY();

                if(val - 8 >= 0 && !solved){
                    val -= 8;
                    findRight(x, y);
                }
                if(val - 4 >= 0 && !solved){
                    val -= 4;
                    findLeft(x, y);
                }
                if(val -2 >= 0 && !solved){
                    val -= 2;
                    findDown(x, y);
                }
                if(val - 1 >= 0 && !solved){
                    val -= 1;
                    findUp(x, y);
                }
                if(!solved){
                    path.remove(node);
                }
            }

        }
        
    }

    private void findUp(int x,int y) throws IOException{
        int closeY = -1;
        MazeNode closeNode = null;
        for(MazeNode node: nodes){
            if(node.getX() == x && node.getY() < y && node.getY() > closeY){
                closeY = node.getY();
                closeNode = node;
            }
        }
        if(path.indexOf(closeNode) != -1){
            closeNode = null;
        }
        checkCoord(closeNode);
    }

    private void findDown(int x,int y) throws IOException{
        int closeY = grid.length;
        MazeNode closeNode = null;
        for(MazeNode node: nodes){
            if(node.getX() == x && node.getY() > y && node.getY() < closeY){
                closeY = node.getY();
                closeNode = node;
            }
        }
        if(path.indexOf(closeNode) != -1){
            closeNode = null;
        }
        checkCoord(closeNode);
    }

    private void findLeft(int x,int y) throws IOException{
        int closeX = -1;
        MazeNode closeNode = null;
        for(MazeNode node: nodes){
            if(node.getY() == y && node.getX() < x && node.getX() > closeX){
                closeX = node.getX();
                closeNode = node;
            }
        }
        if(path.indexOf(closeNode) != -1){
            closeNode = null;
        }
        checkCoord(closeNode);
    }

    private void findRight(int x,int y) throws IOException{
        int closeX = grid[0].length;
        MazeNode closeNode = null;
        for(MazeNode node: nodes){
            if(node.getY() == y && node.getX() > x && node.getX() < closeX){
                closeX = node.getX();
                closeNode = node;
            }
        }
        if(path.indexOf(closeNode) != -1){
            closeNode = null;
        }
        checkCoord(closeNode);
    }

    private MazeNode nodeAtCoord(int x, int y){
        MazeNode result = null;
        for(MazeNode node: nodes){
            if(node.getX() == x && node.getY() == y){
                result = node;
                break;
            }
        }
        return result;
    }

    private void foundEnd(){
        for(MazeNode n: path){
            solution.add(n.clone());
        }
        solved = true;
    }

    public ArrayList<MazeNode> getMazePath(){ return path; }

    public String toString(){
        return "Solution found: " + solved;
    }
}
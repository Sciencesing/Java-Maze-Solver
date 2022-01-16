public class MazeNode{
    
    private int x;
    private int y;
    /**
     * nodeValue Meaning:
     * Up open => +1
     * Down open => +2
     * Left open => +4
     * Right open => +8
     */
    private int nodeValue;

    public MazeNode(int x, int y, int nodeValue){
        this.x = x;
        this.y = y;
        this.nodeValue = nodeValue;
    }

    public int getNodeVal(){ return nodeValue; }

    public int getX(){ return x; }

    public int getY(){ return y; }

    public MazeNode clone(){
        return new MazeNode(this.x, this.y, this.nodeValue);
    }

}
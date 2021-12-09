package api;

public class Edge implements EdgeData{
    private int src;
    private double w;
    private int dest;
    private String info;

    public Edge (int src ,int dest , double weight){
        this.src = src;
        this.dest = dest ;
        this.w = weight;
        this.info = "white";

    }
    public Edge(Edge e){
        this.src=e.src;
        this.dest=e.dest;
        this.info=e.info;
        this.w=this.w;
    }

    public String getKey(){
        return this.getSrc()+"_"+this.dest;
    }

    @Override
    public int getSrc() {
        return  this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}

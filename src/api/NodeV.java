package api;



public class NodeV implements NodeData {
    public String pos;
    public int id;
    public String info;
    public NodeV previous;
    public double length;


    public NodeV (int id , String postion ){
        this.id= id ;
        this.pos = postion;
        this.info = "white";
    }
    public NodeV(NodeV n){
        this.pos=n.pos;
        this.id=n.id;
        this.info=n.info;
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public GeoLocation getLocation() {
        return new Location(this.pos);
    }
    @Override
    public void setLocation(GeoLocation p) {
        String x=Double.toString(p.x());
        String y=Double.toString(p.y());
        String z=Double.toString(p.z());
        this.pos=x+","+y+","+z;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {}

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s ;
    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }

}

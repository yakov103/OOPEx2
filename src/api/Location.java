package api;

public class Location implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public Location(String allPos){
        String xyz [];
        xyz=allPos.split(",");
        this.x=Double.parseDouble(xyz[0]);
        this.y=Double.parseDouble(xyz[1]);
        this.z=Double.parseDouble(xyz[2]);
    }


    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(GeoLocation g) {///1,2 3,2
        double distance1=Math.pow(this.x-g.x(),2)+Math.pow(this.y-g.y(),2)+Math.pow(this.z-g.z(),2);
        return Math.sqrt(distance1);
    }
}

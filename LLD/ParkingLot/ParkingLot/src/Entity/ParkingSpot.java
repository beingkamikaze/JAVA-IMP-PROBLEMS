package Entity;

public class ParkingSpot {
    private final String spotId;
    private boolean isFree=true;

    public ParkingSpot(String spotId) {
        this.spotId = spotId;
    }

    public boolean isSpotFree(){
        return this.isFree;
    }
    //add or park
    public void occupySpot(){
        isFree=false;
    }
    //remove or unpark
    public void releaseSpot(){
        isFree=true;
    }
     public String getSpotId(){
        return this.spotId;
     }
}

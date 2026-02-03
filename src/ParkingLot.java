import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParkingLot {
    Map<String, ParkingSpot> parkingSpotMap;
    Map<String, ParkingSpot> occupiedSpots;
    Map<String, Ticket> validTickets;
    int priceForHourInPaise;
    NumberGenerator numberGenerator;
    ReadWriteLock lock = new ReentrantReadWriteLock();

    public ParkingLot(List<ParkingSpot> parkingSpotList, int price) {
        parkingSpotMap =  new HashMap<>();
        for(ParkingSpot spot: parkingSpotList){
            parkingSpotMap.put(spot.id, spot);
        }
        this.priceForHourInPaise = price;
        numberGenerator= NumberGenerator.getNumberGenerator();
        occupiedSpots  = new HashMap<>();
        validTickets = new HashMap<>();
    }


    public void enterVehicle(VehicleType vehicleType) {
        ParkingSpotType parkingSpotType = ParkingSpotType.getSootForVehicleType(vehicleType);
        ParkingSpot spot = getSpotIfSpotAvailable(parkingSpotType);
        if(spot == null){
            p("No spot available for " + parkingSpotType.name());
            return;
        }

        Ticket ticket = new Ticket();
        ticket.vehicleType = vehicleType;
        ticket.entryTime = System.currentTimeMillis();
        ticket.vehicleNumber = UUID.randomUUID().toString();
        ticket.id = numberGenerator.getSequence()+"";
        ticket.spotId = spot.id;
        validTickets.put(ticket.id, ticket);
        p("vehicle "+vehicleType.name()+" entered a parking lot with id "+ticket.id+", spot Id: "+spot.id);
    }

    public void leaveVehicle(String id){
        if(id == null || validTickets.get(id) == null){
            p("Invalid ticket");
            return;
        }

        Ticket validTicket = validTickets.get(id);
        long price = calculatePrice(validTicket);
        parkingSpotMap.put(validTicket.spotId, occupiedSpots.remove(validTicket.spotId));
        validTickets.remove(id);
        p("vehicle "+validTicket.vehicleType.name()+" left a parking lot with id "+validTicket.id+", spot Id: "+validTicket.spotId+", price: "+price);

    }

    private ParkingSpot getSpotIfSpotAvailable(ParkingSpotType spotType) {
        while(true){
            lock.readLock().lock();
            try{
                for(ParkingSpot spot: parkingSpotMap.values()){
                    if(spot.parkingSpotType.equals(spotType)){
                        occupiedSpots.put(spot.id, spot);
                        parkingSpotMap.remove(spot.id);
                        return spot;
                    }
                }

                return null;
            }finally {
                lock.readLock().unlock();
            }
        }
    }

    long calculatePrice(Ticket ticket) {
        long duration = ticket.entryTime  - System.currentTimeMillis();
        long hours = duration/3600000;
        long price = (duration/3600000) * priceForHourInPaise;
        if(hours*3600000 > duration){
            price += priceForHourInPaise;
        }
        System.out.println("price calculated  ticket: "+ticket.id+", price: "+price);
        return price;
    }

    public void printCurrentAvailableSpots(){
        for(ParkingSpot spot: occupiedSpots.values()){
            p(">    spot available: "+spot.id+", "+spot.parkingSpotType.name());
        }
        p("------------\n");
    }

    void p(String s){
        System.out.println(s);
    }
}


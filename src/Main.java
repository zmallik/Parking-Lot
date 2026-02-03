import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<ParkingSpot> parkingSpotList = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        for(int i=0; i<5; i++){
            if(i < 2){
                parkingSpotList.add(new ParkingSpot(""+i, ParkingSpotType.Motorcycle));
            }else if(i < 3){
                parkingSpotList.add(new ParkingSpot(""+i, ParkingSpotType.Car));
            }else{
                parkingSpotList.add(new ParkingSpot(""+i, ParkingSpotType.LargeVehicle));
            }
        }

        ParkingLot parkingLot =  new ParkingLot(parkingSpotList, 5000);

        while(true){
            System.out.println("press 1 to enter, 2 to leave, any other to exit");
            String s = input.nextLine();
            if("1".equals(s)){
                System.out.println("press 1 for vehicle type Motorcycle, 2 for Car,  or any other number for LargeVehicle ");
                s = input.nextLine();
                if("1".equals(s)){
                    parkingLot.enterVehicle(VehicleType.Motorcycle);
                }else if("2".equals(s)){
                    parkingLot.enterVehicle(VehicleType.Car);
                }else{
                    parkingLot.enterVehicle(VehicleType.LargeVehicle);
                }

            }else if("2".equals(s)){
                System.out.println("please enter ticket id");
                s = input.nextLine();
                parkingLot.leaveVehicle(s);
            }else{
                return;
            }
        }
    }
}
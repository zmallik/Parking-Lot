public enum ParkingSpotType {
    Motorcycle,
    Car,
    LargeVehicle;

    public static ParkingSpotType getSootForVehicleType(VehicleType vehicleType){
        switch (vehicleType){
            case Motorcycle:
                return ParkingSpotType.Motorcycle;
            case Car:
                return ParkingSpotType.Car;
            case LargeVehicle:
                return ParkingSpotType.LargeVehicle;
            default:
                throw new RuntimeException("not a valid vehicle type");
        }

    }
}

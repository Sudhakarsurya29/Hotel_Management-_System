package hotel.management.system;
import java.util.*;
class Customer {
private int id;
private String name;
private String phone;
private int roomNumber;
private int stayDuration;

public Customer(int id, String name, String phone, int roomNumber, int
stayDuration) {
this.id = id;
this.name = name;
this.phone = phone;
this.roomNumber = roomNumber;
this.stayDuration = stayDuration;
}

public int getId() { return id; }
public String getName() { return name; }
public String getPhone() { return phone; }
public int getRoomNumber() { return roomNumber; }

12

public int getStayDuration() { return stayDuration; }

@Override
public String toString() {
return "Customer ID: " + id + ", Name: " + name + ", Phone: " + phone
+
", Room: " + roomNumber + ", Stay Duration: " + stayDuration
+ " days";
}
}

class Room {
private int roomNumber;
private boolean isBooked;
private boolean needsCleaning;

public Room(int roomNumber) {
this.roomNumber = roomNumber;
this.isBooked = false;
this.needsCleaning = false;
}

public int getRoomNumber() { return roomNumber; }
public boolean isBooked() { return isBooked; }
public boolean needsCleaning() { return needsCleaning; }

13
public void bookRoom() { isBooked = true; }
public void checkoutRoom() {
isBooked = false;
needsCleaning = true;
}

public void cleanRoom() { needsCleaning = false; }
}
class HotelManagementSystem {
private ArrayList<Customer> customers = new ArrayList<>();
private ArrayList<Room> rooms = new ArrayList<>();
private static final int ROOM_RATE_PER_DAY = 1000; // Example
rate

public HotelManagementSystem(int roomCount) {
for (int i = 1; i <= roomCount; i++) {
rooms.add(new Room(i));
}
}

public void checkIn(String name, String phone, int stayDuration) {
Room availableRoom = rooms.stream().filter(room ->
!room.isBooked()).findFirst().orElse(null);
if (availableRoom != null) {

14
int customerId = customers.size() + 1;
customers.add(new Customer(customerId, name, phone,
availableRoom.getRoomNumber(), stayDuration));
availableRoom.bookRoom();
System.out.println("Checked in successfully. Room number: " +
availableRoom.getRoomNumber());
} else {
System.out.println("No rooms available.");
}
}

public void checkOut(int roomNumber) {
Customer customer = customers.stream()
.filter(c -> c.getRoomNumber() == roomNumber)
.findFirst()
.orElse(null);

if (customer != null) {
Room room = rooms.get(roomNumber - 1);
room.checkoutRoom();
int bill = customer.getStayDuration() *
ROOM_RATE_PER_DAY;
System.out.println("Checked out successfully. Bill Amount: ₹" +
bill);
customers.remove(customer);

15

} else {
System.out.println("Invalid room number or no customer found.");
}
}

public void reserveRoom() {
System.out.println("\nAvailable Rooms:");
rooms.stream()
.filter(room -> !room.isBooked())
.forEach(room -> System.out.println("Room " +
room.getRoomNumber()));
}

public void viewCustomerDetails() {
System.out.println("\nCustomer Details:");
if (customers.isEmpty()) {
System.out.println("No customers currently checked in.");
} else {
customers.forEach(System.out::println);
}
}

public void housekeepingActivities() {
System.out.println("\nRooms Needing Cleaning:");
rooms.stream()

16
.filter(Room::needsCleaning)
.forEach(room -> System.out.println("Room " +
room.getRoomNumber() + " needs cleaning"));

System.out.println("\nMarking all rooms as cleaned...");
rooms.stream()
.filter(Room::needsCleaning)
.forEach(Room::cleanRoom);
System.out.println("All rooms are now clean.");
}
}

public class Main {
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
HotelManagementSystem hms = new HotelManagementSystem(10);
// Hotel with 10 rooms

while (true) {
System.out.println("\nHotel Management System");
System.out.println("1. Check In");
System.out.println("2. Check Out");
System.out.println("3. View Available Rooms");
System.out.println("4. View Customer Details");
System.out.println("5. Housekeeping Activities");

17
System.out.println("6. Exit");
System.out.print("Enter your choice: ");
int choice = scanner.nextInt();
scanner.nextLine(); // Consume newline

switch (choice) {
case 1:
System.out.print("Enter customer name: ");
String name = scanner.nextLine();
System.out.print("Enter customer phone number: ");
String phone = scanner.nextLine();
System.out.print("Enter stay duration (in days): ");
int duration = scanner.nextInt();
hms.checkIn(name, phone, duration);
break;

case 2:
System.out.print("Enter room number to check out: ");
int roomNumber = scanner.nextInt();
hms.checkOut(roomNumber);
break;

case 3:
hms.reserveRoom();
break;

18

case 4:
hms.viewCustomerDetails();
break;

case 5:
hms.housekeepingActivities();
break;

case 6:
System.out.println("Exiting...");
scanner.close();
return;

default:
System.out.println("Invalid choice. Please try again.");
}
}
}
}

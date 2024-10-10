import java.util.HashSet;
import java.util.Set;

public class PartyGuestList {
    private Set<String> guests = new HashSet<>();

    public void addGuest(String guest) {
        guests.add(guest);
    }

    public boolean removeGuest(String guest) {
        return guests.remove(guest);
    }

    public boolean isGuestInList(String guest) {
        return guests.contains(guest);
    }

    public int getTotalGuests() {
        return guests.size();
    }

    public Set<String> getGuests() {
        return guests;
    }
}
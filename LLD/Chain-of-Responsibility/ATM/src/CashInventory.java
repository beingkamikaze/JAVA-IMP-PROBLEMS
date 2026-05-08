import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class CashInventory {

    private final Map<Integer,Integer> inventory = new HashMap<>();

    public void add(int handlerLevel,int count)
    {
        inventory.put(handlerLevel,count);
    }
    public int getAvailable(int level)
    {
        return inventory.getOrDefault(level,0);
    }
    public void deduct(int level,int dispersedNote)
    {
        inventory.put(level,inventory.get(level)-dispersedNote);
    }
}

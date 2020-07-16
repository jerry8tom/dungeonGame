package unsw.dungeon;

import java.util.ArrayList;

public class Inventory implements Collection{

    ArrayList<Collection> items;

    public Inventory(){
        items = new ArrayList<Collection>();
    }

    @Override
    public Collection pickUp() {
        //Player automatically has an inventory therefore you cannot pick up another one (MILESTONE 3 bags??)
        return null;
    }

    //Displays all items in the inventory as a string
    @Override
    public String getItem() {
        String output = "";
        for(Collection c : items){
            output = output + "\n" + c.getItem();
        }
        return output;

    }

    public void addItem(Collection c){
        if(c == null) return;
        items.add(c);
    }

    public void removeItem(Collection c){
        if(c == null) return;
        if(items.contains(c)){
            items.remove(c);
        }
    }
    
}
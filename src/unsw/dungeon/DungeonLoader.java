package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    public DungeonLoader(JSONObject obj){
        json = obj;
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);
        dungeon.setGoals(json.getJSONObject("goal-condition"));
        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "sword":
            Weapon weapon = new Weapon(x, y , 5);
            onLoad(weapon);
            entity = weapon;
            break;
        case "key":
            int id = json.getInt("id");
            Key key = new Key(x, y, id);
            onLoad(key);
            entity = key;
            break;
        case "invincibility":
            Potion potion = new Potion(x,y);
            onLoad(potion);
            entity = potion;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            onLoad(treasure);
            entity = treasure;
            dungeon.addTreasure(treasure);
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            entity = enemy;
            dungeon.addEnemy(enemy);
            break;
        case "portal":
            int id1 = json.getInt("id");
            Portal portal = new Portal(id1,x,y);
            onLoad(portal);
            entity = portal;
            dungeon.addPortal(portal);
            break;      
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x,y) ;
            onLoad(boulder);
            entity = boulder;
            dungeon.addBoulder(boulder);
            break;           
        case "door":
            int id2 = json.getInt("id");
            Door door = new Door(x,y,id2);
            onLoad(door);
            entity = door;  
            break;   
        case "switch":
            FloorSwitch floorSwitch= new FloorSwitch(dungeon,x,y);
            onLoad(floorSwitch);
            entity = floorSwitch;
            dungeon.addSwitch(floorSwitch);
            break;
        case "exit":
            Exit exit = new Exit(x,y);
            onLoad(exit);
            entity = exit;  
            dungeon.addExit(exit);
            break;  
        case "hound":
            int id3 = json.getInt("id");
            EnemyHound hound = new EnemyHound(dungeon, x, y, id3);
            onLoad(hound);
            entity = hound;
            dungeon.addEnemy(hound);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Weapon weapon);

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Potion potion);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Door door);

    public abstract void onLoad(FloorSwitch floorSwitch);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(EnemyHound enemy);
}

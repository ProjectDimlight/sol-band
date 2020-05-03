package main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class Play implements Listener
{
	Server server;
	
	Play(Server server)
	{
		this.server = server;
	}
	
	Map<String, Instrument> mode = new HashMap<String, Instrument>();
	Map<String, Integer> octave = new HashMap<String, Integer>();
	
	public Instrument getMode(Player p)
	{
		String n = p.getName();
		if (mode.containsKey(n))
			return mode.get(n);
		else
			return null;
	}
	
	public Integer getOctave(Player p)
	{
		String n = p.getName();
		if (octave.containsKey(n))
			return octave.get(n);
		else
		{
			octave.put(n, 0);
			return 0;
		}
	}
	
	public void setMode(Player p, Instrument i)
	{
		String n = p.getName();
		mode.put(n, i);
		octave.put(n, 0);
		(new PlayerItemHeldEvent(p, 7, 0)).setCancelled(true);
	}
	
    @EventHandler
    public void onPlayerPlay(PlayerItemHeldEvent event)
    {
   	 	Player p = event.getPlayer();
    	Instrument m = getMode(p);
    	if (m == null) return;
    	 
    	// Play the note
    	int id = event.getNewSlot();
    	if (id < 7)
    	{
	    	Tone tone = Tone.values()[(id + 3) % 7];
	    	
	    	for (Player q : server.getOnlinePlayers().toArray(new Player[0]))
	    			q.playNote(p.getLocation(), m, new Note(getOctave(p), tone, p.isSneaking()));
	    	event.setCancelled(true);
    	}
    	else if (id == 7)
    	{
			octave.put(p.getName(), 0);
    	}
    	else if (id == 8)
    	{
			octave.put(p.getName(), 1);
    	}
    }
}
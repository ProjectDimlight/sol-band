package main;

import org.bukkit.Instrument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
	
	Play play;
	
	@Override
    public void onEnable() {
		play = new Play(getServer());
		
		getServer().getLogger().info("Register player event...");
		getServer().getPluginManager().registerEvents(play, this);
		
		getServer().getLogger().info("Register commands...");
		getCommand("band").setExecutor(this);
		
		getServer().getLogger().info("Done.");
    }

    @Override
    public void onDisable(){

    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
    	if ((sender instanceof Player) == false)
    		return false;
    	
    	System.out.println(cmd.getName());
    	
    	Player p = (Player)sender;
    	if (cmd.getName().equals("band"))
    	{
        	System.out.println(args.length);
    		if (args.length != 1)
    			return false;
    		
        	System.out.println(args[0]);
    		
    		if (args[0].equals("off"))
    		{
    			play.setMode(p, null);
    		}
    		else if (args[0].equals("help"))
    		{
    			p.sendMessage("/band <乐器>：使用某种乐器演奏；请先将物品栏调到8或9以获得最佳体验，此时你将无法正常切换物品。");
    			p.sendMessage("/band help：显示本帮助。");
    			p.sendMessage("/band off：退出演奏模式，你将可以正常切换物品栏。");

    			p.sendMessage("");
    			p.sendMessage("可选乐器：");
    			p.sendMessage("BANJO, BASS_DRUM, BASS_GUITAR, BELL, BIT");
    			p.sendMessage("CHIME, COW_BELL, DIDGERIDOO, FLUTE, GUITAR");
    			p.sendMessage("IRON_XYLOPHONE, PIANO, PLING, SNARE_DRUM");
    			p.sendMessage("STICKS, XYLOPHONE");
    		}
    		else
    		{
    			Instrument i = Instrument.valueOf(args[0]);
            	System.out.println(i);
    			play.setMode(p, i);
    		}
			return true;
    	}
    	
    	return false;
    }
}
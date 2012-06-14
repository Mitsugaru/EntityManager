package net.milkycraft.metrics;

import java.io.IOException;

import org.bukkit.Bukkit;

import net.milkycraft.EntityManager;
import net.milkycraft.configuration.Settings;
import net.milkycraft.metrics.Metrics.Graph;
import net.milkycraft.metrics.Metrics.Plotter;

public class MetricsStarter extends EntityManager implements Runnable{

	public MetricsStarter() {
		if(Settings.metrics) {		
		}
		else {
			writeLog("[EntityManager] Metrics is harmless, but as you wish... Disabling!");
		}
		return;
	}
	@Override
	public void run() {
		try {
			final Metrics metrics = new Metrics(EntityManager.main);
			final Graph ipGraph = metrics.createGraph("Ip Addresses");
			ipGraph.addPlotter(new SimplePlotter(String.valueOf(Bukkit.getIp())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	private class SimplePlotter extends Plotter
	{
		public SimplePlotter(final String name)
		{
			super(name);
		}

		@Override
		public int getValue() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}

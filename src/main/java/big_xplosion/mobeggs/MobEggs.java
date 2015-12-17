package big_xplosion.mobeggs;

import big_xplosion.mobeggs.config.Config;
import big_xplosion.mobeggs.event.ListenerPlayerInteractEntity;
import big_xplosion.mobeggs.lib.Constants;
import com.google.inject.Inject;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

@Plugin(id = Constants.PLUGIN_ID, name = Constants.PLUGIN_NAME, version = Constants.PLUGIN_VERSION, dependencies = "required-after:MSU|Core")
public class MobEggs {

	private static MobEggs plugin;

	@Inject
	private Game game;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	@Listener
	public void onPreInit(GamePreInitializationEvent event) {
		plugin = this;
		Config.init();
	}

	@Listener
	public void onInit(GameInitializationEvent event) {
		getGame().getEventManager().registerListeners(plugin, new ListenerPlayerInteractEntity());
	}

	public static MobEggs getPlugin() {
		return plugin;
	}

	public Game getGame() {
		return game;
	}

	public Path getConfigDir() {
		return configDir;
	}
}

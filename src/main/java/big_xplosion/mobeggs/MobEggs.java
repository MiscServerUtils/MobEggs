package big_xplosion.mobeggs;

import big_xplosion.mobeggs.event.ListenerPlayerInteractEntity;
import big_xplosion.mobeggs.lib.Constants;
import big_xplosion.msu.core.helper.RegistryHelper;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = Constants.PLUGIN_ID, name = Constants.PLUGIN_NAME, version = Constants.PLUGIN_VERSION)
public class MobEggs {

	private static MobEggs plugin;
	private static Game game;

	public static RegistryHelper registry = RegistryHelper.createRegistryHelper(plugin, game);

	@Listener
	public void preInit(GamePreInitializationEvent event) {
		plugin = this;
		game = event.getGame();

		registry.registerEventListener(new ListenerPlayerInteractEntity());
	}

	public static MobEggs getPlugin() {
		return plugin;
	}

	public Game getGame() {
		return game;
	}
}

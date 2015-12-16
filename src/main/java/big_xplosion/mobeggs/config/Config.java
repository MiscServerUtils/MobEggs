package big_xplosion.mobeggs.config;

import big_xplosion.mobeggs.MobEggs;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

	private static Path configDir = MobEggs.getPlugin().getConfigDir();
	private static Path configFile = Paths.get(configDir + File.separator + "mobeggs.conf");

	private static ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private static CommentedConfigurationNode config = configLoader.createEmptyNode(ConfigurationOptions.defaults());

	public static void init() {
		try {
			if (!Files.exists(configDir))
				Files.createDirectories(configDir);

			if (!Files.exists(configFile)) {
				Files.createFile(configFile);
				populate();
				configLoader.save(config);
			}

			config = configLoader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void populate() {
		config.getNode("activationItem", "item").setValue("minecraft:redstone").setComment("The item that is used to right click a mob before catching it");
		config.getNode("activationItem", "amount").setValue(5).setComment("The amount that you need of the item specified for activating the capturing of the mob");
	}

	public static String ACTIVATION_ITEM() {
		return config.getNode("activationItem", "item").getString("minecraft:redstone");
	}

	public static int ACTIVATION_ITEM_AMOUNT() {
		return config.getNode("activationItem", "amount").getInt(5);
	}
}

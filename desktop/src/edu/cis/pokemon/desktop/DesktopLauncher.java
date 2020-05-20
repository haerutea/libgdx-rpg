package edu.cis.pokemon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.cis.pokemon.Pokemon;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Pokemon Game";
		config.height = 400;
		config.width = 600;
		config.vSyncEnabled = true; //what does this do?

		new LwjglApplication(new Pokemon(), config);
	}
}

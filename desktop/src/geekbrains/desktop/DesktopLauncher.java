package geekbrains.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import geekbrains.MyStarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","\\xD0\\x92\\xD0\\xBB\\xD0\\xB0\\xD0\\xB4\\xD0\\xB8\\xD0\\xBC\\xD0\\xB8\\xD1\\x80");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyStarGame(), config);
	}
}

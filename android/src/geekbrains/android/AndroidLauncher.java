package geekbrains.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import geekbrains.MyStarGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.setProperty("user.name","\\xD0\\x92\\xD0\\xBB\\xD0\\xB0\\xD0\\xB4\\xD0\\xB8\\xD0\\xBC\\xD0\\xB8\\xD1\\x80");

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyStarGame(), config);
	}
}

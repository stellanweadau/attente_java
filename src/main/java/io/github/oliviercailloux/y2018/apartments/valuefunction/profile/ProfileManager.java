package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import java.util.HashMap;
import java.util.Map;

public class ProfileManager {
	
	boolean initialized;
	HashMap<ProfileType, Profile> mapProfile = new HashMap<ProfileType, Profile>();
	
	private ProfileManager() {
		this.initialized = false;
	}
	
	private static ProfileManager INSTANCE = null;
	
	public static ProfileManager gatInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ProfileManager();
		}
		
		return INSTANCE;
	}
}

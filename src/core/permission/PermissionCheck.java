package core.permission;

import nsfw.user.entity.User;

public interface PermissionCheck {

	public boolean isAccessible(User user, String code);
	
}

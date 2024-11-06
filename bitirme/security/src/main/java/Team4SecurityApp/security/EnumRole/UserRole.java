package Team4SecurityApp.security.EnumRole;

public enum UserRole {

    ROLE_ADMIN(0),

    ROLE_USER(1);

    private final int index;

    UserRole(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

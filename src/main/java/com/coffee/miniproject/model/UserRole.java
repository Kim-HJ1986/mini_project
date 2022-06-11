package com.coffee.miniproject.model;

public enum UserRole {
<<<<<<< HEAD
    ADMIN(Authority.ADMIN),
    USER(Authority.USER);

    private final String authority;

    UserRole(String authority){
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
=======
    ADMIN_ROLE,
    USER_ROLE
>>>>>>> 1c28ef9 ([etc]First Commit)
}

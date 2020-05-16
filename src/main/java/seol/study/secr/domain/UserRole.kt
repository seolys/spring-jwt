package seol.study.secr.domain

enum class UserRole(val roleName: String) {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER")
    ;
    companion object {
        fun getRoleByName(roleName: String): UserRole {
            return UserRole.values().first { it.roleName.equals(roleName) }
        }
    }
}
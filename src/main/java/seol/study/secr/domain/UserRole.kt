package seol.study.secr.domain

enum class UserRole(private val roleName: String) {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER")
    ;
}
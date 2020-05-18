package seol.study.secr.domain

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "ACCOUNT")
data class Account (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        @Column(name = "ACCOUNT_USERNAME") 
        val username: String? = null,

        @Column(name = "ACCOUNT_LOGINID")
        val userId: String? = null,

        @Column(name = "ACCOUNT_PASSWORD")
        val password: String? = null,

        @Column(name = "ACCOUNT_ROLE")
        @Enumerated(value = EnumType.STRING)
        val userRole: UserRole? = null,

        @Column(name = "ACCOUNT_SOCIAL_PROVIDER")
        @Enumerated(value = EnumType.STRING)
        val socialProvider: SocialProvider? = null,

        @Column(name = "ACCOUNT_SOCIAL_ID")
        val socialId: Long? = null,

        @Column(name = "ACCOUNT_SOCIAL_PROFILE_PICTURE")
        val profilePicture: String? = null
)



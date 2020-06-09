package ru.kerporation.breddit.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "refresh_tokens")
class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	var id: Long? = null

	@Column(name = "token")
	lateinit var token: String

	@Column(name = "created_date", updatable = false, nullable = false)
	val createdDate: Date = Date()

}

package ru.kerporation.breddit.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "posts")
class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	var id: Long? = null

	@CreationTimestamp
	@Column(name = "created", updatable = false, nullable = false)
	val created: Date = Date()

	@UpdateTimestamp
	@Column(name = "modified", nullable = false)
	var modified: Date = Date()

	@NotBlank(message = "Post Name cannot be empty")
	@Column(name = "post_name")
	lateinit var postName: String

	@Column(name = "url")
	var url: String? = null

	@Lob
	@Column(name = "description")
	var description: String? = null

	@Column(name = "vote_count")
	var voteCount: Long = 0

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	lateinit var user: User

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subreddit_id")
	var subreddit: Subreddit? = null

}

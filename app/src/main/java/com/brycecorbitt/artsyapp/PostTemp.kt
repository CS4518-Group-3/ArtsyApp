package com.brycecorbitt.artsyapp

import java.util.UUID
import kotlin.random.Random

const val UNVOTED = 0
const val UPVOTED = 1
const val DOWNVOTED = 2

data class PostTemp (val id: UUID = UUID.randomUUID(),
                 var index: Int = 0,
                 var net: Int = Random.nextInt(0, 100),
                 var voteStatus: Int = UNVOTED)
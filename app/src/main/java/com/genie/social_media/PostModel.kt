package com.genie.social_media

import com.genie.social_media.fragments.AddPostFragment

class PostModel {
    lateinit var post_url :String
    var post_time :Long = 0
    lateinit var post_caption:String
    lateinit var post_author:String
    var post_id=""
constructor()
    constructor(post_url: String, post_time: Long,post_caption: String, post_author: String) {
        this.post_url = post_url
        this.post_time = post_time
        this.post_caption = post_caption
        this.post_author = post_author
    }
}
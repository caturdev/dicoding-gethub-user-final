package com.example.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GithubUser(
    val username: String?
) : Parcelable
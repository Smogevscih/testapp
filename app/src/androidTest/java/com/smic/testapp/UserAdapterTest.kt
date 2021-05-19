package com.smic.testapp

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.smic.testapp.adapter.ProgressHolder
import com.smic.testapp.adapter.UserAdapter
import com.smic.testapp.adapter.UserHolder
import com.smic.testapp.network.GithubUser
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserAdapterTest {

    private val USER_HOLD = 100
    private val PROGRESS_HOLD = 200

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    private lateinit var activity: MainActivity
    private lateinit var recyclerView: RecyclerView

    @Before
    fun setUp() {
        activity = rule.activity
        recyclerView = RecyclerView(activity)
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
    }

    @Test
    fun test_itemCount_depends_on_lastpage() {
        val githubUserList = getUserList(10)
        var userAdapter = UserAdapter(githubUserList, true)
        Truth.assertThat(userAdapter.itemCount == githubUserList.size).isTrue()

        userAdapter = UserAdapter(githubUserList, false)
        Truth.assertThat(userAdapter.itemCount == githubUserList.size + 1).isTrue()
    }

    @Test
    fun test_get_right_holder() {
        val githubUserList = getUserList(10)
        val userAdapter = UserAdapter(githubUserList, true)

        var result = userAdapter.onCreateViewHolder(recyclerView, USER_HOLD)
        Truth.assertThat(result).isInstanceOf(UserHolder::class.java)

        result = userAdapter.onCreateViewHolder(recyclerView, PROGRESS_HOLD)
        Truth.assertThat(result).isInstanceOf(ProgressHolder::class.java)

    }

    @Test
    fun test_get_right_itemViewType() {
        val count = 10
        val githubUserList = getUserList(count)
        val userAdapter = UserAdapter(githubUserList, true)

        val result = userAdapter.getItemViewType(count)
        Truth.assertThat(result).isEqualTo(PROGRESS_HOLD)

        for (i in 0 until 10) {
            val result = userAdapter.getItemViewType(i)
            Truth.assertThat(result).isEqualTo(USER_HOLD)
        }

    }

    @Test
    fun test_add_new_githubUsers() {
        val count = 10
        val githubUserList = getUserList(count)
        val userAdapter = UserAdapter(githubUserList, false)
        var result = userAdapter.itemCount
        Truth.assertThat(result).isEqualTo(count + 1)

        val newGithubUserList = getUserList(count)
        userAdapter.addNewUsers(newGithubUserList, false) //if it's not the last page


        result = userAdapter.itemCount
        Truth.assertThat(result).isEqualTo(count + count + 1)

        userAdapter.addNewUsers(newGithubUserList, true) //if it's  the last page

        result = userAdapter.itemCount
        Truth.assertThat(result).isEqualTo(count + count + count)

    }


    //get list of GithubUsers
    private fun getUserList(count: Int): ArrayList<GithubUser> {
        val githubUserList = ArrayList<GithubUser>()
        for (i in 0 until count) {
            val githubUser = GithubUser(
                "$i",
                "http://$i",
                "name$i",
                "bio$i",
                i,
                i,
                i
            )

            githubUserList.add(githubUser)
        }
        return githubUserList
    }


}
package com.example.recyclerviewwithdatastore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.recyclerviewwithdatastore.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(
        user = arrayListOf(),
        onItemClick = this::navigateToNextScreen,
            onBookmarkClicked = this::onBookmarkClicked,

        )
    }

    private val preferenceManger by lazy { PreferenceManager(this) }
    private val users: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpUi()
        setUpListeners()
        getUserData()

    }

    private fun setUpUi() {
        binding?.uiRvList?.adapter = userAdapter
    }

    private fun setUpListeners() {
        binding?.uiEtSearchBar?.doOnTextChanged { text, start, before, count ->
            filterUserList(text.toString())
        }
        binding?.uiSwipeRefresh?.setOnRefreshListener {
            getUserData()
        }
    }

    private fun getUserData() {
        val user = UserService.userInstance.getUserData()
        user.enqueue(object :Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("TAG", "error in fetching news", t)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                Log.d("s", "error in fetching news")
                if(response.isSuccessful) {
                    binding?.uiProgressBar?.visibility = View.GONE
                    binding?.uiSwipeRefresh?.isRefreshing = false

                    val userList: List<User>? = response.body()
                    if (userList != null) {
                        checkDataSavedInDb(userList)
                        users.addAll(userList)
                        userAdapter.onUserListChanged(userList)
                    }
                }
            }
        })
    }

    private fun checkDataSavedInDb(userList: List<User>) {
        userList.forEach { user ->
            val savedList = preferenceManger.getUserList()
            savedList?.forEach {
                if (it.id == user.id){
                    user.isSaved = true
                }
            }
        }
    }


    private fun filterUserList(searchText: String) {
        if (searchText.isEmpty()) {
            setUserListToUi(users)
        }else {
            val filteredList = users.filter {
                it.title?.contains(searchText,true) == true
            }
            setUserListToUi(filteredList)
        }
    }

    private fun setUserListToUi(users : List<User>) {
        userAdapter.onUserListChanged(users)
    }

    private fun onBookmarkClicked(user: User?) {
        if(user?.isSaved == false) {
        user.isSaved = true
            preferenceManger.saveUserInfo(user)
            Toast.makeText(this,"Bookmark saved to Bookmarks",Toast.LENGTH_SHORT).show()
        } else {
            user?.isSaved = false
            preferenceManger.removeUserFromList(user)
            Toast.makeText(this,"Bookmark deleted from Bookmarks",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.uiBookmark) {
            val intent = Intent(this, SavedUserActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToNextScreen(user: User?) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_USER,user)
        val intent = Intent(this, DisplayUserActivity::class.java)
        intent.putExtra(KEY_BUNDLE_USER,bundle)
        startActivity(intent)
    }

    companion object {
        const val KEY_USER = "key.user"
        const val KEY_BUNDLE_USER = "key.bundle.user"
    }
}


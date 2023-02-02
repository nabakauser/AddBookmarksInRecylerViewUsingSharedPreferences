package com.example.recyclerviewwithdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.recyclerviewwithdatastore.databinding.ActivitySavedUserBinding

class SavedUserActivity : AppCompatActivity() {


    private var binding: ActivitySavedUserBinding? = null
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(
            user = arrayListOf(),
            onItemClick = {},
            onBookmarkClicked = {
                onBookmarkDelete(it) // -> do this when you have multiple functions inside one button click
            }
            //onBookmarkClicked = this::onBookmarkedClicked -> do this when you have only one function to call
        )
    }

    private val preferenceManager by lazy {
        PreferenceManager (this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpUi()
        getUserData()
    }

    private fun setUpUi() {
        binding?.uiRvBookmarkedItems?.adapter = userAdapter
    }

    private fun getUserData() {
        preferenceManager.getUserList().let {
            userAdapter.onUserListChanged(it)
        }
    }

    private fun onBookmarkDelete(user: User?) {
        if(user?.isSaved == true ) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.alertTitle))
                .setMessage(getString(R.string.alertMessage))
                .setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->
                   onAlertDialogPositiveButtonClicked(user)
                }
                .setNegativeButton("No") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
            builder.create()
            builder.show()
        }
    }

    private fun onAlertDialogPositiveButtonClicked(user: User?) {
        preferenceManager.removeUserFromList(user)
        Toast.makeText(this,"Bookmark deleted from Bookmarks",Toast.LENGTH_SHORT).show()
        getUserData()
    }
}
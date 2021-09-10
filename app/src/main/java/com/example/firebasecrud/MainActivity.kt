package com.example.firebasecrud

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import com.example.firebasecrud.core.UserAdapter
import com.example.firebasecrud.core.UserDTO
import com.example.firebasecrud.core.ItemDivider
import kotlinx.android.synthetic.main.user_list_item.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //save button listener
        save.setOnClickListener {
            //get reference and values to UI
            val firstname = firstname.text.toString()
            val lastname = lastname.text.toString()
            age.inputType = InputType.TYPE_CLASS_NUMBER
            val age: Int = age.text.toString().toInt()

            //pass data to function
            saveData(firstname, lastname, age)
        }
        //get list of users
        readData()

    }

    private fun saveData(firstname: String, lastname: String, age: Number) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["firstname"] = firstname
        user["lastname"] = lastname
        user["age"] = age

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "User added successfully", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity, "Failed to added user", Toast.LENGTH_LONG).show()
            }

        //update view
        readData()
    }

    private  fun readData() {
        val db = FirebaseFirestore.getInstance()
        //Prepare displaying of users list
        //create layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)
        val userList : MutableList<UserDTO> = ArrayList()

        db.collection("users")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        val obj = UserDTO()
                        obj.firstname = document.data.getValue("firstname") as String
                        obj.lastname = document.data.getValue("lastname") as String
                        obj.age = document.data.getValue("age") as Long

                        userList.add(obj)

                        //Delete button
//                        deleteBtn.setOnClickListener {
//                            val builder = AlertDialog.Builder(this@MainActivity)
//                            // set the AlertDialog's title
//                            builder.setTitle(getString(R.string.edit, obj.firstname))
//
//                            // set list of items to display and create event handler
//                            builder.setItems(R.array.dialog_items, DialogInterface.OnClickListener { dialog, which ->
//                                when (which) {
//                                    0 -> editUser(obj)
//                                    1 -> {
//                                        deleteUser(obj)
//                                    }
//                                }
//                            }
//                            )
//                            // set the AlertDialog's negative Button
//                            builder.setNegativeButton(getString(R.string.cancel), null)
//                            builder.create().show() // display the AlertDialog
//                        }
                    }

                }
                // create an assign adapter
                val adapter = UserAdapter(userList as ArrayList<UserDTO>)
                recyclerView.adapter = adapter
                // specify a custom ItemDecorator to draw lines between list items
                recyclerView.addItemDecoration(ItemDivider(this))


                //set onLongPressListener
//                adapter.apply {
//                    onItemLongPress = {UserDTO ->
//    //                    Toast.makeText(this@MainActivity, "You pressed " + contactDTO.name.toString(), Toast.LENGTH_SHORT).show()
//                        // create a new AlertDialog
//                        val builder = AlertDialog.Builder(this@MainActivity)
//                        // set the AlertDialog's title
//                        builder.setTitle(getString(R.string.edit, UserDTO.firstname))
//
//                        // set list of items to display and create event handler
//                        builder.setItems(R.array.dialog_items, DialogInterface.OnClickListener { dialog, which ->
//                            when (which) {
//                                0 -> editUser(UserDTO)
//                                1 -> {
//                                    deleteUser(UserDTO)
//                                }
//                            }
//                        }
//                        )
//                        // set the AlertDialog's negative Button
//                        builder.setNegativeButton(getString(R.string.cancel), null)
//                        builder.create().show() // display the AlertDialog
//                    }
//                }
            }
    }

    private  fun editUser(user: UserDTO){}
    private fun deleteUser(user: UserDTO){}
}
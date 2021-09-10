package com.example.firebasecrud.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasecrud.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class UserAdapter(private val users: ArrayList<UserDTO>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var onItemLongPress: ((UserDTO) -> Unit)? = null

    // this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(v)
    }

    // this method is binding the data on the list
    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bindItems(users[position])
    }

    // this method is returning the size of the list
    override fun getItemCount(): Int {
        return users.size
    }

    // the class is holding the listview
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: UserDTO) {
            val firstname = itemView.findViewById<TextView>(R.id.tv_firstname)
            val lastname  = itemView.findViewById<TextView>(R.id.tv_lastname)
            val age = itemView.findViewById<TextView>(R.id.tv_age)
            val menuPopUp = itemView.findViewById<ImageView>(R.id.moreMenu)
            //Assign UI elements
            firstname.text = user.firstname
            lastname.text = user.lastname
            age.text = user.age.toString()

            //Implement Popup Menu
            menuPopUp.setOnClickListener {
                val pop: PopupMenu = PopupMenu(itemView.context, it)
                pop.menuInflater.inflate(R.menu.popup_menu, pop.menu)
                pop.setOnMenuItemClickListener { item->
                    when(item.itemId)
                    {
                        R.id.edit->{
                            val tempUser: UserDTO = user

                            Toast.makeText(itemView.context, "You clicked Edit of "+ tempUser.firstname, Toast.LENGTH_LONG).show()
                        }

                        R.id.delete->{
                            val tempUser: UserDTO = user
                            Toast.makeText(itemView.context, "You clicked Delete of "+ tempUser.firstname, Toast.LENGTH_LONG).show()
                        }

                    }
                    true
                }
                pop.show()
            }
        }
    }
}

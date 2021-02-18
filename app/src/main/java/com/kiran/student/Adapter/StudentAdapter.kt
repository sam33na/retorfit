package com.kiran.student.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiran.student.R
import com.kiran.student.api.StudentAPI
import com.kiran.student.entity.Student
import com.kiran.student.repository.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentAdapter (
    private  val context:Context,
            private val lstStudent:MutableList<Student>
): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>()
{
    class StudentViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvAge: TextView = view.findViewById(R.id.tvAge)
        val imgEdit: ImageButton = view.findViewById(R.id.imgEdit)
        val imgDelete: ImageButton = view.findViewById(R.id.imgDelete)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.student_view_layout,parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student= lstStudent[position]
        holder.tvName.text=student.fullname
        holder.tvAge.text=student.age.toString()

        holder.imgEdit.setOnClickListener{

        }
        holder.imgDelete.setOnClickListener {
        val builder=AlertDialog.Builder(context)
            builder.setTitle("Confirm Delete?")
            builder.setMessage("Are you sure you will delete ${ student.fullname}?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes")
            {
                _,_ ->
                deleteStudent(student)
            }
            builder.setNegativeButton("No")
            {
                    _,_ ->
                Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show()
            }
            val alertDialog:AlertDialog=builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        }


    override fun getItemCount(): Int {
       return lstStudent.size
    }
    private fun deleteStudent(student: Student) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = StudentRepository()
                val response = studentRepository.deleteStudents(student._id!!)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    }
                    withContext(Main) {
                        lstStudent.remove(student)
                        notifyDataSetChanged()
                    }
                }
            } catch (ex: Exception) {
                withContext(Main) {
                    Toast.makeText(
                        context,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
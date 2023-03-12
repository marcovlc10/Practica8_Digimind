package lopez.marco.mydigimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import lopez.marco.mydigimind.databinding.FragmentHomeBinding
import lopez.marco.mydigimind.R
import lopez.marco.mydigimind.ui.Tarea

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter: AdaptadorTareas? = null
    companion object{
        var tasks = ArrayList<Tarea>()
        var first = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (first){
            fillTask()
            first = false
        }

        adapter = AdaptadorTareas(root.context, tasks)
        val gridTasks: GridView = root.findViewById(R.id.reminders)
        gridTasks.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun fillTask(){
        tasks.add(Tarea("Practice 1", arrayListOf("Tuesday"), "17:30"))
        tasks.add(Tarea("Practice 2", arrayListOf("Monday", "Sunday"), "12:30"))
        tasks.add(Tarea("Practice 3", arrayListOf("Wednesday"), "19:00"))
        tasks.add(Tarea("Practice 4", arrayListOf("Saturday"), "18:00"))
        tasks.add(Tarea("Practice 5", arrayListOf("Friday"), "14:00"))
        tasks.add(Tarea("Practice 6", arrayListOf("Thursday"), "8:30"))
        tasks.add(Tarea("Practice 7", arrayListOf("Monday"), "10:30"))
    }

    private class AdaptadorTareas: BaseAdapter {
        var tasks = ArrayList<Tarea>()
        var context: Context? = null
        constructor(context: Context, tasks: ArrayList<Tarea>){
            this.context = context
            this.tasks = tasks
        }

        override fun getCount(): Int {
            return tasks.size
        }
        override fun getItem(p0: Int): Any {
            return tasks[p0]
        }
        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var task = tasks[p0]
            var inflater = LayoutInflater.from(context)
            var view = inflater.inflate(R.layout.task_view, null)

            var title: TextView = view.findViewById(R.id.title_reminder)
            var days: TextView = view.findViewById(R.id.days_reminder)
            var time: TextView = view.findViewById(R.id.time_reminder)

            title.setText(task.titulo)
            days.setText(task.dias.toString())
            time.setText(task.tiempo)

            return view
        }
    }
}
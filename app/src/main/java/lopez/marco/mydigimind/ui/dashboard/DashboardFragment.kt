package lopez.marco.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import lopez.marco.mydigimind.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat
import java.util.*
import lopez.marco.mydigimind.ui.Tarea
import lopez.marco.mydigimind.R
import lopez.marco.mydigimind.ui.home.HomeFragment

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val timeBtn: Button = root.findViewById(R.id.time_btn)

        var storage = FirebaseFirestore.getInstance()

        timeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                timeBtn.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(
                Calendar.MINUTE), true).show()
        }

        val doneBtn = root.findViewById(R.id.done) as Button
        val nameTask = root.findViewById(R.id.name) as EditText
        val checkSunday = root.findViewById(R.id.sunday) as CheckBox
        val checkMonday = root.findViewById(R.id.monday) as CheckBox
        val checkTuesday = root.findViewById(R.id.tuesday) as CheckBox
        val checkWednesday = root.findViewById(R.id.wednesday) as CheckBox
        val checkThursday = root.findViewById(R.id.thursday) as CheckBox
        val checkFriday = root.findViewById(R.id.friday) as CheckBox
        val checkSaturday = root.findViewById(R.id.saturday) as CheckBox

        doneBtn.setOnClickListener {
            var actividad = hashMapOf(
                "actividad" to nameTask.text.toString(),
                "lu" to checkMonday.isChecked,
                "ma" to checkTuesday.isChecked,
                "mi" to checkWednesday.isChecked,
                "ju" to checkThursday.isChecked,
                "vi" to checkFriday.isChecked,
                "sa" to checkSaturday.isChecked,
                "do" to checkSunday.isChecked,
                "tiempo" to timeBtn.text.toString()
            )
            storage.collection("actividades").add(actividad).addOnSuccessListener {
                    documentReference -> Toast.makeText(root.context, "Nueva tarea agregada!", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {e ->
                    Toast.makeText(root.context, "Error al agregar la tarea", Toast.LENGTH_SHORT).show()
                    println(e.printStackTrace())
                }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
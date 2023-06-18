package wing.tree.world.capitals.quiz.view.fragment

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {
    protected val application: Application get() = requireActivity().application

    protected fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    protected fun popBackStack() {
        findNavController().popBackStack()
    }
}

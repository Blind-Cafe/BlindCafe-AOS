package com.abouttime.blindcafe.common.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.abouttime.blindcafe.common.base.view_model.BaseViewModel
import com.abouttime.blindcafe.presentation.NavHostActivity
import com.abouttime.blindcafe.presentation.NavHostViewModel
import com.abouttime.blindcafe.presentation.onboarding.rule.RuleFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.prefs.Preferences

abstract class BaseFragment<VM: BaseViewModel>(layoutId: Int) : Fragment(layoutId) {

    // View Model
    abstract val viewModel: VM

    private lateinit var dataStore: DataStore<Preferences>



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeToastEvent()
        observeNavigationEvent()

    }

    private fun observeToastEvent() {
        viewModel.toastEvent.observe(viewLifecycleOwner) { resId ->
            showToast(resId)
        }
    }

    private fun observeNavigationEvent() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) { directions ->
            moveToDirections(directions)
        }
    }
    private fun observeSaveStringDataEvent() {
        viewModel.saveStringDataEvent.observe(viewLifecycleOwner) { pair ->

        }
    }
    private fun observeGetStringDataEvent() {
        viewModel.getStringDataEvent.observe(viewLifecycleOwner) { key ->

        }
    }


    fun moveToDirections(directions: NavDirections) {
        Log.d("asdf", "BaseFragment 의 moveToDriections")
        //parentActivity.moveToDirections(directions)

        findNavController().navigate(directions)
    }


    /** Hide Keyboard **/
    fun hideKeyboard() {
        if (activity?.currentFocus != null) {
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                0
            )
        }
    }






    /** Util for all fragment **/
    fun showToast(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }

    fun getColor(resId: Int) = resources.getColor(resId, null)
}
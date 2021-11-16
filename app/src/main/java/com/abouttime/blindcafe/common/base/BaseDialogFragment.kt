package com.abouttime.blindcafe.common.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseDialogFragment<VM: BaseViewModel>(layoutId: Int): DialogFragment(layoutId) {
    // View Model
    abstract val viewModel: VM



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
            directions?.let {
                moveToDirections(directions)
            } ?: kotlin.run {
                popDirections()
            }
        }
    }


    fun moveToDirections(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    fun popDirections() {
        findNavController().popBackStack()
    }


    /** Hide Keyboard **/
    fun hideKeyboard() {
        if (activity?.currentFocus != null) {
            val inputMethodManager = getInputManager()
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                0
            )
        }
    }
    fun getInputManager(): InputMethodManager =  requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager






    /** Util for all fragment **/
    fun showToast(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }

    fun getColorByResId(resId: Int) = resources.getColor(resId, null)

    fun getStringByResId(resId: Int): String = resources.getString(resId)
}
package com.abouttime.blindcafe.common.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController


abstract class BaseFragment<VM: BaseViewModel>(layoutId: Int) : Fragment(layoutId) {

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
    fun getInputManager(): InputMethodManager =  requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager






    /** Util for all fragment **/
    fun showToast(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }

    fun getColor(resId: Int) = resources.getColor(resId, null)
}

package com.abouttime.blindcafe.common.base

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.abouttime.BlindCafeApplication
import com.abouttime.blindcafe.databinding.ToastBinding
import com.abouttime.blindcafe.presentation.GlobalLiveData


abstract class BaseFragment<VM : BaseViewModel>(layoutId: Int) : Fragment(layoutId) {

    // View Model
    abstract val viewModel: VM

    private var loadingDialog: AlertDialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observeLoadingEvent()

        observeToastEvent()
        observeNavigationEvent()
        observeSaveNavigationDataEvent()
        observePopNavigationEvent()
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

    private fun observePopNavigationEvent() {
        viewModel.popNavigationEvent.observe(viewLifecycleOwner) { id ->
            id?.let {
                popUntilDirections(it)
            } ?: kotlin.run {
                popOneDirections()
            }
        }
    }


    fun moveToDirections(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    fun popOneDirections() {
        findNavController().popBackStack()
    }

    fun popUntilDirections(id: Int) {
        findNavController().popBackStack(id, true)
    }


    private fun observeSaveNavigationDataEvent() {
        viewModel.saveNavigationDataEvent.observe(viewLifecycleOwner) { pair ->
            saveNavigationResult(pair.first, pair.second)
        }
    }

    protected fun getNavigationResult(key: String): MutableLiveData<String>? {
        return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)
    }

    private fun saveNavigationResult(key: String, result: String) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
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

    fun getInputManager(): InputMethodManager =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


    /** Util for all fragment **/
    fun showToast(resId: Int) {
        createToast(getString(resId))?.show()
    }

    fun createToast(content: String): Toast? {
        val inflater = LayoutInflater.from(requireContext())
        val binding: ToastBinding = ToastBinding.inflate(layoutInflater)
        binding.textView.text = content

        return Toast(requireContext()).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 102.toPx())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun getColorByResId(resId: Int) = resources.getColor(resId, null)

    fun getStringByResId(resId: Int): String = resources.getString(resId)

    fun saveStringData(pair: Pair<String, String>) {
        BlindCafeApplication.sharedPreferences
            .edit()
            .putString(
                pair.first,
                pair.second
            )
            .apply()
    }

    fun getStringData(key: String): String? {
        return BlindCafeApplication.sharedPreferences
            .getString(key, null)

    }

    fun showLoading() {
        GlobalLiveData.loadingEvent.postValue(true)
    }
    fun dismissLoading() {
        GlobalLiveData.loadingEvent.postValue(false)
    }


}

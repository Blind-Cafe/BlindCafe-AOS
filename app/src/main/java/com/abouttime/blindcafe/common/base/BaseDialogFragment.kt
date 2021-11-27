package com.abouttime.blindcafe.common.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.abouttime.BlindCafeApplication
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.ToastBinding

abstract class BaseDialogFragment<VM: BaseViewModel>(layoutId: Int): DialogFragment(layoutId) {
    // View Model
    abstract val viewModel: VM



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    protected fun getNavigationResult(key: String = "result") =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

    protected fun saveNavigationResult(key: String = "result", result: String) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }
    protected fun saveHomeResult(key: String, result: String) {
        findNavController().getBackStackEntry(R.id.mainFragment).savedStateHandle.set(key, result)
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
        //Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
        createToast(getString(resId))?.show()
    }
    fun createToast(content: String): Toast? {
        val inflater = LayoutInflater.from(requireContext())
        val binding: ToastBinding = ToastBinding.inflate(layoutInflater)
        binding.textView.text = content

        return Toast(requireContext()).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 48.toPx())
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
}
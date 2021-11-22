package com.abouttime.blindcafe.common.base

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.abouttime.BlindCafeApplication
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.ToastBinding


abstract class BaseFragment<VM: BaseViewModel>(layoutId: Int) : Fragment(layoutId) {

    // View Model
    abstract val viewModel: VM

    lateinit var loadingDialog: Dialog





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoadingDialog()
        observeToastEvent()
        observeNavigationEvent()

    }

    private fun observeLoadingEvent() {
        viewModel.loadingEvent.observe(viewLifecycleOwner) {
            if (it) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
            }
        }
    }
    private fun initLoadingDialog() {
        loadingDialog = Dialog(requireContext())
        loadingDialog.setContentView(R.layout.dialog_fragment_loading)
        loadingDialog.setCanceledOnTouchOutside(true)
        loadingDialog.window?.setGravity(Gravity.CENTER)
    }
    protected fun showLoadingDialog() {
        loadingDialog.show()
    }
    protected fun dismissLoadingDialog() {
        loadingDialog.dismiss()
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
        //Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
        createToast(getString(resId))?.show()
    }
    fun createToast(content: String): Toast? {
        val inflater = LayoutInflater.from(requireContext())
        val binding: ToastBinding = ToastBinding.inflate(layoutInflater)
        binding.textView.text = content

        return Toast(requireContext()).apply {
            //setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.toPx())
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

    fun Fragment.getNavigationResult(key: String = "result") =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

    fun Fragment.setNavigationResult(result: String, key: String = "result") {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }
}

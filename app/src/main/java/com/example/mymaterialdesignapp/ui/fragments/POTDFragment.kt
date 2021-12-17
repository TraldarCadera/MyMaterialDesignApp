package com.example.mymaterialdesignapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mymaterialdesignapp.R
import com.example.mymaterialdesignapp.databinding.FragmentMainBinding
import com.example.mymaterialdesignapp.repository.POTDResponseData
import com.example.mymaterialdesignapp.ui.MainActivity
import com.example.mymaterialdesignapp.ui.chips.ChipsFragment
import com.example.mymaterialdesignapp.viewmodel.POTDState
import com.example.mymaterialdesignapp.viewmodel.POTDViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior

class POTDFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: POTDViewModel by lazy {
        ViewModelProvider(this).get(POTDViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequest()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://ru.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        setBottomAppBar()

        val behavior = BottomSheetBehavior.from(binding.includedBottomSheet.bottomSheetContainer)
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_buttom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, R.string.favorite, Toast.LENGTH_SHORT)
                .show()
            R.id.app_bar_setting -> requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container,ChipsFragment.newInstance())
                .commit()
            android.R.id.home -> BottomNavigationDrawerFragment().show(
                requireActivity().supportFragmentManager,
                ""
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderData(state: POTDState) {
        when (state) {
            is POTDState.Error -> {
                binding.imageView.load(R.drawable.ic_load_error_vector)
            }
            is POTDState.Loading -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
                // TODO ("Сделать красоту, скорее всего веселую анимацию загрузки")
            }
            is POTDState.Success -> {
                val response = state.response
                val url = response?.url
                val header = response?.title
                val description = response?.explanation
                binding.imageView.load(url) {
                    lifecycle(this@POTDFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }
                binding.includedBottomSheet.bottomSheetDescriptionHeader.text = header
                binding.includedBottomSheet.bottomSheetDescription.text = description
            }
        }
    }

    private var isMain = true

    private fun setBottomAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_buttom_bar)
            }
        }
    }

    companion object {
        fun newInstance() = POTDFragment()
    }
}
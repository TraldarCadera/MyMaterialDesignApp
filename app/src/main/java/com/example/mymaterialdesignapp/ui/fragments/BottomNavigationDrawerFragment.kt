package com.example.mymaterialdesignapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mymaterialdesignapp.R
import com.example.mymaterialdesignapp.databinding.BottomNavigationLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    Toast.makeText(context, "Тык один", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_two -> {
                    Toast.makeText(context, "Тык два", Toast.LENGTH_SHORT).show()
                }

            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
package com.example.diary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.databinding.FragmentEditorBinding

class FragmentEditor : Fragment(R.layout.fragment_editor) {
	private val viewModel: RecordsViewModel by activityViewModels()
	private lateinit var binding: FragmentEditorBinding

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding = FragmentEditorBinding.bind(view)

		binding.back.setOnClickListener {
			findNavController().navigate(R.id.action_fragmentEditor_to_fragmentRecords)
		}

		if (arguments == null) {
			binding.ok.setOnClickListener { onCreateButtonClick() }
		} else {
			binding.name.setText(requireArguments().getCharSequence("TITLE"))
			binding.text.setText(requireArguments().getCharSequence("CONTENT"))
			binding.ok.setOnClickListener { updateDiaryRecord(
				uid = requireArguments().getString("ID") as String,
				requireArguments().getLong("DATE")
			) }
		}
	}

	private fun updateDiaryRecord(uid: String, createdAt: Long) {
		if (binding.name.text.isEmpty()) {
			return
		}

		viewModel.updateDiaryRecord(
			uid,
			title = binding.name.text.toString(),
			content = binding.text.text.toString(),
			createdAt
			)

		findNavController().navigate(R.id.action_fragmentEditor_to_fragmentRecords)
	}

	private fun onCreateButtonClick() {
		if (binding.name.text.isEmpty()) {
			return
		}

		viewModel.createDiaryRecord(
			binding.name.text.toString(),
			binding.text.text.toString()
		)

		findNavController().navigate(R.id.action_fragmentEditor_to_fragmentRecords)
	}
}
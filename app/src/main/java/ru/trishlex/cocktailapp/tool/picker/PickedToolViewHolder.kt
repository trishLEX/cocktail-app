package ru.trishlex.cocktailapp.tool.picker

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R

class PickedToolViewHolder(
    view: View,
    private val toolPickerService: ToolPickerService
) : RecyclerView.ViewHolder(view) {

    private var preview: ImageView = view.findViewById(R.id.toolPreview)
    private var name: TextView = view.findViewById(R.id.toolName)
    private var checkBox: CheckBox = view.findViewById(R.id.pickedCheckBox)

    private lateinit var pickedTool: PickedTool

    init {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            pickedTool.isPicked = isChecked
            if (isChecked) {
                toolPickerService.add(pickedTool)
            } else {
                toolPickerService.remove(pickedTool)
            }
        }
    }

    fun setTool(pickedTool: PickedTool) {
        this.pickedTool = pickedTool
        preview.setImageBitmap(pickedTool.preview)
        name.text = pickedTool.name
        checkBox.isChecked = pickedTool.isPicked
    }
}
package ru.trishlex.cocktailapp.tool.picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R

class PickedToolsListAdapter(
    private val toolPickerService: ToolPickerService,
    private val pickedTools: ArrayList<PickedTool> = ArrayList()
) : RecyclerView.Adapter<PickedToolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickedToolViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tool_picker, parent, false)
        return PickedToolViewHolder(view, toolPickerService)
    }

    override fun onBindViewHolder(holder: PickedToolViewHolder, position: Int) {
        val tool = pickedTools[position]
        holder.setTool(tool)
    }

    override fun getItemCount(): Int {
        return pickedTools.size
    }

    fun add(pickedTool: PickedTool) {
        pickedTools.add(pickedTool)
        notifyItemInserted(pickedTools.size - 1)
    }

    fun addAll(pickedTools: List<PickedTool>) {
        pickedTools.forEach { add(it) }
    }

    fun removeAll() {
        pickedTools.clear()
        notifyItemRangeRemoved(0, pickedTools.size)
    }
}
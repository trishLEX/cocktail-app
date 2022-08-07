package ru.trishlex.cocktailapp.tool.picker

import ru.trishlex.cocktailapp.PickerService

class ToolPickerService private constructor() : PickerService<PickedTool>() {

    private val tools = HashMap<Int, PickedTool>()

    companion object {
        private var instance: ToolPickerService? = null

        @Synchronized
        fun getInstance(): ToolPickerService {
            if (instance == null) {
                instance = ToolPickerService()
            }
            return instance as ToolPickerService
        }
    }

    fun getTool(id: Int): PickedTool {
        return tools[id]!!
    }

    fun getTool(pickedTool: PickedTool): PickedTool {
        return tools[pickedTool.id]!!
    }

    fun putTool(pickedTool: PickedTool) {
        tools[pickedTool.id] = pickedTool
    }

    fun putTools(pickedTools: List<PickedTool>) {
        pickedTools.forEach { putTool(it) }
    }

    fun getTools(): Collection<PickedTool> {
        return tools.values
    }
}
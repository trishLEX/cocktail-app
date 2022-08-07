package ru.trishlex.cocktailapp.tool.picker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R

class ToolPickerActivity : AppCompatActivity() {

    private lateinit var toolsRecyclerView: RecyclerView
    private lateinit var pickedToolsListAdapter: PickedToolsListAdapter
    private val toolPickerService = ToolPickerService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_picker)

        val tools = (intent.extras!!.getSerializable("tools") as Array<PickedTool>)
            .map { toolPickerService.getTool(it) }
            .map {
                if (toolPickerService.contains(it)) {
                    it.isPicked = true
                }
                it
            }.toList()

        pickedToolsListAdapter = PickedToolsListAdapter(toolPickerService)
        toolsRecyclerView = findViewById(R.id.toolsRecyclerView)
        toolsRecyclerView.layoutManager = LinearLayoutManager(this)
        toolsRecyclerView.adapter = pickedToolsListAdapter
        pickedToolsListAdapter.addAll(tools)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val returnIntent = Intent()
            returnIntent.putExtra("picked", toolPickerService.getPicked().toTypedArray())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
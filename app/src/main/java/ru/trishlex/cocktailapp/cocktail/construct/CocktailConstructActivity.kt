package ru.trishlex.cocktailapp.cocktail.construct

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import org.openapitools.client.model.SaveCocktailRequestDTO
import ru.trishlex.cocktailapp.EmptyFieldException
import ru.trishlex.cocktailapp.FIELD
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.model.Unit
import ru.trishlex.cocktailapp.ingredient.picker.IngredientPickerActivity
import ru.trishlex.cocktailapp.ingredient.picker.IngredientPickerService
import ru.trishlex.cocktailapp.ingredient.picker.IngredientsPickerLoader
import ru.trishlex.cocktailapp.ingredient.picker.PickerIngredient
import ru.trishlex.cocktailapp.loader.AsyncResult
import ru.trishlex.cocktailapp.tool.picker.PickedTool
import ru.trishlex.cocktailapp.tool.picker.ToolPickerActivity
import ru.trishlex.cocktailapp.tool.picker.ToolPickerLoader
import ru.trishlex.cocktailapp.tool.picker.ToolPickerService
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt


class CocktailConstructActivity :
    AppCompatActivity(),
    LoaderManager.LoaderCallbacks<AsyncResult<Boolean>>
{
    companion object {
        private val previewSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            130f,
            Resources.getSystem().displayMetrics
        ).roundToInt()

        private val margin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            5f,
            Resources.getSystem().displayMetrics
        ).roundToInt()
    }

    private lateinit var cocktailNameView: EditText
    private lateinit var cocktailInstructionsView: EditText
    private lateinit var cocktailDescriptionView: EditText
    private lateinit var cocktailTagsView: EditText
    private var imageView: ImageView? = null
    private var previewView: ImageView? = null
    private lateinit var imageCard: CardView
    private lateinit var previewCard: CardView
    private lateinit var ingredientsLayout: LinearLayout
    private lateinit var toolsLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var pickedIngredientCallback: PickedIngredientCallback
    private lateinit var pickedToolCallback: PickedToolCallback
    private lateinit var saveCocktailButton: Button
    private lateinit var loaderManager: LoaderManager

    private val ingredientPickerService = IngredientPickerService.getInstance()
    private val toolPickerService = ToolPickerService.getInstance()
    private val saveCocktailDTO = SaveCocktailRequestDTO()

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data!!
            imageCard.removeAllViews()
            imageView = ImageView(this)
            imageView!!.setImageURI(data.data)
            imageView!!.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            imageCard.addView(imageView)
        }
    }

    private val previewLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data!!
            previewCard.removeAllViews()
            previewView = ImageView(this)
            previewView!!.setImageURI(data.data)
            previewView!!.layoutParams = ViewGroup.LayoutParams(
                previewSize,
                previewSize
            )

            previewCard.layoutParams = LinearLayout.LayoutParams(
                previewSize,
                previewSize
            )
            previewCard.addView(previewView)
        }
    }

    private val ingredientLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data!!
            val pickedIngredients = (data.extras!!.getSerializable("picked") as Array<PickerIngredient>)
                .map { ingredientPickerService.getIngredient(it) }
                .toList()
            val inflater = LayoutInflater.from(ingredientsLayout.context)
            ingredientsLayout.removeViews(0, ingredientsLayout.childCount - 1)
            for (ingredient in pickedIngredients.reversed()) {
                ingredientPickerService.add(ingredient)
                val ingredientPickerView = inflater.inflate(
                    R.layout.cocktail_ingredient_picker,
                    ingredientsLayout,
                    false
                )

                val preview = ingredientPickerView.findViewById<ImageView>(R.id.cocktailIngredientPreview)
                preview.setImageBitmap(ingredient.preview)

                val nameView = ingredientPickerView.findViewById<TextView>(R.id.cocktailIngredientName)
                nameView.text = ingredient.name

                val amountSpinner = ingredientPickerView.findViewById<Spinner>(R.id.cocktailIngredientUnit)
                ArrayAdapter.createFromResource(
                    this,
                    R.array.units,
                    R.layout.spinner_list
                ).also { adapter ->
                    adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
                    amountSpinner.adapter = adapter
                }
                amountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        ingredient.unit = Unit.getByOrdinal(position)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                }

                val amountEdit = ingredientPickerView.findViewById<EditText>(R.id.cocktailIngredientAmount)
                amountEdit.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable) {
                        Log.d("debugLog", "${ingredient}, ${ingredientPickerService.getPicked()}")
                        ingredient.amount = s.toString().toInt()
                        Log.d("debugLog", "${ingredient}, ${ingredientPickerService.getPicked()}")
                    }

                })

                ingredientsLayout.addView(ingredientPickerView, 0)
            }
        }
    }

    private val toolLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data!!
            val pickedTools = (data.extras!!.getSerializable("picked") as Array<PickedTool>)
                .map { toolPickerService.getTool(it) }
                .toList()
            val inflater = LayoutInflater.from(toolsLayout.context)
            toolsLayout.removeViews(0, toolsLayout.childCount - 1)
            for (tool in pickedTools.reversed()) {
                val toolPickerView = inflater.inflate(
                    R.layout.tool_picker,
                    toolsLayout,
                    false
                )

                val preview = toolPickerView.findViewById<ImageView>(R.id.toolPreview)
                preview.setImageBitmap(tool.preview)

                val nameView = toolPickerView.findViewById<TextView>(R.id.toolName)
                nameView.text = tool.name

                val checkBox = toolPickerView.findViewById<CheckBox>(R.id.pickedCheckBox)
                checkBox.visibility = View.GONE

                toolsLayout.addView(toolPickerView, 0)
            }
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        this.pickedIngredientCallback = PickedIngredientCallback(this, ingredientPickerService)
        this.pickedToolCallback = PickedToolCallback(this, toolPickerService)
        this.pickedIngredientCallback.pickedToolCallback = this.pickedToolCallback
        this.pickedToolCallback.pickedIngredientCallback = this.pickedIngredientCallback
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_construct)

        progressBar = findViewById(R.id.progressBar)

        cocktailNameView = findViewById(R.id.cocktailName)
        cocktailInstructionsView = findViewById(R.id.cocktailInstructions)
        cocktailDescriptionView = findViewById(R.id.cocktailDescription)
        cocktailTagsView = findViewById(R.id.cocktailTags)

        loaderManager = LoaderManager.getInstance(this)
        val ingredientLoader = loaderManager.getLoader<List<PickerIngredient>>(IngredientsPickerLoader.ID)
        if (ingredientLoader == null) {
            loaderManager.initLoader(
                IngredientsPickerLoader.ID,
                null,
                pickedIngredientCallback
            )
        } else {
            loaderManager.restartLoader(
                IngredientsPickerLoader.ID,
                null,
                pickedIngredientCallback
            )
        }

        val toolLoader = loaderManager.getLoader<List<PickedTool>>(ToolPickerLoader.ID)
        if (toolLoader == null) {
            loaderManager.initLoader(
                ToolPickerLoader.ID,
                null,
                pickedToolCallback
            )
        } else {
            loaderManager.restartLoader(
                ToolPickerLoader.ID,
                null,
                pickedToolCallback
            )
        }

        imageCard = findViewById(R.id.addCard)
        imageCard.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoPickerIntent.type = "image/*"
            imageLauncher.launch(photoPickerIntent)
        }

        previewCard = findViewById(R.id.addPreview)
        previewCard.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoPickerIntent.type = "image/*"
            previewLauncher.launch(photoPickerIntent)
        }

        ingredientsLayout = findViewById(R.id.cocktailIngredients)
        val ingredientPlaceHolder = findViewById<CardView>(R.id.addIngredient)
        ingredientPlaceHolder.setOnClickListener {
            val intent = Intent(this, IngredientPickerActivity::class.java)
            intent.putExtra(
                "ingredientTypes",
                ingredientPickerService.getIngredients().toTypedArray()
            )
            ingredientLauncher.launch(intent)
        }

        toolsLayout = findViewById(R.id.cocktailTools)
        val toolPlaceholder = findViewById<CardView>(R.id.addTool)
        toolPlaceholder.setOnClickListener {
            val intent = Intent(this, ToolPickerActivity::class.java)
            intent.putExtra(
                "tools",
                toolPickerService.getTools().toTypedArray()
            )
            toolLauncher.launch(intent)
        }

        saveCocktailButton = findViewById(R.id.saveCocktailButton)
        saveCocktailButton.setOnClickListener {
            try {
                saveCocktailDTO.name = cocktailNameView.text.toString()
                if (saveCocktailDTO.name.isBlank()) {
                    throw EmptyFieldException(FIELD.NAME)
                }
                if (imageView == null) {
                    throw EmptyFieldException(FIELD.IMAGE)
                }
                if (previewView == null) {
                    throw EmptyFieldException(FIELD.PREVIEW)
                }
                saveCocktailDTO.image = getBytes(imageView!!)
                saveCocktailDTO.preview = getBytes(previewView!!)
                saveCocktailDTO.ingredients = ingredientPickerService.getPicked()
                    .map { it.toSaveDto() }
                if (saveCocktailDTO.ingredients.isEmpty()) {
                    throw EmptyFieldException(FIELD.INGREDIENTS)
                }
                saveCocktailDTO.tools = toolPickerService.getPicked().map { it.id }
                if (saveCocktailDTO.tools.isEmpty()) {
                    throw EmptyFieldException(FIELD.TOOLS)
                }
                saveCocktailDTO.instructions = cocktailInstructionsView.text
                    .split('\n')
                    .map { it.trim() }
                if (saveCocktailDTO.ingredients.isEmpty()) {
                    throw EmptyFieldException(FIELD.INSTRUCTIONS)
                }
                saveCocktailDTO.description = cocktailDescriptionView.text.toString().trim().ifEmpty { null }
                saveCocktailDTO.tags = cocktailTagsView.text
                    .split('\n')
                    .map { it.trim() }

                loaderManager = LoaderManager.getInstance(this)
                val saveLoader = loaderManager.getLoader<Boolean>(CocktailSaveLoader.ID)
                if (saveLoader == null) {
                    loaderManager.initLoader(
                        CocktailSaveLoader.ID,
                        null,
                        this@CocktailConstructActivity
                    )
                } else {
                    loaderManager.restartLoader(
                        CocktailSaveLoader.ID,
                        null,
                        this@CocktailConstructActivity
                    )
                }
            } catch (ex: EmptyFieldException) {
                Toast.makeText(this, ex.field.text, Toast.LENGTH_SHORT).show()
            } catch (ex: Exception) {
                Toast.makeText(this, R.string.internetError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBytes(imageView: ImageView): ByteArray {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bytesData: ByteArray = stream.toByteArray()
        stream.close()
        return bytesData
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<AsyncResult<Boolean>> {
        if (id == CocktailSaveLoader.ID) {
            return CocktailSaveLoader(this, saveCocktailDTO)
        }
        throw UnsupportedOperationException(id.toString())
    }

    override fun onLoadFinished(loader: Loader<AsyncResult<Boolean>>, data: AsyncResult<Boolean>?) {
        Toast.makeText(this, "Коктейль сохранен", Toast.LENGTH_SHORT).show()
    }

    override fun onLoaderReset(loader: Loader<AsyncResult<Boolean>>) {
    }


}
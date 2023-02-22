package com.terrinc.shopinglist.presentation.shopitem

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.terrinc.shopinglist.R
import com.terrinc.shopinglist.ShopListApp
import com.terrinc.shopinglist.data.ShopItemProvider
import com.terrinc.shopinglist.domain.ShopItem
import com.terrinc.shopinglist.presentation.ViewModelFactory
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopItemFragment : Fragment() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: TextInputEditText
    private lateinit var etCount: TextInputEditText
    private lateinit var buttonSave: Button

    private val component by lazy {
        (requireActivity().application as ShopListApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ShopItemViewModel

    private var screenMode: String = UNKNOWN_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    private lateinit var editFinishedListener: EditFinishedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EditFinishedListener) {
            editFinishedListener = context
        } else {
            throw RuntimeException("Activity mas implement EditFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        when (screenMode) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            editFinishedListener.onEditFinished()
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) { isError ->
            tilName.error = if (isError) getString(R.string.error_input_name) else null
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) { isError ->
            tilCount.error = if (isError) getString(R.string.error_input_count) else null
        }
    }

    private fun launchEditMode() {
        viewModel.shopItem.observe(viewLifecycleOwner) { shopItem ->
            etName.setText(shopItem.name)
            etCount.setText(shopItem.count.toString())
        }
        viewModel.getShopItem(shopItemId)
        buttonSave.setOnClickListener {
            viewModel.editShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
//            viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
            thread {
                val contentValues = ContentValues().apply {
                    put(ShopItemProvider.ID, 0)
                    put(ShopItemProvider.NAME, etName.text.toString())
                    put(ShopItemProvider.COUNT, etCount.text.toString())
                    put(ShopItemProvider.ENABLED, true)
                }
                context?.contentResolver?.insert(Uri.parse(ShopItemProvider.URI), contentValues)
            }
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.tilName)
        tilCount = view.findViewById(R.id.tilCount)
        etName = view.findViewById(R.id.nameInput)
        etCount = view.findViewById(R.id.countInput)

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        buttonSave = view.findViewById(R.id.saveButton)
    }

    interface EditFinishedListener {
        fun onEditFinished()
    }

    companion object {
        private const val SCREEN_MODE = "mode"
        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val ADD_MODE = "mode_add"
        private const val EDIT_MODE = "mode_edit"
        private const val UNKNOWN_MODE = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, EDIT_MODE)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}

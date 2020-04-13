package com.shuneault.netrunnerdeckbuilder

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.shuneault.netrunnerdeckbuilder.ViewModel.FullScreenViewModel
import com.shuneault.netrunnerdeckbuilder.game.Card
import com.shuneault.netrunnerdeckbuilder.helper.NrdbHelper
import org.koin.android.viewmodel.ext.android.viewModel

class ViewDeckFullscreenActivity : AppCompatActivity() {
    private var mCards = ArrayList<Card>()
    private val vm: FullScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) { // load data
        vm.setCardCode ( intent.getStringExtra(EXTRA_CARD_CODE))
        vm.position = intent.getIntExtra(EXTRA_POSITION, 0)
        val cardList = intent.getSerializableExtra(EXTRA_CARDS)
        if (cardList != null) {
            vm.cardCodes = cardList as java.util.ArrayList<String>
        }

        val deckId = intent.getLongExtra(EXTRA_DECK_ID, 0)
        if (deckId > 0) {
            vm.loadDeck(deckId)
        }
        // set theme to identity's faction colors
        val factionCode = vm.factionCode
        if (factionCode != null) {
            val themeName = "Theme.Netrunner_" + factionCode.replace("-", "")
            val theme = resources.getIdentifier(themeName, "style", this.packageName)
            setTheme(theme)
        }
        // super must be called after setTheme or else notification and navigation bars won't be themed properly
        super.onCreate(savedInstanceState)
        // This too
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true)
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }
        setContentView(R.layout.activity_fullscreen_view)
        // Quit if deck is empty
        if (vm.size == 0) {
            exitIfDeckEmpty()
            return
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.fullscreen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnuOnline -> {
                // show nrdb page!
                val currentCard = mCards[vm.position]
                NrdbHelper.ShowNrdbWebPage(this, currentCard)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun exitIfDeckEmpty() {
        if (mCards.size == 0) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.view_deck)
            builder.setMessage(R.string.deck_is_empty)
            builder.setCancelable(false)
            builder.setPositiveButton(R.string.ok) { _, _ -> finish() }
            builder.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

    companion object {
        // Arguments
        const val EXTRA_DECK_ID = "com.example.netrunnerdeckbuilder.EXTRA_DECK_ID"
        const val EXTRA_CARD_CODE = "com.example.netrunnerdeckbuilder.EXTRA_CARD_CODE"
        const val EXTRA_POSITION = "com.example.netrunnerdeckbuilder.EXTRA_POSITION"
        const val EXTRA_CARDS = "com.example.netrunnerdeckbuilder.EXTRA_CARDS"
    }
}
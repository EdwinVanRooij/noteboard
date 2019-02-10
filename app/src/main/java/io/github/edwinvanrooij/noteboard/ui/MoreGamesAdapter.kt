package io.github.edwinvanrooij.noteboard.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.github.edwinvanrooij.noteboard.Game
import io.github.edwinvanrooij.noteboard.R


class MoreGamesAdapter(
        private val context: Context,
        private val dataSource: ArrayList<Game>
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private lateinit var moreGamesAdapterListener: MoreGamesAdapterListener

    fun setMoreGamesAdapterListener(moreGamesAdapterListener: MoreGamesAdapterListener) {
        this.moreGamesAdapterListener = moreGamesAdapterListener
    }

    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_game, parent, false)

        // Get thumbnail element
        val ivLogo = rowView.findViewById(R.id.ivLogo) as ImageView

        // Get title element
        val tvTitle = rowView.findViewById(R.id.tvTitle) as TextView

        // Get subtitle element
        val tvDescription = rowView.findViewById(R.id.tvDescription) as TextView

        // Get choose button element
        val btnChoose = rowView.findViewById(R.id.btnChoose) as Button

        // 1
        val game = getItem(position) as Game

        // 2
        if (game == Game.OCTAVES) {
            tvTitle.text = "Octaves"
            tvDescription.text = "Train your musical ear by recognizing notes in various octaves!"
            btnChoose.setOnClickListener {
                moreGamesAdapterListener.onChoseOctaves()
            }
        } else if (game == Game.FRETS) {
            tvTitle.text = "Frets"
            tvDescription.text = "Learn to recognize musical notes on the fretboard!"
            btnChoose.setOnClickListener {
                moreGamesAdapterListener.onChoseFrets()
            }
        }

        return rowView
    }
}

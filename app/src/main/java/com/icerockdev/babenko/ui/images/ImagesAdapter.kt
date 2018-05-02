package com.icerockdev.babenko.ui.images

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.icerockdev.babenko.R
import com.icerockdev.babenko.model.entities.ImageItem
import com.squareup.picasso.Picasso

/**
 * Created by Roman Babenko on 11/05/17.
 */

class ImagesAdapter(private val mImageList: List<ImageItem>, private val mCallback: ImagesListCallback) : RecyclerView.Adapter<ImagesAdapter.ImagesItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesItemHolder {
        val inflater = parent.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.item_image, parent, false)
        return ImagesItemHolder(convertView)
    }

    override fun onBindViewHolder(holder: ImagesItemHolder, position: Int) {
        holder.updateView(mImageList[position])
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    inner class ImagesItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.pictureElementImageView)
        lateinit var pictureElementImageView: ImageView
        @BindView(R.id.pictureElementId)
        lateinit var pictureElementId: TextView
        @BindView(R.id.pictureElementTitle)
        lateinit var pictureElementTitle: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun updateView(item: ImageItem) {
            Picasso.get().load(item.thumbnailUrl).error(R.drawable.question_mark)
                    .placeholder(R.drawable.question_mark).into(pictureElementImageView)
            pictureElementId.text = item.id.toString()
            pictureElementTitle.text = item.title
            pictureElementImageView.rootView.setOnClickListener { v -> mCallback.itemClicked(item.url) }
        }

    }
}

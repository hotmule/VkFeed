package com.hotmule.vkfeed.ui.feed

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hotmule.vkfeed.R
import com.hotmule.vkfeed.data.network.model.feed.FeedResponse
import com.hotmule.vkfeed.data.network.model.feed.content.Group
import com.hotmule.vkfeed.data.network.model.feed.content.Item
import com.hotmule.vkfeed.data.network.model.feed.content.Profile
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class FeedAdapter(private var onBottomReachedListener: OnBottomReachedListener,
                  private var onPostClickListener: OnPostClickListener)
    : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var itemsData: MutableList<Item> = mutableListOf()
    private var profilesData: MutableList<Profile> = mutableListOf()
    private var groupsData: MutableList<Group> = mutableListOf()

    private lateinit var context: Context

    interface OnBottomReachedListener {
        fun onBottomReached()
    }

    interface OnPostClickListener {
        fun onPostClick(item: Item, sourceName: String)
    }

    fun setPosts(feedResponse: FeedResponse) {
        itemsData = feedResponse.items
        profilesData = feedResponse.profiles
        groupsData = feedResponse.groups
        notifyDataSetChanged()
    }

    fun setMorePosts(feedResponse: FeedResponse) {
        itemsData.addAll(feedResponse.items)
        profilesData.addAll(feedResponse.profiles)
        groupsData.addAll(feedResponse.groups)
        notifyDataSetChanged()
    }

    fun clean() {
        itemsData.clear()
        profilesData.clear()
        groupsData.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_post_preview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position == itemsData.size - 1)
            onBottomReachedListener.onBottomReached()

        val item = itemsData[position]

        val text = item.text
        val unixDate = item.date
        val sourceId = item.sourceId

        setText(text, holder)
        setDate(unixDate, holder)

        val sourceName = setSourceDetails(sourceId, holder)

        holder.setOnPostClickListener(item, sourceName, onPostClickListener)
    }

    private fun setText(text: String, holder: FeedAdapter.ViewHolder) {
        if (text.isNotEmpty())
            setupTextParams(text, Color.BLACK, holder)
        else
            setupTextParams(context.getString(R.string.post_without_text), Color.GRAY, holder)
    }

    private fun setupTextParams(text: String, textColor: Int, holder: ViewHolder) {
        holder.tvText.text = text
        holder.tvText.setTextColor(textColor)
    }

    private fun setDate(unixDate: Int, holder: ViewHolder) {
        val date = Date(unixDate * 1000L)
        val simpleDateFormat = SimpleDateFormat("HH:mm, d MMM", Locale.getDefault())
        val formattedDate = simpleDateFormat.format(date)
        holder.tvDate.text = formattedDate
    }

    private fun setSourceDetails(sourceId: Int, holder: ViewHolder): String {
        var sourceName = ""
        var imageLink = ""

        if (sourceId > 0)
            profilesData.filter { sourceId == it.id }
                    .forEach {
                        sourceName = it.getFullName()
                        imageLink = it.photoLink
                    }
        else
            groupsData.filter { sourceId == -1 * it.id }
                    .forEach {
                        sourceName = it.name
                        imageLink = it.photoLink
                    }

        Picasso.get().load(imageLink).into(holder.ivAvatar)

        holder.tvSourceName.text = sourceName
        holder.tvSourceName.setTypeface(null, Typeface.BOLD)

        return sourceName
    }

    override fun getItemCount() = itemsData.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvText: TextView = itemView.findViewById(R.id.tvText)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvSourceName: TextView = itemView.findViewById(R.id.tvSourceName)
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivPhoto)

        fun setOnPostClickListener(item: Item, sourceName: String, listener: OnPostClickListener) {
            itemView.setOnClickListener { listener.onPostClick(item, sourceName) }
        }
    }
}
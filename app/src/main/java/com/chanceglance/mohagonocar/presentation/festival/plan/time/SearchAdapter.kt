package com.chanceglance.mohagonocar.presentation.festival.plan.time

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanceglance.mohagonocar.data.responseDto.ResponseAddressDto
import com.chanceglance.mohagonocar.databinding.ItemSearchBinding

class SearchAdapter(private val clickText:(String) -> Unit):RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private val searchList: MutableList<String> = mutableListOf()

    inner class SearchViewHolder(private val binding:ItemSearchBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(text:String){
            binding.tvName.text=text
            binding.iSearch.setOnClickListener{
                clickText(text)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int  = searchList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = searchList[position]
        holder.onBind(item)
    }

    fun getList(text: List<ResponseAddressDto.Document>) {
        searchList.clear()
        searchList.addAll(text.map { it.placeName })
        notifyDataSetChanged()
    }
}
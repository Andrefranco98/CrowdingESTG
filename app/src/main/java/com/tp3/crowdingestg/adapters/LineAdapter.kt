package com.tp3.crowdingestg.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp3.crowdingestg.R
import com.tp3.crowdingestg.dataclasses.Place
import kotlinx.android.synthetic.main.recyclerline.view.*

class LineAdapter(val list: ArrayList<Place>):RecyclerView.Adapter<LineViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {

        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recyclerline, parent, false);
        return LineViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentPlace = list[position]

        holder.posicao.text = currentPlace.posicao.toString()
        holder.name.text = currentPlace.name
        holder.pontos.text = currentPlace.pontos.toString()
        holder.contribuicoes.text = currentPlace.contribuicoes
    }

}

class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val posicao = itemView.posicao
    val name = itemView.name
    var pontos = itemView.pontos
    val contribuicoes = itemView.contribuicoes


}
package com.tzuhsien.noodoeassignment.parkinginfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoToDisplay
import com.tzuhsien.noodoeassignment.databinding.ItemParkBinding

class ParkingInfoAdapter: ListAdapter<ParkingInfoToDisplay, ParkingInfoAdapter.ParkViewHolder>(DiffCallBack) {

    class ParkViewHolder(private val binding: ItemParkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(park: ParkingInfoToDisplay) {

            binding.txtId.text = park.id
            binding.txtName.text = park.name
            binding.txtAddress.text = park.address
            binding.txtTotalCar.text = park.totalCar.toString()
            binding.txtAvailableCar.text = park.availableCar.toString()
            binding.txtSocketAvailable.text = park.socketAvailable.toString()
            binding.txtSocketInUse.text = park.socketInUse.toString()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<ParkingInfoToDisplay>() {
        override fun areItemsTheSame(oldItem: ParkingInfoToDisplay, newItem: ParkingInfoToDisplay): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ParkingInfoToDisplay, newItem: ParkingInfoToDisplay): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
        return ParkViewHolder(ItemParkBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ParkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
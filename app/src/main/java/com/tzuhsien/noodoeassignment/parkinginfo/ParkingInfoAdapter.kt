package com.tzuhsien.noodoeassignment.parkinginfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tzuhsien.noodoeassignment.util.Util
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoToDisplay
import com.tzuhsien.noodoeassignment.databinding.ItemParkBinding

class ParkingInfoAdapter: ListAdapter<ParkingInfoToDisplay, ParkingInfoAdapter.ParkViewHolder>(DiffCallBack) {

    class ParkViewHolder(private val binding: ItemParkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(park: ParkingInfoToDisplay) {

            binding.txtId.text = Util.getString(R.string.park_id, park.id)
            binding.txtName.text = Util.getString(R.string.park_name, park.name)
            binding.txtAddress.text = Util.getString(R.string.park_address, park.address)
            binding.txtTotalCar.text = Util.getString(R.string.park_total_car, park.totalCar)
            binding.txtAvailableCar.text = Util.getString(R.string.park_available_car, park.availableCar)

            binding.txtChargeStation.visibility = if (null == park.socketQty) View.GONE else View.VISIBLE

            binding.txtSocketAvailable.apply {
                text = Util.getString(R.string.park_socket_available, park.socketAvailable)
                visibility = if (null == park.socketAvailable) View.GONE else View.VISIBLE
            }
            binding.txtSocketInUse.apply {
                text = Util.getString(R.string.park_socket_in_use, park.socketInUse)
                visibility = if (null == park.socketInUse) View.GONE else View.VISIBLE
            }

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
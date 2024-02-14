package com.example.dialogfragmenttest.entities

class AvailableVolumeValues(
    val volumeList: List<Int>,
    val currentIndex: Int
) {

    companion object {

        fun getVolumeList(volume: Int): AvailableVolumeValues {
            val volumeList = (0..100 step 10).toList()
            val currentIndex = volumeList.indexOf(volume)
            if (currentIndex == -1) {
                val newList = volumeList + volume
                return AvailableVolumeValues(newList, newList.lastIndex)
            }
            return AvailableVolumeValues(volumeList, currentIndex)
        }
    }

}
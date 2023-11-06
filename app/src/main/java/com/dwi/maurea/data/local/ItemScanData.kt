package com.dwi.maurea.data.local

object ItemScanData {
    private val itemScanName = arrayOf(
        "Pepaya",
        "Buah Naga",
        "Tomat",
        "Semangka",
    )

    private val itemId = arrayOf(
        "1",
        "2",
        "3",
        "4",
    )

    // TODO: 1. Change this to your own image
    private val itemScanImage = arrayOf(
        "https://images.unsplash.com/photo-1581242335635-ce8631489ac5?auto=format&fit=crop&q=80&w=1887&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1623030235422-07f96401f5ea?auto=format&fit=crop&q=80&w=1935&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1582284540020-8acbe03f4924?auto=format&fit=crop&q=80&w=1935&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1628160154851-ea8b3d79ef0a?auto=format&fit=crop&q=80&w=1950&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    )

    val listData: ArrayList<ItemScan>
        get() {
            val list = arrayListOf<ItemScan>()
            for (position in itemScanName.indices) {
                val itemScan = ItemScan(
                    id = itemId[position],
                    nama = itemScanName[position],
                    gambar = itemScanImage[position],
                )
                list.add(itemScan)
            }
            return list
        }
}
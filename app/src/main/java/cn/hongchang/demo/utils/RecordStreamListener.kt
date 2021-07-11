package cn.hongchang.demo.utils

interface RecordStreamListener {
    fun recordOfByte(data: ByteArray?, business: Int, length: Int)
}
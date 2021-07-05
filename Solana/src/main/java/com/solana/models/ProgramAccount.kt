package com.solana.models

import com.solana.models.Buffer.Buffer
import com.solana.vendor.borshj.Borsh
import com.solana.vendor.borshj.BorshCodable
import com.squareup.moshi.Json

class ProgramAccount<T: BorshCodable>(pa: Map<String, Any>, clazz: Class<T>) {

    @Json(name = "account") val account: BufferInfo<T> = BufferInfo(pa["account"], clazz)
    @Json(name = "pubkey")  val pubkey: String = pa["pubkey"] as String

    class BufferInfo<T: BorshCodable>(acc: Any?, clazz: Class<T>){
        @Json(name = "data") var data: Buffer<T>? = null

        @Json(name = "executable") val executable: Boolean

        @Json(name = "lamports") val lamports: Double

        @Json(name = "owner") val owner: String?

        @Json(name = "rentEpoch") val rentEpoch: Double


        init {
            val borsh = Borsh()
            val account = acc as Map<String, Any>
            val rawData = account["data"]!!
            data = Buffer(borsh, rawData, clazz)
            executable = account["executable"] as Boolean
            lamports = account["lamports"] as Double
            owner = account["owner"] as String?
            rentEpoch = account["rentEpoch"] as Double
        }
    }
}
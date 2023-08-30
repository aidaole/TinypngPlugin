package com.aidaole.plugin.imgcompress

import com.tinify.Tinify
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO

class Tinypng(private val apiKey: String, private val imgTypesStr: String) {

    private val imgTypes = mutableSetOf(".png", ".jpg", ".jepg")

    init {
        Tinify.setKey(apiKey)
        if (imgTypesStr.isNotEmpty()) {
            imgTypes.clear()
            imgTypes.addAll(imgTypesStr.split("|"))
        }
        log("imageTypes: $imgTypes")
    }

    // 递归所有图片
    fun compressAllFile(filePath: String) {
        if (filePath.isExcludeFile()) {
            return
        }
        val root = File(filePath)
        if (root.isDirectory) {
            root.listFiles()?.forEach { item ->
                compressAllFile(item.toString())
            }
        } else {
            compressFile(filePath, filePath)
        }
    }

    // 调用api压缩图片
    private fun compressFile(fromFile: String, toFile: String) {
        if (fromFile.isInImgTypes() && !fromFile.endsWith(".9.png")) {
            val imgBitDepth = getImgBitDepth(fromFile)
            if (imgBitDepth > 16) {
                log("compress:${fromFile}, imgBitDepth: $imgBitDepth")
                try {
                    Tinify.fromFile(fromFile).toFile(toFile)
                } catch (e: Exception) {
                    log("error: $e")
                }
            } else {
                log("not compress:${fromFile}")
            }
        }
    }

    // 是否是要压缩的类型
    private fun String.isInImgTypes(): Boolean {
        imgTypes.forEach { imgSuffix ->
            if (this.endsWith(imgSuffix)) {
                return true
            }
        }
        return false
    }

    // 获取图片位深度，避免重复压缩
    private fun getImgBitDepth(imgPath: String): Int {
        val pngFile = File(imgPath)
        val fis = FileInputStream(pngFile)
        var depth = 0
        fis.use {
            val sourceImg = ImageIO.read(fis)
            depth = sourceImg.colorModel.pixelSize
        }
        return depth
    }

    // 排除不需要扫描的目录
    private fun String.isExcludeFile(): Boolean {
        if ((contains("build")) || contains("intermediates") || contains(".gradle")) {
            return true
        }
        return false
    }
}
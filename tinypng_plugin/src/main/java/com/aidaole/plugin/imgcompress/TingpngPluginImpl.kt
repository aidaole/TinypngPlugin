package com.aidaole.plugin.imgcompress

import org.gradle.api.Plugin
import org.gradle.api.Project

class TingpngPluginImpl : Plugin<Project> {
    override fun apply(project: Project) {
        log("config plugin start")
        // 注册插件的extensions配置
        val config = project.extensions.create("tinypngConfig", TinypngConfig::class.java)
        // 创建图片压缩的task, 其中 apiKey 和 imgTypes 参数通过 extensions 中的配置读取
        project.tasks.register("compressImg") { compressTask ->
            compressTask.group = "tinypng"
            compressTask.doLast {
                val apiKey = config.apiKey ?: ""
                val imgTypes = config.imgTypes ?: ""
                log("apiKey: $apiKey")
                log("imgTypes: $imgTypes")
                log("root: ${project.rootProject.rootDir.absolutePath}")

                val tinyPng = Tinypng(apiKey, imgTypes)
                tinyPng.compressAllFile(project.rootProject.rootDir.absolutePath)
            }
        }
        log("config plugin end")
    }
}
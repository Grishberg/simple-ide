package com.github.grishberg.simpleide.projecttree

import java.io.File


class FileNode(private val file: File) {

    override fun toString(): String {
        val name = file.name
        return if (name == "") {
            file.absolutePath
        } else {
            name
        }
    }
}
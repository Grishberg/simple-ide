package com.github.grishberg.simpleide.projecttree

import com.github.grishberg.profiler.common.CoroutinesDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.awt.geom.Rectangle2D
import java.io.File
import javax.swing.tree.DefaultMutableTreeNode



class ProjectSourceTreeController(
        private val coroutineScope: CoroutineScope,
        private val dispatchers: CoroutinesDispatchers
) {
    var view: ProjectTreeView? = null

    fun loadTree(rootFolder: File) {
        coroutineScope.launch(dispatchers.ui) {
            // ui updates
            val root = DefaultMutableTreeNode(FileNode(rootFolder))
            val ccn = calculateFlame(rootFolder, root)

            view?.updateRoot(root)
        }
    }

    private suspend fun calculateFlame(rootFolder: File, root: DefaultMutableTreeNode) {
        val data = coroutineScope.async(dispatchers.worker) {
            // worker thread calculations.
            createChildren(rootFolder, root)
            return@async true
        }
        data.await()
    }

    private fun createChildren(fileRoot: File,
                               node: DefaultMutableTreeNode) {
        val files = fileRoot.listFiles() ?: return

        for (file in files) {
            val childNode = DefaultMutableTreeNode(FileNode(file))
            node.add(childNode)
            if (file.isDirectory) {
                createChildren(file, childNode)
            }
        }
    }
}
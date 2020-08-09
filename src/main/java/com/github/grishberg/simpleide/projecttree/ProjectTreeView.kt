package com.github.grishberg.simpleide.projecttree

import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel


interface ProjectTreeView {
    fun updateRoot(root: DefaultMutableTreeNode)
}

class ProjectTreeViewImpl(
        private val controller: ProjectSourceTreeController
): ProjectTreeView {
    private val tree = JTree(DefaultMutableTreeNode())
    val scrollPane = JScrollPane(tree)
    private val treeModel: DefaultTreeModel? = null

    init {
        tree.setShowsRootHandles(true)
        controller.view = this
    }

    override fun updateRoot(root: DefaultMutableTreeNode) {
        val treeModel = DefaultTreeModel(root)
        tree.model = treeModel
    }

}
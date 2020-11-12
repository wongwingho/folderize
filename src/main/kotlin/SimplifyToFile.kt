import com.intellij.icons.AllIcons.General.Error
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile

class SimplifyToFile : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        file?.let {
            val onlyChild = it.children[0]
            val newFileName = "${it.name}.${onlyChild.extension}"

            if (it.parent.findChild(newFileName) == null) {
                onlyChild.move(e, it.parent)
                onlyChild.rename(e, newFileName)
                it.delete(e)
            } else {
                Messages.showMessageDialog("$newFileName already exist", "Error", Error)
            }
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.getData(PlatformDataKeys.VIRTUAL_FILE)?.let { it.isDirectory && onlyChild(it) } ?: false
    }

    private fun onlyChild(f: VirtualFile): Boolean = f.isDirectory && f.children.size == 1
}
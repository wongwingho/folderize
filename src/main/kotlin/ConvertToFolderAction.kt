import com.intellij.icons.AllIcons.General.Error
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages

class ConvertToFolderAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        file?.let {
            if (file.parent.findChild(it.nameWithoutExtension) == null) {
                val folderized = file.parent.createChildDirectory(e, file.nameWithoutExtension)
                file.move(e, folderized)
                file.rename(e, "index.${file.extension}")
            } else {
                Messages.showMessageDialog("${file.nameWithoutExtension} already exist", "Error", Error)
            }
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = !(e.getData(PlatformDataKeys.VIRTUAL_FILE)?.isDirectory ?: true)
    }
}
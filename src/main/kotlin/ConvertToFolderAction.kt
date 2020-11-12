import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys

class ConvertToFolderAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        file?.let {
            val noFileWithSameNameWithoutExtension =
                file.parent.children.all { it.path == file.path || it.name != file.nameWithoutExtension }

            if (noFileWithSameNameWithoutExtension) {
                val folderized = file.parent.createChildDirectory(e, file.nameWithoutExtension)
                file.move(e, folderized)
                file.rename(e, "index.${file.extension}")
            }
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = !(e.getData(PlatformDataKeys.VIRTUAL_FILE)?.isDirectory ?: true)
    }
}
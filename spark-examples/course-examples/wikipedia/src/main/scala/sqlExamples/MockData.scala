package sqlExamples

import java.io.File

object MockData {

  private[sqlExamples] def filePath(fileName: String) = {
    val resource = this.getClass.getClassLoader.getResource("data/" + fileName)
    if (resource == null) sys.error("Please put your data file at /src/main/resources path.")
    new File(resource.toURI).getPath
  }
}

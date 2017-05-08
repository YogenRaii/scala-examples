package sqlExamples

import java.io.File

object PeopleData {

  private[sqlExamples] def filePath = {
    val resource = this.getClass.getClassLoader.getResource("data/people.json")
    if (resource == null) sys.error("Please put your data file at /src/main/resources path.")
    new File(resource.toURI).getPath
  }
}
